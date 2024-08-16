package zombicide.item.items;

import zombicide.item.Item;
import zombicide.actor.survivor.*;
import zombicide.util.Direction;
import zombicide.zone.Street;
import zombicide.zone.Zone;

/**
 * class for MasterKey
 */
public class MasterKey extends Item {

    /**
     * Constructor for class MasterKey
     * @param possessor the survivor who held the item
     */
    public MasterKey(Survivor possessor){
        super(possessor);
        this.canOpenDoor = true;
    }

    /**
     * Method to use item
     * @param d the direction of the zone we want to open the door (only if the current zone and the opposite zone are not instance of Street)
     */
    public void effect(Direction d){
        Zone currentZone = this.possessor.getZone();
        Zone targetZone = currentZone.getZoneInDirection(d);

        if (!(targetZone instanceof Street && currentZone instanceof Street)) {
            currentZone.openDoor(d);
            this.removeItem();
        }
    }
}
