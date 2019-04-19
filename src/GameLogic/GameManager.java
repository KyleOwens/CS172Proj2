package GameLogic;

import Characters.MainCharacter;

import java.util.Scanner;

public class GameManager {
    private MainCharacter mainCharacter;
    private Scanner scanner;
    private int menuSelection = 0;
    private BattleManager battleManager;

    public GameManager(){
        this.mainCharacter = MainCharacter.createCharacterWithStats(100,20);
    }

    public GameManager(MainCharacter mainCharacter){
        this.mainCharacter = mainCharacter;
    }

    public void startGame(){
        scanner = new Scanner(System.in);
        battleManager = new BattleManager(mainCharacter, scanner);

        while(menuSelection != 4){
            printOptions();
            menuSelection = getUserInput();

            switch(menuSelection){
                case 1:
                    beginBattle();
                    break;
                case 2:
                    manageEquipment();
                    break;
                case 3:
                    manageAttributes();
                    break;
                default:
                    if(menuSelection != 4){
                        System.out.println("Your selection is invalid, please try again!");
                    }

            }
        }



    }

    private void printOptions(){
        System.out.println("\n" + this.mainCharacter + "\n1) Enter Battle \n2) Manage Equipment\n3) Manage Attributes\n4) Exit Game");
    }

    private int getUserInput(){
        return Integer.parseInt(this.scanner.nextLine());
    }

    private void beginBattle(){
        battleManager.startNewBattle();
    }

    private void manageEquipment(){

    }

    private void manageAttributes(){

    }

}
