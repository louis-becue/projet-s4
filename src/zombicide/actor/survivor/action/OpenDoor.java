package zombicide.actor.survivor.action;

import zombicide.Game;
import zombicide.actor.survivor.Survivor;
import zombicide.item.Item;
import zombicide.util.Direction;
import zombicide.zone.Street;
import zombicide.zone.Zone;

import java.util.List;
import java.util.ArrayList;

public class OpenDoor extends Action{
    private final String title = "Open door";

    /**
     * Constructor for class OpenDoor
     */
    public OpenDoor(){
        this.cost = 1;
    }

    /**
     * Check if the given survivor can open a door.
     *          - If true, the survivor open the door
     *          - Else
     * @param survivor the survivor who executes the action
     * @throws ActionExecuteException exception if survivor can't execute this action for any reasons
     */
    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            Item item = survivor.getItemInHand();
            if (item.canOpenDoor()) {
                List<Direction> permittedDirections = this.constructPermittedDirectionsList(survivor);
                Direction direction = (Direction) Game.lc.choose("Direction de la porte à ouvrir :", permittedDirections);
                this.executeAction(survivor, direction);
                survivor.useActionPoints(this.cost);
            } else {
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("OpenDoor", "Cannot open door with this item in hand"));
            }
        } else {
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("OpenDoor", "Not enough action points"));
        }
    }

    /**
     * Give the list of directions in which the survivor can open the door
     *
     * @param survivor the survivor who executes the action
     * @return the list of directions
     */
    private List<Direction> constructPermittedDirectionsList(Survivor survivor) {
        List<Direction> permittedDirections = new ArrayList<>();
        Zone currentZone = survivor.getZone();
        for (Direction direction : Direction.values()) {
            Zone targetZone = currentZone.getZoneInDirection(direction);
            if (!currentZone.getBooleanInDirection(direction) && !(targetZone instanceof Street && currentZone instanceof Street)) {
                permittedDirections.add(direction);
            }
        }
        return permittedDirections;
    }

    /**
     * Make the given survivor to open the door in the given direction
     *
     * @param survivor the survivor who executes the action
     * @param direction the direction in which the survivor wants to open the door
     */
    public void executeAction(Survivor survivor, Direction direction) {
        survivor.getZone().openDoor(direction);
    }
    public String toString(){
        return this.title;
    }
}