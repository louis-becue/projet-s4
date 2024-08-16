package zombicide.actor.survivor.action;

import zombicide.Game;
import zombicide.actor.zombie.Zombie;
import zombicide.item.Weapon;
import zombicide.util.Direction;
import zombicide.zone.Zone;
import zombicide.actor.survivor.Survivor;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Attack extends Action {
    public Attack() {
        this.cost = 1;
        this.title = "Attack";
    }

    /**
     * Execute the action with the survivor
     * @param survivor the survivor executing the action
     */
    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            if (survivor.getItemInHand() instanceof Weapon weapon) {
                Direction directionToShoot = (Direction) Game.lc.choose("Direction du tir : ", Arrays.asList(Direction.values()));
                List<Zone> zone = reachableZones(directionToShoot, weapon);
                List<Zombie> zombies = reachableZombies(zone);

                if (!zombies.isEmpty()) {
                    Zombie target = (Zombie) Game.lc.choose("Zombie à attaquer : ", zombies);

                    try {
                        executeAction(survivor, weapon, target);
                    } catch (ActionExecuteException e) {
                        if (!survivor.getZone().getHostile()){
                            System.out.println("Cannot execute Attack action : You cannot attack in this room");
                        }
                        else{
                            System.out.println("Cannot execute Attack action : Not enough action points");
                        }
                    }
                } else {
                    System.out.println("There was no zombies.. You shot in the wind !");
                }
            } else {
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Attack", "Survivor doesn't have weapon in hand"));
            }
            survivor.useActionPoints(this.cost);
        } else {
            if (!survivor.getZone().getHostile()){
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Attack", "Cannot attack in this room"));
            }
            else{
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Attack", "Not enough action points"));
            }

        }
    }

    /**
     * Method for a given survivor execute the attack action
     *
     * @param survivor the survivor who attack
     * @param weapon the weapon used to attack
     * @param target the zombie to attack
     *
     * @throws ActionExecuteException the exception if it can't reach target for any reasons
     */
    public void executeAction(Survivor survivor, Weapon weapon, Zombie target) throws ActionExecuteException {
        System.out.println("Attacking " + target);
        for (int i = 0; i < weapon.getNbRolls() ; i++) {
            attack(survivor, weapon, target);
        }
    }

    /**
     * Method for a given survivor to attack a given zombie
     *
     * @param survivor the survivor who attack
     * @param weapon the survivor's weapon
     * @param target the zombie to attack
     */
    private void attack(Survivor survivor, Weapon weapon, Zombie target) {
        int result = Game.dice.roll();
        if (result >= weapon.getThreshold()) {
            System.out.println("Bullseye ! Zombie lost " + weapon.getDamage() + " hp");
            survivor.attack(weapon, target);
        } else {
            System.out.println("Missed shot !");
        }
    }

    /**
     * Return all zones reachable by a weeapon in a specified direction
     * @param direction the direction to shoot in
     * @param weapon the weapon used (range differs)
     * @return a list of reachable zones
     */
    protected List<Zone> reachableZones(Direction direction, Weapon weapon) {
        List<Zone> reachable = new ArrayList<>();
        Zone currentZone = weapon.getPossessor().getZone();
        int minRange = weapon.getMinRange();
        int maxRange = weapon.getMaxRange();

        for (int i = 0; i <= maxRange; i++) {
            if (i >= minRange)
                reachable.add(currentZone);
            Zone nextZone = currentZone.getZoneInDirection(direction);
            if (nextZone == null || (!currentZone.getBooleanInDirection(direction)))
                return reachable;
            currentZone = nextZone;
        }

        return reachable;
    }

    /**
     * Return all zombies in given zones
     * @param zones the zones to find zombies
     * @return a list of zombies in those zones
     */
    protected List<Zombie> reachableZombies(List<Zone> zones) {
        List<Zombie> reachable = new ArrayList<>();
        for (Zone zone : zones) {
            reachable.addAll(zone.getZombies());
        }
        return reachable;
    }
    protected boolean canDoAction(Survivor survivor) {
        return (super.canDoAction(survivor) && (survivor.getZone().getHostile()));
    }
}
