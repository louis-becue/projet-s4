package zombicide.item;


import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.item.items.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ItemsTest {
    @Test
    void ItemsCreationTest(){
        // Creation of Survivor
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.BASE);
        Survivor survivor = new Survivor("Base",role);

        // Creation of Items
        HealPotion heal = new HealPotion(survivor);
        AidKit kit = new AidKit(survivor);
        MapItem map = new MapItem(survivor);
        InfraredGlasses glasses = new InfraredGlasses(survivor);
        MasterKey key = new MasterKey(survivor);

        // Test possessor
        assertEquals(heal.getPossessor(), survivor);
        assertEquals(kit.getPossessor(), survivor);
        assertEquals(map.getPossessor(), survivor);
        assertEquals(glasses.getPossessor(), survivor);
        assertEquals(key.getPossessor(), survivor);

        //Test OpenDoor
        assertFalse(heal.canOpenDoor());
        assertFalse(kit.canOpenDoor());
        assertFalse(map.canOpenDoor());
        assertFalse(glasses.canOpenDoor());
        assertTrue(key.canOpenDoor());
    }
}
