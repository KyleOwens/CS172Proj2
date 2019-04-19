package Equipment;

import java.util.Random;

public class Armor extends Equipment {
    private Random random = new Random();

    private Armor(int maxArmor){
        this.setItemSlot("Armor");
        this.setStat(random.nextInt(maxArmor));
        this.setLevelRequirement(this.getStat()/5);
    }

    public static Armor makeRandomArmor(int maxArmor){
        return new Armor(maxArmor);
    }

//    public String toString(){
//        return "Armor: [Level Requirement: " + this.getLevelRequirement() + "  Armor: " + this.getStat() + "]"
//    }
}
