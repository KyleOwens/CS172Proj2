package Saving;

import Characters.MainCharacter;
import Equipment.Equipment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Saver {
    public static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    public static final String SAVE_FILE_DIRECTORY = PROJECT_DIRECTORY + "\\Saves";
    private File saveFile;
    private BufferedWriter bufferedWriter;

    private MainCharacter mainCharacter;

    private String[] statNames = {"HP", "MAXHP", "POWER", "DEFENSE", "MP", "MAXMP", "EXPERIENCE", "LEVEL", "GOLD", "ATTRIBUTEPOINTS"};

    public Saver(MainCharacter mainCharacter){
        try {
            this.mainCharacter = mainCharacter;
            saveFile = new File(SAVE_FILE_DIRECTORY + "\\CharacterSave.dat");
            bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            saveStats();
            saveEquipment();
            saveBag();
            clearRestOfFile();
        } catch(Exception e){
            System.out.println("Failed to save file.");
        }
    }

    public void close(){
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch(Exception e){
            System.out.println("Closing Error");
        }
    }

    private void saveStats() throws IOException {
        int[] stats = mainCharacter.getAllStats();
        for(int i = 0; i < statNames.length; i++){
            String line = statNames[i] + " - " + stats[i];
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
    }

    private void saveEquipment() throws IOException {
        String line = "ARMOR: STAT ";
        if(mainCharacter.getArmor() == null){
           line += "0 REQ 0";
        } else {
            line += mainCharacter.getArmor().getStat() + " REQ " + mainCharacter.getArmor().getLevelRequirement();
        }
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "WEAPON: STAT ";
        if(mainCharacter.getWeapon() == null){
            line += "0 REQ 0";
        } else {
            line += mainCharacter.getWeapon().getStat() + " REQ " + mainCharacter.getWeapon().getLevelRequirement();
        }
        bufferedWriter.write(line);
        bufferedWriter.newLine();
    }

    private void saveBag() throws IOException {
        ArrayList<Equipment> bag = mainCharacter.getItems();
        String line = "";

        for(int i = 0; i < bag.size(); i++){
            Equipment currentItem = bag.get(i);
            line = "EQUIP: TYPE " + currentItem.getItemSlot().toUpperCase() + " STAT " + currentItem.getStat() + " REQ " + currentItem.getLevelRequirement();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
    }

    private void clearRestOfFile() throws IOException {
        for(int i = 0; i < 5; i++){
            bufferedWriter.write("");
            bufferedWriter.newLine();
        }
    }

}
