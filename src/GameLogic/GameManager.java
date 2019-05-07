package GameLogic;

import Characters.MainCharacter;
import Equipment.Armor;
import Equipment.Weapon;
import UserInput.UserInput;

public class GameManager {
    private MainCharacter mainCharacter;
    private int menuSelection = 0;
    private BattleManager battleManager;

    public GameManager() {
        this.mainCharacter = MainCharacter.createCharacterWithStats(100, 20);
    }

    public GameManager(MainCharacter mainCharacter, Armor a, Weapon w) {
        this.mainCharacter = mainCharacter;
        this.mainCharacter.setArmor(a);
        this.mainCharacter.setWeapon(w);
    }

    public void startGame() {
        battleManager = new BattleManager(mainCharacter);

        while (menuSelection != 6) {
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
                case 5:
                    openShop();
                    break;
                default:
                    if (menuSelection != 6) {
                        System.out.println("Your selection is invalid, please try again!");
                    }

            }
        }


    }

    private void printOptions() {
        System.out.println("\n" + this.mainCharacter + "\n1) Enter Battle \n2) Manage Equipment\n3) Manage Attributes\n4) Print Loot Bag\n5) Shop \n6) Exit");
    }

    private void beginBattle() {
        battleManager.startNewBattle();
    }

    private void manageEquipment() {
        if (mainCharacter.getCurrentBagSize() > 0) {
            mainCharacter.printEquipment();
            printLootBag();
            System.out.println((mainCharacter.getCurrentBagSize() + 1) + ") Exit");

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
        if (newSelection == 1) {
            if (!mainCharacter.swapEquipment(selection - 1)) {
                System.out.println("Your level is too low to wear that!");
                manageEquipment();
            }
        } else if (newSelection == 2) {
            mainCharacter.removeItem(selection - 1);
        }
    }

    private void manageAttributes() {
        printAttributeOptions();
        int selection = UserInput.getUserInput();

        if (selection < 4) {
            System.out.println("\nHow many points to use?");
            int amount = UserInput.getUserInput();

            if (amount > mainCharacter.getCurrentAttributes()) {
                amount = mainCharacter.getCurrentAttributes();
            }


            if (selection == 1) {
                mainCharacter.raiseMaxHP(amount);
                mainCharacter.lowerAttributePoints(amount);
            } else if (selection == 2) {
                mainCharacter.raiseMaxPower(amount);
                mainCharacter.lowerAttributePoints(amount);
            } else if (selection == 3) {
                mainCharacter.raiseMaxMP(amount);
                mainCharacter.lowerAttributePoints(amount);
            }
        }
    }

    private void printAttributeOptions() {
        System.out.println("\nCurrent Attribute Points: " + mainCharacter.getCurrentAttributes() + "\n1) Raise Max HP\n2) Raise Max Power\n3) Raise Max MP \n4) Exit");
    }

    private void printLootBag() {
        mainCharacter.printItems();
    }

    private void openShop() {
        printShopOptions();
        int selection = UserInput.getUserInput();

        if (selection == 1) {
            if (mainCharacter.getCurrentGold() > 200) {
                mainCharacter.setMp(mainCharacter.getMaxMp());
                mainCharacter.subtractGold(200);
            } else {
                System.out.println("You can't afford that!");
                openShop();
            }
        } else if (selection == 2) {
            if (mainCharacter.getCurrentGold() > 500) {
                mainCharacter.setHp(mainCharacter.getMaxHp());
                mainCharacter.subtractGold(500);
            } else {
                System.out.println("You can't afford that!");
                openShop();
            }
        }
    }

    private void printShopOptions() {
        System.out.println("\nCurrent Gold: " + mainCharacter.getCurrentGold());
        System.out.println("1) Replenish MP - 200 Gold");
        System.out.println("2) Replenish HP - 500 Gold");
        System.out.println("3) Exit");
    }

}
