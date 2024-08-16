package zombicide.actor.zombie;

import org.junit.jupiter.api.Test;
import zombicide.Game;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.RandomBoard;
import zombicide.board.type.TrainingBoard;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;

import java.io.OutputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ZombieTest {

    @Test
    void testAttack() {
        Zone zone = new Room(new Position(0, 0));
        Zombie zombie = new Abomination();
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Patrick Balkany", roles);
        zone.addActorInZone(survivor);
        zone.addActorInZone(zombie);
        zombie.attack(survivor);
        // A survivor has 5 base health, the abomination does at least 3 damage : remaining health must be less than 2
        assertTrue(survivor.getHealth() <= 2);
    }

    @Test
    void testZombiePlayTurnAndMove() {
        Board board = new TrainingBoard();
        Survivor survivor = new Survivor("Bruno",Role.HEALERROLES);
        ArrayList<Survivor> survivors = new ArrayList<Survivor>();
        survivors.add(survivor);
        Game game = new Game(board, survivors, true);
        survivor.move(Direction.NORTH);
        survivor.move(Direction.NORTH);
        game.spawnZombies(1);
        ArrayList<Zombie> zombies = board.getZone(new Position(2, 2)).getZombies();
        Zombie zombie = zombies.get(0);
        // We increase noise level in the north zone at pos (2, 0)
        board.getZone(new Position(2, 0)).increaseNoiseLevel(1);
        Zone noisiestZone = board.getNoisiestZone();
        zombie.playTurn(noisiestZone);
        // Zombie must be on the zone at pos (2, 1) as it is the zone between his initial zone and the noisiest zone
        assertEquals(zombie.getZone().getPosition(), new Position(2, 1));

        board.display();

    }
    @Test
    void testZombiePlayTurnAndAttack(){
        Board board = new TrainingBoard();
        Game game = new Game(board, new ArrayList<Survivor>(), true);
        Zombie zombie = new Abomination();
        board.getZone(new Position(2,2)).addActorInZone(zombie);

        Zone noisiestZone = board.getNoisiestZone();
        // Creation of the survivor
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Jacques",roles);
        assertEquals(survivor.getHealth(),5);
        // We put the survivor in the same room as the zombie
        board.getZone(new Position(2,2)).addActorInZone(survivor);
        // There, the zombie is supposed to attack the survivor, as there is one in the same room as him
        zombie.playTurn(noisiestZone);
        // The zombie does at least 3 damage, so we check if the survivor has lost health points
        assertTrue(survivor.getHealth()<=2);
        board.display();

    }
}