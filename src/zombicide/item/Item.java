package zombicide.item;

import zombicide.actor.survivor.Survivor;
import zombicide.item.items.ItemUsesException;

/**
 * class for Item
 */
public abstract class Item {
    protected boolean canOpenDoor;
    protected Survivor possessor;
    protected int noiseLevel;

    /**
     * Constructor for Item
     * @param possessor Survivor who hold the item
     */
    public Item(Survivor possessor){
        this.possessor = possessor;
        this.noiseLevel = 0;
    }

    public Item() {

    }

    /**
     * @return the survivor who held the item
     */
    public Survivor getPossessor(){
        return this.possessor;
    }

    /**
     * @param survivor the survivor who will hold the item
     */
    public void setPossessor(Survivor survivor) {
        this.possessor = survivor;
    }

    /**
     * @return boolean, true: if the item can open doors, false: else
     */
    public boolean canOpenDoor(){
        return this.canOpenDoor;
    }

    /**
     * method to use item
     * @throws ItemUsesException exception in case of bad uses
     */
    public void effect() throws ItemUsesException {

    }

    public void removeItem(){
        this.possessor.removeItemInHand();
    }
}
