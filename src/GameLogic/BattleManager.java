package GameLogic;

import Characters.Enemy;
import Characters.MainCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BattleManager {
    private static boolean battleState = true;

    private MainCharacter mainCharacter;
    private ArrayList<Enemy> enemies;

    private Random random = new Random();
    private Scanner scanner;


    public BattleManager(MainCharacter mainCharacter, Scanner scanner) {
        this.mainCharacter = mainCharacter;
        this.random = new Random();
        this.scanner = scanner;
        this.enemies = new ArrayList();
    }

    public void startNewBattle() {
        createEnemies();
        while (battleState) {
            mainCharacterTurn();
            checkForEnemyDeaths();

            if(isBattleComplete()){
                endBattle();
            }
            enemyTurn();
        }

    }

    private void createEnemies() {
        int enemyCount = random.nextInt(5) + 1;

        this.enemies.clear();

        for (int i = 0; i < enemyCount; i++) {
            this.enemies.add(Enemy.createRandomEnemy(50, 10));
        }
    }

    private void mainCharacterTurn() {
        printCharacterOptionMenu();
        int selection = getUserInput();

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
        System.out.println("1) Attack \n2) Special \n3) Flee");
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
        printEnemies();
        int selection = getUserInput();
        this.mainCharacter.attack(enemies.get(selection - 1));
    }

    private void printEnemies() {
        System.out.println("\nPick an enemy to attack:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ")" + enemies.get(i));
        }
    }

    private void attemptToFlee() {
        if (random.nextInt(2) == 0) {
            System.out.println("You have successfull fled the battle");
            battleState = false;
        } else {
            System.out.println("You were not able to leave the battle!");
        }
    }

    private void useSpecial() {
    }

    private void checkForEnemyDeaths() {
        for (int i = enemies.size(); i >= 0; i--){
            if(enemies.get(i).getHp() <= 0){
                System.out.println("Enemy " + i + "has died");
                enemies.remove(i);
            }
        }
    }

    private void enemyTurn() {
        for (int i = 0; i < enemies.size(); i++) {
            if (random.nextInt(10) + 1 >= enemies.size()) {
                System.out.println("Enemy " + i + " hits you for " + enemies.get(i).getPower());
                enemies.get(i).attack(mainCharacter);
            } else {
                System.out.println("Enemy " + i + " missed!");
            }
        }
    }

    private boolean isBattleComplete(){
        if(enemies.size() == 0){
            return true;
        }
        return false;
    }

    private void endBattle(){
        awardExperience();
        rollLootTable();

    }

    private int getUserInput() {
        return Integer.parseInt(this.scanner.nextLine());
    }
}
