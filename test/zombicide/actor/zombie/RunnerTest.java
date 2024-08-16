package zombicide.actor.zombie;

import org.junit.jupiter.api.Test;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;

import static org.junit.jupiter.api.Assertions.*;

class RunnerTest {
    @Test
    void testCreationOfRunnerZombie() {
        Zone zone = new Room(new Position(0,0));
        Zombie zombie = new Runner();

        // Quick reminder : Abomination must have 2 of health, 2 of base damage, and 2 action per turn
        assertEquals(zombie.getHealth(),2);
        assertEquals(zombie.getDamage(),1);
        assertEquals(zombie.getNumberActions(),2);
        assertEquals(zombie.getType(),ZombieType.RUNNER);

    }
}