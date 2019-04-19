package GameLogic;

import Characters.MainCharacter;
import UserInput.UserInput;

public class GameManager {
    private MainCharacter mainCharacter;
    private int menuSelection = 0;
    private BattleManager battleManager;

    public GameManager() {
        this.mainCharacter = MainCharacter.createCharacterWithStats(100, 20);
    }

    public GameManager(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public void startGame() {
        battleManager = new BattleManager(mainCharacter);

        while (menuSelection != 5) {
            printOptions();
            menuSelection = UserInput.getUserInput();

            switch (menuSelection) {
                case 1:
                    beginBattle();
                    break;
                case 2:
                    manageEquipment();
                    break;
                case 3:
                    manageAttributes();
                    break;
                case 4:
                    printLootBag();
                    break;
                default:
                    if (menuSelection != 5) {
                        System.out.println("Your selection is invalid, please try again!");
                    }

            }
        }


    }

    private void printOptions() {
        System.out.println("\n" + this.mainCharacter + "\n1) Enter Battle \n2) Manage Equipment\n3) Manage Attributes\n4) Print Loot Bag\n5) Exit");
    }

    private void beginBattle() {
        battleManager.startNewBattle();
    }

    private void manageEquipment() {

    }

    private void manageAttributes() {

    }

    private void printLootBag(){
        mainCharacter.printItems();
    }

}
