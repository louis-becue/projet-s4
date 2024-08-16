package zombicide.actor.survivor.action;

import zombicide.Game;
import zombicide.actor.survivor.Survivor;
import zombicide.item.Item;
import zombicide.zone.Room;

import java.util.ArrayList;
import java.util.List;

public class Seek extends Action{

    /**
     * Constructor for class Seek
     */
    public Seek() {
        this.title = "Seek";
        this.cost = 1;
    }

    /**
     * Display all items on ground of the survivor's zone
     *
     * @param survivor the survivor who executes the action
     * @throws ActionExecuteException exception if the survivor can't execute the action for any reason
     */
    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            Room room = (Room) survivor.getZone();
            if (!room.getLootOnGround().isEmpty()) {  //If there is no items on ground we throw a new exception
                System.out.println("Loot on ground :");
                List<Item> items = new ArrayList<>(room.getLootOnGround());
                    for (int j = 0; j < items.size(); j++) {
                        System.out.print(j+1 + "-" + items.get(j).toString() + "  ");
                    }
                    if (survivor.getItems().size() < survivor.getMaxBagSize()) {
                        Item choosedItem = (Item) Game.lc.choose("\nChoix de l'item à ramasser :",items);
                        survivor.addItem(choosedItem);
                        survivor.useActionPoints(this.cost);
                    }
                    else{
                        System.out.println("\nLe survivant a trop d'objets dans son sac");
                        Item choosedItem = (Item) Game.lc.choose("Choix de l'item à déposer :",survivor.getItems());
                        survivor.removeItem(choosedItem);
                        Item item = (Item) Game.lc.choose("Choix de l'item à ramasser :",items);
                        survivor.addItem(choosedItem);
                        survivor.useActionPoints(this.cost);
                    }
            }
            else{
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Seek", "No items on ground..."));

            }
        } else {
            if (!this.enoughActionPoints(survivor)) {
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Seek", "Not enough action points"));
            } else if (!this.isSurvivorInRoom(survivor)) {
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Seek", "Only usable in room !"));
            }
        }
    }


    /**
     * Check if the given survivor fulfill all conditions to execute the action
     *
     * @param survivor tne survivor to execute the action
     * @return True if the survivor has mor action points than it costs to execute the action and the survivor's zone is a room
     */
    public boolean canDoAction(Survivor survivor) {
        return (survivor.getRemainingActionPoints() >= this.cost && (survivor.getZone() instanceof Room));
    }

    /**
     * Check if the given survivor has enough action points to execute the action
     *
     * @param survivor the survivor we want to check the remaining action points
     * @return True if the survivor has enough actions points to execute the action
     */
    private boolean enoughActionPoints(Survivor survivor){
        return (survivor.getRemainingActionPoints()>=this.cost);
    }

    /**
     * Check if the given survivor is in a Room
     *
     * @param survivor the survivor we want to check the instance of his zone
     * @return True if the survivor's zone is a Room
     */
    private boolean isSurvivorInRoom(Survivor survivor){
        return ((survivor.getZone() instanceof Room));

    }

    public String toString(){
        return this.title;
    }
}
