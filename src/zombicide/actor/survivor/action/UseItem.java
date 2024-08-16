package zombicide.actor.survivor.action;

import zombicide.Game;
import zombicide.actor.survivor.Survivor;
import zombicide.item.Item;
import zombicide.board.Board;
import zombicide.item.items.*;
import zombicide.util.Direction;
import zombicide.zone.Street;

import java.util.ArrayList;
import java.util.List;

public class UseItem extends Action {

    private final Board board;

    public UseItem(Board board) {
        this.cost = 1;
        this.title = "UseItem";
        this.board = board;
    }

    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            Item itemInHand = survivor.getItemInHand();
            try {
                if (itemInHand instanceof MapItem item) {
                    item.effect(board);
                } else if (itemInHand instanceof InfraredGlasses item) {
                    item.effect(board);
                } else if (itemInHand instanceof AidKit item) {
                    item.effect((Survivor) Game.lc.choose("Choose player to heal : ", survivor.getZone().getSurvivors()));
                } else if (itemInHand instanceof MasterKey item) {
                    item.effect((Direction) Game.lc.choose("Choose direction to open door :", constructPermittedDirectionsList(survivor)));
                } else {
                    itemInHand.effect();
                }
            } catch (ItemUsesException e) {
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("UseItem", "Error while using item " + itemInHand));
            }
        } else {
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("UseItem", "Not enough action points"));
        }
    }

    private List<Direction> constructPermittedDirectionsList(Survivor survivor) {
        List<Direction> permittedDirections = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (!((survivor.getZone() instanceof Street) || (survivor.getZone().getZoneInDirection(direction) instanceof Street)))
                permittedDirections.add(direction);
        }
        return permittedDirections;
    }
}
