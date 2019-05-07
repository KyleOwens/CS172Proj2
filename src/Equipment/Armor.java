package Equipment;

import java.util.Random;

public class Armor extends Equipment {
    private Random random = new Random();

    private Armor(int maxArmor){
        this.setItemSlot("Armor");
        this.setStat(random.nextInt(maxArmor));
        this.setLevelRequirement(this.getStat()/5);
    }

    private Armor(int armor, int levelRequirement){
        this.setItemSlot("Armor");
        this.setStat(armor);
        this.setLevelRequirement(levelRequirement);
    }

    public static Armor makeRandomArmor(int maxArmor){
        return new Armor(maxArmor);
    }

    public static Armor makeArmorWithStats(int armor, int levelRequirement){
        return new Armor(armor, levelRequirement);
    }

//    public String toString(){
//        return "Armor: [Level Requirement: " + this.getLevelRequirement() + "  Armor: " + this.getStat() + "]"
//    }
}
