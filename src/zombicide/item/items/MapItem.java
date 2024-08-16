package zombicide.item.items;

import zombicide.actor.survivor.*;
import zombicide.board.*;
import zombicide.item.Item;

/**
 * class for MapItem
 */
public class MapItem extends Item {
    protected int noiseLevel;

    /**
     * Constructor for MapItem
     * @param possessor the survivor who held the item
     */
    public MapItem(Survivor possessor){
        super(possessor);
        this.noiseLevel = 2;
        this.canOpenDoor = false;
    }

    /**
     * @param board the Board we want to see the zone around the player
     */
    public void effect(Board board){
        board.display(false);
        this.getPossessor().getZone().increaseNoiseLevel(this.noiseLevel);
        this.removeItem();
    }

    /**
     * @return name of item
     */
    public String toString() {
        return "Map";
    }
}