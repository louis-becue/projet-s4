package zombicide.actor.survivor.action;
import zombicide.actor.zombie.Zombie;
import zombicide.util.Direction;
import zombicide.zone.*;
import zombicide.actor.survivor.Survivor;

public class LookAround extends Action{
    public LookAround() {
        this.cost = 0;
        this.title = "LookAround";
    }

    /**
     * Displays information about the zone of the survivor. It shows the survivors, the zombies, and the possible direction
     * @param survivor The survivor on which we use the action
     */
    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            Zone zone = survivor.getZone();
            // Shows survivors in the zone of the survivor
            String survivors = "Survivors : ";
            for (Survivor survivorInZone : zone.getSurvivors()) {
                survivors += (survivorInZone.toString() + " ");
            }
            System.out.println(survivors);

            // Shows zombies in the zone of the survivor
            String zombies = ("Zombies : ");
            for (Zombie zombieInZone : zone.getZombies()) {
                zombies += (zombieInZone.toString() + " ");
            }
            System.out.println(zombies);
            // Show the possible directions the survivor can go through
            String possActions = ("Possible directions : ");
            if (zone.getBooleanInDirection(Direction.EAST)) {
                possActions += ("East ");
            }
            if (zone.getBooleanInDirection(Direction.WEST)) {
                possActions += ("West ");
            }
            if (zone.getBooleanInDirection(Direction.NORTH)) {
                possActions += ("North ");
            }
            if (zone.getBooleanInDirection(Direction.SOUTH)) {
                possActions += ("South ");
            }
            System.out.println(possActions);
        } else {
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("LookAround", "Not enough action points"));
        }

    }
}
