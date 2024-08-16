package zombicide.actor.survivor.action;

import zombicide.Game;
import zombicide.actor.survivor.Survivor;
import zombicide.item.Item;

public class TakeInHand extends Action {
    public TakeInHand() {
        this.cost = 1;
        this.title = "TakeInHand";
    }

    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            Item item = (Item) Game.lc.choose("Item à prendre en main :", survivor.getItems());
            survivor.setItemInHand(item);
            if (item != null)
                System.out.println("New item in " + survivor + "'s hand : " + item);
        } else {
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("TakeInHand", "Not enough action points"));
        }

    }
}
