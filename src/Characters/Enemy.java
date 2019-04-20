package Characters;

import java.util.Random;

public class Enemy extends Character {
    private static Random random = new Random();
    private int awardExp;
    private int awardGold;
    private boolean loot;

    public Enemy(int maxHp, int maxPower) {
        int hp = random.nextInt(maxHp) + 1;
        this.setHp(hp);
        this.setMaxHp(hp);
        this.setPower(random.nextInt(maxPower) + 1);

        this.awardExp = this.getHp() + this.getPower();
        this.awardGold = this.getPower() * 10;
        this.loot = random.nextBoolean();
    }

    public static Enemy createRandomEnemy(int maxHp, int maxPower) {
        return new Enemy(maxHp, maxPower);
    }

    @Override
    public void attack(Character character) {
        int hit = this.getPower();
        hit -= getPlayerArmor((MainCharacter) character);

        if(hit < 0){
            hit = 0;
        }

        character.setHp(character.getHp() - hit);
        System.out.println(" hits you for " + hit);
    }

    private int getPlayerArmor(MainCharacter character) {
        if (character.getArmor() != null) {
            return character.getArmor().getStat();
        } else {
            return 0;
        }
    }

    public int getAwardGold() {
        return awardGold;
    }

    public boolean hasLoot() {
        return loot;
    }

    public int getAwardExp() {
        return awardExp;
    }

    @Override
    public String toString() {
        return "HP: " + this.getHp() + "   Power: " + this.getPower();
    }
}
