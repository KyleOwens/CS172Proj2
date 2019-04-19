package Characters;

import Equipment.Armor;
import Equipment.Equipment;
import Equipment.Weapon;
import UserInput.UserInput;
import javafx.scene.Parent;

import java.util.ArrayList;

public class MainCharacter extends Character {
    private Weapon weapon;
    private Armor armor;
    private int experience = 0;
    private int level = 1;
    private LootBag lootBag;
    private int mp;
    private int maxMp;


    private MainCharacter(int hp, int power) {
        this.setHp(hp);
        this.setMaxHp(hp);
        this.setPower(power);
        this.lootBag = new LootBag(5);
        this.mp = 20;
        this.maxMp = 20;

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

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void printEquipment(){
        if(armor == null){
            System.out.println("\nCurrent Armor: none");
        } else {
            System.out.println("\nCurrent Armor: " + armor);
        }

        if(weapon == null){
            System.out.println("Current Weapon: none");
        } else {
            System.out.println("Current Weapon: " + weapon);
        }

    }

    public boolean healSelf(){
        if(mp >= 5){
            if(this.getHp() + 20 < this.getMaxHp()){
                this.setHp(this.getHp() + 20);
            } else {
                this.setHp(this.getMaxHp());
            }
            this.mp -= 5;
            return true;
        } else {
            return false;
        }
    }

    public boolean attackAll(ArrayList<Enemy> enemies){
        if(mp >= 10){
            this.mp -= 10;
            for(int i = 0; i < enemies.size(); i++){
                enemies.get(i).setHp(enemies.get(i).getHp() - (this.getPower())/2);
            }
            return true;
        }
        return false;
    }

    public int getCurrentBagSize(){
          return lootBag.getAmountOfItmes();
    }

    public void printBagItem(int selection){
        lootBag.printBagItem(selection);
    }

    public void removeItem(int selection){
        lootBag.removeItem(selection);
    }

    public void swapEquipment(int selection){
        Equipment newEquip = lootBag.retrieveItem(selection);
        if(newEquip instanceof Armor){
            if(this.armor == null){
                lootBag.removeItem(selection);
                this.armor = (Armor) newEquip;
            } else {
                lootBag.removeItem(selection);
                lootBag.addItem(this.armor);
                this.armor = (Armor) newEquip;
            }
        }

        if(newEquip instanceof Weapon){
            if(this.weapon == null){
                lootBag.removeItem(selection);
                this.weapon = (Weapon) newEquip;
            } else {
                lootBag.removeItem(selection);
                lootBag.addItem(this.weapon);
                this.weapon = (Weapon) newEquip;
            }
        }
    }


    @Override
    public String toString() {
        String armor;
        String weapon;
        if(this.armor == null){
            armor = "   Armor: 0";
        } else {
            armor = "   Armor: " + this.armor.getStat();
        }

        if(this.weapon == null){
            weapon = "   Weapon: 0";
        } else {
            weapon = "   Weapon: " + this.weapon.getStat();
        }
        return "Your Characters Current Stats:  HP: " + this.getHp() + "   Power: "
                + this.getPower() + "   MP: " + this.mp + armor + weapon;
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

        public void removeItem(int selection){
            items.remove(selection);
        }

        public void printBagItem(int selection){
            System.out.println(items.get(selection));
        }

        public Equipment retrieveItem(int selection){
            return items.get(selection);
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
            if(items.size() > 0){
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ") " + items.get(i));
                }
            } else {
                System.out.println("You have no items in your bag!");
            }

        }

        private int getAmountOfItmes(){
            return items.size();
        }

    }
}
