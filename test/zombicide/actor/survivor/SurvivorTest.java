package zombicide.actor.survivor;

import org.junit.jupiter.api.Test;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.item.Item;
import zombicide.item.items.HealPotion;
import zombicide.item.items.ItemUsesException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SurvivorTest {

    @Test
    void testCreationOfBaseSurvivor() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.BASE);
        Survivor survivor = new Survivor("Base", role);
        assertEquals(survivor.getHealth(), 5);
        assertEquals(survivor.getLevel(), 1);
        assertEquals(survivor.getBaseActionPoints(), 3);
        assertEquals(survivor.getBaseDamage(), 0);
        assertEquals(survivor.getDiceThrows(), 1);
    }

    @Test
    void testCreationOfHealerSurvivor() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.HEALER);
        Survivor survivor = new Survivor("Healer", role);
        assertEquals(survivor.getHealth(), 5);
        assertEquals(survivor.getLevel(), 1);
        assertEquals(survivor.getBaseActionPoints(), 3);
        assertEquals(survivor.getBaseDamage(), 0);
        assertEquals(survivor.getDiceThrows(), 1);
    }

    @Test
    void testCreationOfNosySurvivor() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.NOSY);
        Survivor survivor = new Survivor("Nosy", role);
        assertEquals(survivor.getHealth(), 5);
        assertEquals(survivor.getLevel(), 1);
        assertEquals(survivor.getBaseActionPoints(), 3);
        assertEquals(survivor.getBaseDamage(), 0);
        assertEquals(survivor.getDiceThrows(), 1);
    }

    @Test
    void testCreationOfFighterSurvivor() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.FIGHTER);
        Survivor survivor = new Survivor("Fighter", role);
        assertEquals(survivor.getHealth(), 5);
        assertEquals(survivor.getLevel(), 1);
        assertEquals(survivor.getBaseActionPoints(), 3);
        assertEquals(survivor.getBaseDamage(), 1);
        assertEquals(survivor.getDiceThrows(), 1);
    }

    @Test
    void testCreationOfLuckySurvivor() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.LUCKY);
        Survivor survivor = new Survivor("Lucky", role);
        assertEquals(survivor.getHealth(), 5);
        assertEquals(survivor.getLevel(), 1);
        assertEquals(survivor.getBaseActionPoints(), 3);
        assertEquals(survivor.getBaseDamage(), 0);
        assertEquals(survivor.getDiceThrows(), 2);
    }


    @Test
    void testAddItem() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Batman", roles);
        // His bag must be empty
        assertEquals(0, survivor.getItems().size());
        Item item = new HealPotion(survivor);
        survivor.addItem(item);
        // After adding 1 item, his bag must be of size 1
        assertEquals(survivor.getItems().size(), 1);
    }

    @Test
    void testUpgradeLevel() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Jean", roles);
        // Survivor level must be 1 at creation
        assertEquals(survivor.getLevel(), 1);
        survivor.upgradeLevel();
        // Survivor level after increasing by 1 must be 2
        assertEquals(survivor.getLevel(), 2);
    }

    @Test
    void testIsAttacked() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Marcel", roles);
        // Survivor health must be 5
        assertEquals(survivor.getHealth(), 5);
        survivor.isAttacked(4);
        // Survivor health must be 1 after taking 4 damage
        assertEquals(survivor.getHealth(), 1);
    }

    @Test
    void testIncreaseHealth() {

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Azerty", roles);
        // Survivor health must be 5
        assertEquals(survivor.getHealth(), 5);
        survivor.increaseHealth(2);
        // Survivor health must be 7 after incrasing his health by 2
        assertEquals(survivor.getHealth(), 7);

    }

    @Test
    void testItemInHand() throws ItemUsesException {
        // Creation of dependencies
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Azerty", roles);
        // survivor should not have an item in his hand
        assertNull(survivor.getItemInHand());
        Item item = new HealPotion(survivor);
        survivor.addItem(item);
        survivor.setItemInHand(item);
        // Now he should have a potion in his hand
        assertEquals(survivor.getItemInHand(), item);
        // If he uses it on himself, the potion must destroy and be removed from his hand
        // For that, we need him to take one damage
        survivor.isAttacked(1);
        survivor.useItem(null, survivor, null);
        assertNull(survivor.getItemInHand());
    }
}