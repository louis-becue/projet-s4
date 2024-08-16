package zombicide.item.items;

import zombicide.actor.survivor.*;
import zombicide.board.*;
import zombicide.item.Item;
import zombicide.util.Display;

/**
 * class for InfraredGlasses
 */
public class InfraredGlasses extends Item{

    /**
     * Constructor for class InfraredGlasses
     * @param possessor the survivor who held the item
     */
    public InfraredGlasses(Survivor possessor){
        super(possessor);
        this.canOpenDoor = false;
    }

    /**
     * method tu use the item
     */
    public void effect(Board board){
        Display displayBoard = new Display(board);
        displayBoard.displayNeighbours(this.getPossessor().getZone(), true);
        this.removeItem();
    }

    /**
     * @return name of item
     */
    public String toString() {
        return "Infrared Glasses";
    }
}