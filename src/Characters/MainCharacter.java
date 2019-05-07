package Characters;

import Equipment.Armor;
import Equipment.Equipment;
import Equipment.Weapon;
import UserInput.UserInput;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainCharacter extends Character {
    private Weapon weapon;
    private Armor armor;
    private LootBag lootBag;
    private Random random;

    private int experience = 0;
    private int level = 1;
    private int mp;
    private int maxMp;
    private int currentGold;
    private int currentAttributes;


    private MainCharacter(int hp, int power) {
        this.setHp(hp);
        this.setMaxHp(hp);
        this.setPower(power);
        this.lootBag = new LootBag(5);
        this.mp = 20;
        this.maxMp = 20;
        this.currentGold = 0;
        this.currentAttributes = 0;
        this.random = new Random();
    }

    private MainCharacter(Map<String, Integer> statMap, ArrayList<Equipment> bag){
        this.setHp(statMap.get("HP"));
        this.setMaxHp(statMap.get("MAXHP"));
        this.setPower(statMap.get("POWER"));
        this.setDefense(statMap.get("DEFENSE"));
        this.mp = statMap.get("MP");
        this.maxMp = statMap.get("MAXMP");
        this.currentGold = statMap.get("GOLD");
        this.currentAttributes = statMap.get("ATTRIBUTEPOINTS");
        this.lootBag = new LootBag(5, bag);
        this.random = new Random();
    }

    public static MainCharacter createCharacterWithStats(int hp, int power) {
        return new MainCharacter(hp, power);
    }

    public static MainCharacter loadCharacterFromFile(Map<String, Integer> statMap, Weapon weapon, Armor armor, ArrayList<Equipment> bag){
        MainCharacter mc = new MainCharacter(statMap, bag);
        mc.setArmor(armor);
        mc.setWeapon(weapon);
        return mc;
    }

    @Override
    public void attack(Character character) {
        character.setHp(character.getHp() - calculateHit());
    }

    private int calculateHit() {
        int hit = this.getPower();
        if (weapon != null) {
            hit += weapon.getStat();
        }

        hit = rollCriticalHit(hit);

        return hit;
    }

    private int rollCriticalHit(int hit) {
        int critical = random.nextInt(10);

        if (critical <= 1) {
            hit *= 1.5;
            System.out.println("Critical Hit!");
        }
        return hit;
    }

    public void raiseMaxHP(int amount) {
        this.setMaxHp(this.getMaxHp() + amount);
    }

    public void raiseMaxPower(int amount) {
        this.setPower(this.getPower() + amount);
    }

    public void raiseMaxMP(int amount) {
        this.maxMp += amount;
    }

    public void addGold(int gold) {
        this.currentGold += gold;
    }

    public void lowerAttributePoints(int amount) {
        this.currentAttributes -= amount;
    }


    public void addExperience(int experience) {
        this.experience += experience;
        checkForLevelUp();
    }

    private void checkForLevelUp() {
        while (experience >= ((level + 1) * 200)) {
            level++;
            this.currentAttributes += 5;
            System.out.println("\n************* \nLevel Up! - Your level: " + this.level + "\n*************");
        }
    }

    public void addItemToBag(Equipment equipment) {
        lootBag.addItem(equipment);
    }

    public void printItems() {
        lootBag.listItems();
    }


    public void printEquipment() {
        if (armor == null) {
            System.out.println("\nCurrent Armor: none");
        } else {
            System.out.println("\nCurrent Armor: " + armor);
        }

        if (weapon == null) {
            System.out.println("Current Weapon: none");
        } else {
            System.out.println("Current Weapon: " + weapon);
        }

    }

    public boolean healSelf() {
        if (mp >= 5) {
            if (this.getHp() + 20 < this.getMaxHp()) {
                this.setHp(this.getHp() + 30);
            } else {
                this.setHp(this.getMaxHp());
            }
            this.mp -= 5;
            return true;
        } else {
            return false;
        }
    }

    public boolean attackAll(ArrayList<Enemy> enemies) {
        if (mp >= 10) {
            this.mp -= 10;
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).setHp((int) (enemies.get(i).getHp() - (calculateHit() * .75)));
            }
            return true;
        }
        return false;
    }

    public int getCurrentBagSize() {
        return lootBag.getAmountOfItmes();
    }

    public void printBagItem(int selection) {
        lootBag.printBagItem(selection);
    }

    public void removeItem(int selection) {
        lootBag.removeItem(selection);
    }

    public boolean swapEquipment(int selection) {
        Equipment newEquip = lootBag.retrieveItem(selection);
        if (newEquip.getLevelRequirement() <= this.level) {
            if (newEquip instanceof Armor) {
                if (this.armor == null) {
                    lootBag.removeItem(selection);
                    this.armor = (Armor) newEquip;
                    return true;
                } else {
                    lootBag.removeItem(selection);
                    lootBag.addItem(this.armor);
                    this.armor = (Armor) newEquip;
                    return true;
                }
            }

            if (newEquip instanceof Weapon) {
                if (this.weapon == null) {
                    lootBag.removeItem(selection);
                    this.weapon = (Weapon) newEquip;
                    return true;
                } else {
                    lootBag.removeItem(selection);
                    lootBag.addItem(this.weapon);
                    this.weapon = (Weapon) newEquip;
                    return true;
                }
            }
        }

        return false;
    }

    public int[] getAllStats(){
        int[] stats = {this.getHp(), this.getMaxHp(), this.getPower(), this.getDefense(), this.mp, this.maxMp,
                this.experience, this.level, this.currentGold, this.currentAttributes};

        System.out.println(this.getMaxHp());
        return stats;
    }

    public ArrayList<Equipment> getItems(){
        return this.lootBag.items;
    }

    public int getCurrentGold() {
        return currentGold;
    }

    public void subtractGold(int amount) {
        this.currentGold -= amount;
    }

    public int getCurrentAttributes() {
        return currentAttributes;
    }

    public int getLevel() {
        return level;
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

    @Override
    public String toString() {
        String armor;
        String weapon;
        if (this.armor == null) {
            armor = "   Armor: 0";
        } else {
            armor = "   Armor: " + this.armor.getStat();
        }

        if (this.weapon == null) {
            weapon = "   Weapon: 0";
        } else {
            weapon = "   Weapon: " + this.weapon.getStat();
        }
        return "Your Characters Current Stats:  HP: " + this.getHp() + "   Power: "
                + this.getPower() + "   MP: " + this.mp + armor + weapon + "   Gold: " + currentGold;
    }


    class LootBag {
        private int slots;
        private ArrayList<Equipment> items;

        public LootBag(int bagSize) {
            this.slots = bagSize;
            items = new ArrayList();
        }

        public LootBag(int bagSize, ArrayList<Equipment> bag){
            this.slots = bagSize;
            this.items = bag;
        }

        public void addItem(Equipment equipment) {
            if (items.size() < slots) {
                items.add(equipment);
            } else {
                manageBag(equipment);
            }
        }

        public void removeItem(int selection) {
            items.remove(selection);
        }

        public void printBagItem(int selection) {
            System.out.println(items.get(selection));
        }

        public Equipment retrieveItem(int selection) {
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
            if (items.size() > 0) {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ") " + items.get(i));
                }
            } else {
                System.out.println("You have no items in your bag!");
            }

        }

        private int getAmountOfItmes() {
            return items.size();
        }

    }
}
