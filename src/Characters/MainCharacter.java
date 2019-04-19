package Characters;

import Equipment.Armor;
import Equipment.Equipment;
import Equipment.Weapon;
import UserInput.UserInput;

import java.util.ArrayList;

public class MainCharacter extends Character {
    private Weapon weapon;
    private Armor armor;
    private int experience = 0;
    private int level = 1;
    private LootBag lootBag;

    private MainCharacter(int hp, int power) {
        this.setHp(hp);
        this.setPower(power);
        this.lootBag = new LootBag(5);
    }

    public static MainCharacter createCharacterWithStats(int hp, int power) {
        return new MainCharacter(hp, power);
    }

    @Override
    public void attack(Character character) {
        int hit = this.getPower();
        character.setHp(character.getHp() - hit);
    }

    public int getLevel() {
        return level;
    }

    public void addExperience(int experience) {
        this.experience += experience;
        System.out.println(experience);
        checkForLevelUp();
    }

    private void checkForLevelUp() {
        while (experience >= ((level + 1) * 200)) {
            level++;
            System.out.println(experience + "  " + (level+1)*200);
            System.out.println("\n************* \nLevel Up! - Your level: " + this.level + "\n*************");
        }
    }

    public void addItemToBag(Equipment equipment) {
        lootBag.addItem(equipment);
    }

    public void printItems() {
        lootBag.listItems();
    }


    @Override
    public String toString() {
        return "Your Characters Current Stats:  HP: " + this.getHp() + "   Power: " + this.getPower();
    }


    class LootBag {
        private int slots;
        private ArrayList<Equipment> items;

        public LootBag(int bagSize) {
            this.slots = bagSize;
            items = new ArrayList();
        }

        public void addItem(Equipment equipment) {
            if (items.size() < slots) {
                items.add(equipment);
            } else {
                manageBag(equipment);
            }
        }

        private void manageBag(Equipment equipment) {
            System.out.println("\n Which item would you like to get rid of?");
            listItems();
            System.out.println((items.size() + 1 + ") Exit and drop item instead"));
            int selection = UserInput.getUserInput();
            if (selection <= items.size()) {
                items.remove(selection - 1);
                items.add(equipment);
            }
        }

        private void listItems() {
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ") " + items.get(i));
            }
        }
    }
}
