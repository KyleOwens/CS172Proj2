package Saving;

import Characters.MainCharacter;
import Equipment.Armor;
import Equipment.Equipment;
import Equipment.Weapon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Loader {
    public static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    public static final String SAVE_FILE_DIRECTORY = PROJECT_DIRECTORY + "\\Saves";
    private File saveFile;
    private BufferedReader bufferedReader;

    private Map<String, Integer> characterStats;
    private Armor armor;
    private Weapon weapon;
    private ArrayList<Equipment> equipment;


    public Loader() {
        try {
            saveFile = new File(SAVE_FILE_DIRECTORY + "\\CharacterSave.dat");
            bufferedReader = new BufferedReader(new FileReader(saveFile));
            equipment = new ArrayList<Equipment>();
        } catch (Exception e) {
        }
    }

    public MainCharacter loadFile() {
        try {
            loadStats();
            loadEquipment();
            loadBag();
        } catch (Exception e) {
            System.out.println("The save file could not be loaded! Starting new game.");
            return null;
        }
        return MainCharacter.loadCharacterFromFile(characterStats, weapon, armor, equipment);
    }

    public void close(){
        try{
            bufferedReader.close();
        } catch(Exception e){
            System.out.println("Failed to close");
        }
    }

    private void loadStats() throws IOException {
        characterStats = new HashMap<String, Integer>();
        String currentLine = "";
        String currentStat = "";
        int currentAmount = -1;


        for (int i = 0; i < 10; i++) {
            currentLine = bufferedReader.readLine();
            currentStat = currentLine.substring(0, currentLine.indexOf('-') - 1);
            currentAmount = Integer.parseInt(currentLine.substring(currentLine.indexOf('-') + 2));
            characterStats.put(currentStat, currentAmount);
        }
    }


    private void loadEquipment() throws IOException {
        String currentLine = bufferedReader.readLine();
        int stat = Integer.parseInt(currentLine.substring(currentLine.indexOf("STAT") + 5, currentLine.indexOf("REQ") - 1));
        int requirement = Integer.parseInt(currentLine.substring(currentLine.indexOf("REQ") + 4));
        this.armor = Armor.makeArmorWithStats(stat, requirement);

        currentLine = bufferedReader.readLine();
        stat = Integer.parseInt(currentLine.substring(currentLine.indexOf("STAT") + 5, currentLine.indexOf("REQ") - 1));
        requirement = Integer.parseInt(currentLine.substring(currentLine.indexOf("REQ") + 4));
        this.weapon = Weapon.makeWeaponWithStats(stat, requirement);

    }

    private void loadBag() throws IOException {
        String currentLine = "";
        String currentType = "";
        int currentStat = -1;
        int currentReq = -1;

        currentLine = bufferedReader.readLine();
        while (currentLine != null && !currentLine.equals("")) {
            currentType = currentLine.substring(currentLine.indexOf("TYPE") + 5, currentLine.indexOf("STAT") - 1);
            currentStat = Integer.parseInt(currentLine.substring(currentLine.indexOf("STAT") + 5, currentLine.indexOf("REQ") - 1));
            currentReq = Integer.parseInt(currentLine.substring(currentLine.indexOf("REQ") + 4));

            if (currentType.equals("WEAPON")) {
                equipment.add(Weapon.makeWeaponWithStats(currentStat, currentReq));
            } else {
                equipment.add(Armor.makeArmorWithStats(currentStat, currentReq));
            }

            currentLine = bufferedReader.readLine();
        }
    }

    public Armor getArmor() {
        return armor;
    }

    public Weapon getWeapon() {
        return weapon;
    }

}
