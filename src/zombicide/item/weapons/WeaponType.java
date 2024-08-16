package zombicide.item.weapons;

public enum WeaponType {
    RIFLE(2, 4, 1, 3, 1, false, "Rifle"),
    AXE(1, 4, 0, 0, 2, true, "Axe"),
    CHAINSAW(2, 5, 0, 0, 3, true, "Chainsaw"),
    GUN(1, 4, 0, 1, 1, false, "Gun"),
    CROWBAR(1, 4, 0, 0, 1, true, "Crowbar"),
    ADMIN(1, 1, 0, 10, 42, true, "ADMIN");

    private final int nbRolls;
    private final int threshold;
    private final int minRange;
    private final int maxRange;
    private final int damage;
    private final boolean canOpenDoor;

    public final String name;

    WeaponType(int nbRolls, int threshold, int minRange, int maxRange, int damage, boolean canOpenDoor, String name) {
        this.nbRolls = nbRolls;
        this.threshold = threshold;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.damage = damage;
        this.canOpenDoor = canOpenDoor;
        this.name = name;
    }

    public int getNbRolls() {
        return nbRolls;
    }
    public int getThreshold() {
        return threshold;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getDamage() {
        return damage;
    }

    public boolean canOpenDoor() {
        return canOpenDoor;
    }

    public String toString() {
        return name;
    }
}
