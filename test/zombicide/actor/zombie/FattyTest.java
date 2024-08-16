package zombicide.actor.zombie;

import org.junit.jupiter.api.Test;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;

import static org.junit.jupiter.api.Assertions.*;

class FattyTest {
    @Test
    void testCreationOfFattyZombie() {
        Zone zone = new Room(new Position(0,0));
        Zombie zombie = new Fatty();
        // Quick reminder : Abomination must have 4 of health, 2 of base damage, and 1 action per turn
        assertEquals(zombie.getHealth(),4);
        assertEquals(zombie.getDamage(),2);
        assertEquals(zombie.getNumberActions(),1);
        assertEquals(zombie.getType(),ZombieType.FATTY);

    }

}