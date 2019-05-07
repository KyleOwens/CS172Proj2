package Loader;

import Characters.MainCharacter;
import Equipment.Armor;
import Equipment.Equipment;
import Equipment.Weapon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    //private String[] statNames = {"HP", "MAXHP", "POWER", "DEFENSE", "MP", "MAXMP", "EXPERIENCE", "LEVEL", "GOLD", "ATTRIBUTEPOINTS"};

    public Loader() {
        try {
            saveFile = new File(SAVE_FILE_DIRECTORY + "\\CharacterSave.dat");
            bufferedReader = new BufferedReader(new FileReader(saveFile));
            equipment = new ArrayList<Equipment>();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public MainCharacter loadFile() {
        loadStats();
        loadEquipment();
//        loadBag();
        return MainCharacter.loadCharacterFromFile(characterStats);
    }

    private void loadStats() {
        characterStats = new HashMap<String, Integer>();
        String currentLine = "";
        String currentStat = "";
        int currentAmount = -1;

        try {
            for (int i = 0; i < 10; i++) {
                currentLine = bufferedReader.readLine();
                currentStat = currentLine.substring(0, currentLine.indexOf('-') - 1);
                System.out.println(currentStat);
                currentAmount = Integer.parseInt(currentLine.substring(currentLine.indexOf('-') + 2));
                characterStats.put(currentStat, currentAmount);
            }
        } catch (Exception e) {
        }
    }

    private void loadEquipment() {
        try {
            String currentLine = bufferedReader.readLine();
            int stat = Integer.parseInt(currentLine.substring(currentLine.indexOf("STAT") + 5, currentLine.indexOf("REQ") - 1));
            int requirement = Integer.parseInt(currentLine.substring(currentLine.indexOf("REQ") + 4));
            this.armor = Armor.makeArmorWithStats(stat, requirement);

            currentLine = bufferedReader.readLine();
            stat = Integer.parseInt(currentLine.substring(currentLine.indexOf("STAT") + 5, currentLine.indexOf("REQ") - 1));
            requirement = Integer.parseInt(currentLine.substring(currentLine.indexOf("REQ") + 4));
            this.weapon = Weapon.makeWeaponWithStats(stat, requirement);
        } catch (Exception e) {

        }
    }

    private void loadBag(){
        String currentLine = "";
        String currentType = "";
        int currentStat = -1;
        int currentReq = -1;

        try {
            while ((currentLine = bufferedReader.readLine()) != null){
                currentType = currentLine.substring(currentLine.indexOf("TYPE") + 5, currentLine.indexOf("STAT")-1);
                currentStat = Integer.parseInt(currentLine.substring(currentLine.indexOf("STAT") + 5, currentLine.indexOf("REQ")-1));
                currentReq = Integer.parseInt(currentLine.substring(currentLine.indexOf("REQ")+4));

                if(currentType.equals("WEAPON")){
                    equipment.add(Weapon.makeWeaponWithStats(currentStat, currentReq));
                } else {
                    equipment.add(Armor.makeArmorWithStats(currentStat, currentReq));
                }
            }
        } catch (Exception e){

        }
    }

    public Armor getArmor() {
        return armor;
    }

    public Weapon getWeapon() {
        return weapon;
    }

}
