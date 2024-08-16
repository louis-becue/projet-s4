package zombicide.item.items;

import zombicide.actor.survivor.*;
import zombicide.item.Item;

/**
 * class for AidKit
 */
public class AidKit extends Item {

    /**
     * Constructor for class AidKit
     * @param possessor the survivor who held the item
     */
    public AidKit(Survivor possessor){
        super(possessor);
        this.canOpenDoor = false;
    }

    /**
     * @param survivor the survivor who wants to use this item
     * @throws ItemUsesException exception if the player is already at full health points
     */
    public void effect(Survivor survivor) throws ItemUsesException{
        if (this.possessor.getZone().getSurvivors().contains(survivor)){
            if (survivor.getHealth() < 5){
                survivor.increaseHealth(1);
                this.removeItem();
            }
            else{
                throw new ItemUsesException("The player is already at full health");
            }
        }
    }

    /**
     * @return name of the item
     */
    public String toString() {
        return "Aid Kit";
    }
}