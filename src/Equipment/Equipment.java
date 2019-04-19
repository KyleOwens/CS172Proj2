package Equipment;

public abstract class Equipment {

    private String itemSlot;
    private int levelRequirement;
    private int stat;

    public String getItemSlot() {
        return itemSlot;
    }

    public void setItemSlot(String itemSlot) {
        this.itemSlot = itemSlot;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    public String toString(){
        return "[Level Requirement: " + this.levelRequirement + "] ["
                + this.itemSlot + ": " + this.stat + "]";
    }

}
