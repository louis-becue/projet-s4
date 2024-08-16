package zombicide.actor.zombie;

import org.junit.jupiter.api.Test;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;

import static org.junit.jupiter.api.Assertions.*;

class WalkerTest {

    @Test
    void testCreationOfWalkerZombie() {
        Zone zone = new Room(new Position(0,0));
        Zombie zombie = new Walker();

        // Quick reminder : Abomination must have 2 of health, 2 of base damage, and 2 action per turn
        assertEquals(zombie.getHealth(),1);
        assertEquals(zombie.getDamage(),1);
        assertEquals(zombie.getNumberActions(),1);
        assertEquals(zombie.getType(),ZombieType.WALKER);

    }
}