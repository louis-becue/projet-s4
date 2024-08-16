package zombicide.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.Map.entry;

import zombicide.item.Item;
import zombicide.item.Weapon;
import zombicide.item.items.*;
import zombicide.item.weapons.WeaponType;

/**
 * Enum for LootTable
 * Each type of Room that can spawn items has a Map of entries in which one item is associated to a weight.
 * The weight determines the chance for an item to spawn
 */
public enum LootTable {
    ROOM(Map.ofEntries(
            entry(new AidKit(null), .2),
            entry(new HealPotion(null), .25),
            entry(new MapItem(null), .1),
            entry(new InfraredGlasses(null), .1),
            entry(new MasterKey(null), .1),
            entry(new Weapon(WeaponType.RIFLE, null), .05),
            entry(new Weapon(WeaponType.AXE, null), .05),
            entry(new Weapon(WeaponType.CROWBAR, null), .05),
            entry(new Weapon(WeaponType.CHAINSAW, null), .05),
            entry(new Weapon(WeaponType.GUN, null), .05)
    )),
    PHARMACY(Map.ofEntries(
            entry(new HealPotion(null), 1.0)
    ));

    public static final int NITEMS_ROOM = 4;
    public static final int NITEMS_PHARMACY = 2;

    private final Map<Item, Double> table;

    /**
     * Constructor for class LootTable
     * @param table map of entries of items and weight
     */
    LootTable(Map<Item, Double> table) {
        this.table = table;
    }

    /**
     * @return the table
     */
    public Map<Item, Double> getTable() {
        return this.table;
    }

    /**
     * @return One random item in the table
     */
    public Item generateItem() {
        double totalWeight = 0.0;
        for (Double p : this.table.values()) {
            totalWeight += p;
        }

        List<Item> items = new ArrayList<>(this.table.keySet());

        int index = 0;
        for (double r = Math.random()*totalWeight; index < this.table.size()-1; ++index) {
            r -= this.table.get(items.get(index));
            if (r <= 0.0) break;
        }

        return items.get(index);
    }

    /**
     * @return list of random items in the table
     */
    public List<Item> generateItems(int maxBound) {
        List<Item> Items = new ArrayList<>();
        Random nbItemRandom = new Random();
        int nbItem = nbItemRandom.nextInt(1,maxBound);

        for (int i =0; i<nbItem; i++){
            Items.add(generateItem());
        }
        return Items;
    }
}
