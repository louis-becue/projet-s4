package zombicide.item.items;

import zombicide.actor.survivor.*;
import zombicide.item.Item;

/**
 * class for HealPotion
 */
public class HealPotion extends Item {

    /**
     * Constructor for HealPotion
     * @param possessor the survivor who held the item
     */
    public HealPotion(Survivor possessor){
        super(possessor);
        this.canOpenDoor = false;
    }

    /**
     * method to use item
     * @throws ItemUsesException exception if the player is already at full health points
     */
    public void effect() throws ItemUsesException {
        if (this.possessor.getHealth() < 5){
            this.possessor.increaseHealth(1);
            this.removeItem();
        }
        else{
            throw new ItemUsesException("The player is already at full health");
        }
    }

    /**
     * @return name of item
     */
    public String toString() {
        return "Heal Potion";
    }
}