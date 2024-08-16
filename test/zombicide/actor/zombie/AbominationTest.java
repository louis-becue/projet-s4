package zombicide.actor.zombie;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AbominationTest {

    @Test
    void testCreationOfAbominationZombie() {
        Zone zone = new Room(new Position(0,0));
        Zombie zombie = new Abomination();

        // Quick reminder : Abomination must have 6 of health, 3 of base damage, and 1 action per turn
        assertEquals(zombie.getHealth(),6);
        assertEquals(zombie.getDamage(),3);
        assertEquals(zombie.getNumberActions(),1);
        assertEquals(zombie.getType(),ZombieType.ABOMINATION);

    }
}