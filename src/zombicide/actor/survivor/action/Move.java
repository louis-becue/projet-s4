package zombicide.actor.survivor.action;

import zombicide.Game;
import zombicide.actor.survivor.Survivor;
import zombicide.item.items.HealPotion;
import zombicide.util.Direction;
import zombicide.zone.Zone;
import zombicide.zone.room.Pharmacy;

import java.util.ArrayList;
import java.util.List;

public class Move extends Action {
    /**
     * Constructor for class Move
     */
    public Move() {
        this.cost = 1;
        this.title = "Move";
    }

    /**
     * Move the given survivor
     * @param survivor the survivor who executes the action
     * @throws ActionExecuteException exception if the survivor can't execute action for any reasons
     */
    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            List<Direction> permittedDirections = new ArrayList<>();
            for (Direction direction : Direction.values()) {
                if (survivor.getZone().getBooleanInDirection(direction)) {
                    permittedDirections.add(direction);
                }
            }
            Direction direction = (Direction) Game.lc.choose("Direction du déplacement :", permittedDirections);
            executeAction(survivor, direction);
        } else {
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Move", "Not enough action points"));
        }
    }

    /**
     * Execute action for given survivor. This moves the survivor in the given direction
     * @param survivor the survivor to move
     * @param direction the direction to go
     */
    public void executeAction(Survivor survivor, Direction direction) throws ActionExecuteException {
        if (survivor.getZone().getBooleanInDirection(direction)) {
            survivor.move(direction);
            survivor.useActionPoints(this.cost);
            Zone zone = survivor.getZone();
            if (zone instanceof Pharmacy){
                ((Pharmacy) zone).addItemOnGround(new HealPotion(null));
            }
        } else {
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Move", "Cannot go in this direction"));
        }
    }
}
