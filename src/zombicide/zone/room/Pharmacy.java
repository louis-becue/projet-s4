package zombicide.zone.room;

import java.util.HashMap;

import zombicide.item.Item;
import zombicide.item.items.HealPotion;
import zombicide.util.Position;
import zombicide.zone.Room;

/**
 * Represents a pharmacy room, extending the Room class.
 */


public class Pharmacy extends Room{
	protected HashMap<Item, Integer> spawner;
	protected Position p;

    /**
     * Constructor for the Pharmacy class.
     * @param p The position of the pharmacy.
     */

    public Pharmacy(Position p){
        super(p);
        this.spawner = new HashMap<>();
    }

    /**
     * Adds a healing potion to the loot on the ground in the pharmacy.
     */
    public void addPotionOnGround(){
        this.lootOnGround.add(new HealPotion(null));
    }
}