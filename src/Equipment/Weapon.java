package Equipment;

import java.util.Random;

public class Weapon extends Equipment {
    private Random random = new Random();

    private Weapon(int maxArmor){
        this.setItemSlot("Weapon");
        this.setStat(random.nextInt(maxArmor));
        this.setLevelRequirement(this.getStat()/5);
    }

    public static Weapon makeRandomWeapon(int maxPower){
        return new Weapon(maxPower);
    }

}