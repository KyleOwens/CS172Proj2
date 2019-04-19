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
        if (mainCharacter.getCurrentBagSize() > 0) {
            mainCharacter.printEquipment();
            printLootBag();
            System.out.println((mainCharacter.getCurrentBagSize()+1) + ") Exit");

            int selection = UserInput.getUserInput();
            if (selection <= mainCharacter.getCurrentBagSize()) {
                printEquipmentOptions(selection);
            }
        } else {
            System.out.println("No items to manage, returning to menu");
        }
    }

    private void printEquipmentOptions(int selection) {
        mainCharacter.printBagItem(selection - 1);
        System.out.println("1) Equip \n2) Drop \n3) Return To Equipment");
        int newSelection = UserInput.getUserInput();
        if(newSelection == 1){
            mainCharacter.swapEquipment(selection -1);
        } else if(newSelection == 2) {
            mainCharacter.removeItem(selection-1);
        }
    }

    private void manageAttributes() {

    }

    private void printLootBag() {
        mainCharacter.printItems();
    }

}
