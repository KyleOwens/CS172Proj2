package Characters;

import java.util.Random;

public class Enemy extends Character{
    private static Random random = new Random();

    public Enemy(int maxHp, int maxPower){
        this.setHp(random.nextInt(maxHp) + 1);
        this.setPower(random.nextInt(maxPower) + 1);
    }

    public static Enemy createRandomEnemy(int maxHp, int maxPower){
        return new Enemy(maxHp, maxPower);
    }

    @Override
    public void attack(Character character) {
        int hit = this.getPower();
        character.setHp(character.getHp() - hit);
    }

    @Override
    public String toString(){
        return "HP: " + this.getHp() + "   Power: " + this.getPower();
    }
}
