package zombicide.zone;
import zombicide.item.Item;
import zombicide.util.LootTable;
import zombicide.util.Position;
import zombicide.zone.room.Pharmacy;

import java.util.*;

/**
 * Represents a room in the game, extending the Zone class.
 */
public class Room extends Zone {
	protected List<Item> loot;
	protected List<Item> lootOnGround;
	protected HashMap<Item, Integer> spawner;

	/**
	 * Constructor for the Room class.
	 */
	public Room(Position p) {
		super(p);
		this.loot = new ArrayList<>();
		this.lootOnGround = new ArrayList<>();
		this.spawner = new HashMap<>();
	}

	/**
	 * Checks if the room is hostile and can be attacked.
	 *
	 * @return True if the room is hostile, False otherwise.
	 */
	public boolean canAttack() {
		return this.hostile;
	}

	/**
	 * Gets the list of loot available in the room.
	 *
	 * @return The list of loot available in the room.
	 */
	public List<Item> getLoot() {
		return this.loot;
	}


	/**
	 * Gets the list of loot currently on the ground in the room.
	 *
	 * @return The list of loot currently on the ground in the room.
	 */
	public List<Item> getLootOnGround() {
		return this.lootOnGround;
	}

	/**
	 * Set the items on the room's ground
	 * @param list the list of items to put in the room
	 */
	public void setLootOnGround(List<Item> list) {
		this.lootOnGround = list;
	}

	/**
	 * Add one item in the room's ground
	 * @param item item to add in the room's ground
	 */
	public void addItemOnGround(Item item) {
		this.lootOnGround.add(item);
	}

	/**
	 * Remove one item in the room's ground
	 * @param item the item to be removed from the room's ground
	 */
	public void removeItemOnGround(Item item) {
		this.lootOnGround.remove(item);
	}

	public void spawnItemsOnGround() {
		List<Item> spawnedItems;
		if (this instanceof Pharmacy) {
			spawnedItems = LootTable.PHARMACY.generateItems(LootTable.NITEMS_PHARMACY);
		} else {
			spawnedItems = LootTable.ROOM.generateItems(LootTable.NITEMS_ROOM);
		}
		this.setLootOnGround(spawnedItems);
	}
}