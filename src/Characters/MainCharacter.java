package Characters;

import Equipment.Armor;
import Equipment.Weapon;

public class MainCharacter extends Character{
    private Weapon weapon;
    private Armor armor;

    private MainCharacter(int hp, int power){
        this.setHp(hp);
        this.setPower(power);
    }

    public static MainCharacter createCharacterWithStats(int hp, int power){
        return new MainCharacter(hp, power);
    }

    @Override
    public void attack(Character character) {
        int hit = this.getPower();
        character.setHp(character.getHp() - hit);
    }

    @Override
    public String toString(){
        return "Your Characters Current Stats:  HP: " + this.getHp() + "   Power: " + this.getPower();
    }
}
