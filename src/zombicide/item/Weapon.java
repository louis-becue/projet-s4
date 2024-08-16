package zombicide.item;

import zombicide.actor.survivor.Survivor;
import zombicide.item.weapons.WeaponType;

public class Weapon extends Item {
    private final WeaponType type;
    public Weapon(WeaponType type, Survivor possessor) {
        super(possessor);
        this.type = type;
    }

    public Weapon(WeaponType type) {
        this.type = type;
    }

    public int getDamage() {
        return this.type.getDamage();
    }

    public int getNbRolls() {
        return this.type.getNbRolls();
    }

    public int getThreshold() {
        return this.type.getThreshold();
    }

    public int getMinRange() {
        return this.type.getMinRange();
    }

    public int getMaxRange() {
        return this.type.getMaxRange();
    }

    public boolean canOpenDoor() {
        return this.type.canOpenDoor();
    }

    public String toString() {
        return this.type.name;
    }
}