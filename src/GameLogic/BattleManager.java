package GameLogic;

import Characters.Enemy;
import Characters.MainCharacter;
import Equipment.Armor;
import Equipment.Equipment;
import Equipment.Weapon;
import UserInput.UserInput;

import java.util.ArrayList;
import java.util.Random;


public class BattleManager {
    private static boolean battleState = true;
    private int possibleExperience;

    private MainCharacter mainCharacter;
    private ArrayList<Enemy> enemies;
    private ArrayList<Equipment> loot;

    private Random random;


    public BattleManager(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
        this.random = new Random();
        this.enemies = new ArrayList();
        this.loot = new ArrayList();
    }

    public void startNewBattle() {
        createEnemies();
        generateLootTable();
        calculatePossibleExp();
        runBattle();
    }

    private void createEnemies() {
        int enemyCount = random.nextInt(5) + 1;
        this.enemies.clear();

        for (int i = 0; i < enemyCount; i++) {
            this.enemies.add(Enemy.createRandomEnemy(50, 10));
        }

        battleState = true;
    }

    //968-ymb
    private void generateLootTable(){
        this.loot.clear();
        for (int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).hasLoot()){
                addLootToTable();
            }
        }
    }

    private void addLootToTable(){
        int type = random.nextInt(2);
        if(type == 1){
            loot.add(Armor.makeRandomArmor((mainCharacter.getLevel() + 5) * 5));
        } else {
            loot.add(Weapon.makeRandomWeapon((mainCharacter.getLevel() + 5) * 5));
        }
    }

    private void calculatePossibleExp() {
        this.possibleExperience = 0;
        for (int i = 0; i < enemies.size(); i++) {
            possibleExperience += enemies.get(i).getAwardExp();
        }
    }

    private void runBattle(){
        while (battleState) {
            mainCharacterTurn();
            checkForEnemyDeaths();

            if (isBattleComplete()) {
                endBattle();
            }

            enemyTurn();
            checkforCharacterDeath();
        }
    }

    private void mainCharacterTurn() {
        printCharacterOptionMenu();
        int selection = UserInput.getUserInput();

        switch (selection) {
            case 2:
                useSpecial();
                break;
            case 3:
                attemptToFlee();
                break;
            default:
                attackEnemy();
        }
    }

    private void printCharacterOptionMenu() {
        System.out.println("\nBattle Options:");
        printBattleInfo();
        System.out.println("1) Attack \n2) FriendlySpecial \n3) Flee");
    }

    private void printBattleInfo() {
        System.out.print("Enemies: ");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.print("[" + enemies.get(i) + "]  ");
        }
        System.out.println("");
        System.out.println(mainCharacter);
    }

    private void attackEnemy() {
        if(enemies.size() > 1){
            printEnemies();
            int selection = UserInput.getUserInput();
            this.mainCharacter.attack(enemies.get(selection - 1));
        } else {
            this.mainCharacter.attack(enemies.get(0));
        }

    }

    private void printEnemies() {
        System.out.println("\nPick an enemy to attack:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ")" + enemies.get(i));
        }
    }

    private void attemptToFlee() {
        if (random.nextInt(2) == 0) {
            System.out.println("You have successfully fled the battle");
            battleState = false;
        } else {
            System.out.println("You were not able to leave the battle!");
        }
    }

    private void useSpecial() {
        System.out.println("\nYour MP: " + mainCharacter.getMp());
        System.out.println("1) Attack All(10mp) \n2) Heal Self (5mp)\n3)Return to other options");
        int selection = UserInput.getUserInput();
        if(selection == 1){
            if(!mainCharacter.attackAll(enemies)){
                System.out.println("The spell cast failed, you didn't have enough mana!");
                mainCharacterTurn();
            }
        } else if (selection == 2){
            if(!mainCharacter.healSelf()){
                System.out.println("The spell cast failed, you didn't have enough mana!");
                mainCharacterTurn();
            }
        } else {
            mainCharacterTurn();
        }
    }

    private void checkForEnemyDeaths() {
        for (int i = enemies.size()-1; i >= 0; i--) {
            if (enemies.get(i).getHp() <= 0) {
                System.out.println("Enemy " + (i+1) + " has died");
                enemies.remove(i);
            }
        }
    }

    private void enemyTurn() {
        for (int i = 0; i < enemies.size(); i++) {
            if (random.nextInt(10) + 1 >= enemies.size()) {
                System.out.println("Enemy " + (i+1) + " hits you for " + enemies.get(i).getPower());
                enemies.get(i).attack(mainCharacter);
            } else {
                System.out.println("Enemy " + (i+1) + " missed!");
            }
        }
    }

    private void checkforCharacterDeath(){
        if(mainCharacter.getHp() <= 0){
            System.out.println("You have died!! --- GAME OVER");
            System.exit(0);
        }
    }

    private boolean isBattleComplete() {
        if (enemies.size() == 0) {
            return true;
        }
        return false;
    }

    private void endBattle() {
        battleState = false;
        System.out.println("\nYOU ARE VICTORIOUS");
        awardExperience();
        offerLoot();
    }

    private void offerLoot(){
        for(int i = 0; i < loot.size(); i++){
            System.out.println("\nWould you like to take this item --");
            System.out.println("[Level Requirement: " + loot.get(i).getLevelRequirement() + "] ["
                    + loot.get(i).getItemSlot() + ": " + loot.get(i).getStat() + "]");

            System.out.println("1) Yes");
            System.out.println("2) No");
            int selection = UserInput.getUserInput();

            if(selection == 1){
                mainCharacter.addItemToBag(loot.get(i));
            }
        }
    }

    private void awardExperience() {
        mainCharacter.addExperience(possibleExperience);
    }

}
