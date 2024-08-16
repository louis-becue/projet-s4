package zombicide;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.RandomBoard;
import zombicide.zone.street.Manhole;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testSpawnZombies() {
        ArrayList<Role> roles1 = new ArrayList<Role>();
        roles1.add(Role.BASE);
        Survivor survivor1 = new Survivor("Valentin",roles1);
        Survivor survivor2 = new Survivor("Hugo",roles1);
        Survivor survivor3 = new Survivor("Paul",roles1);
        Survivor survivor4 = new Survivor("Louis",roles1);
        ArrayList<Survivor> survivors = new ArrayList<>();
        survivors.add(survivor1);
        survivors.add(survivor2);
        survivors.add(survivor3);
        survivors.add(survivor4);

        Board board = new RandomBoard(10,10);

        Game game = new Game(board, survivors, true);
        game.spawnZombies(board.getManholes().size());

        // here, the 4 survivors are level 1 : average is 1, divided by 3 and rounded up, it's 1. There must be 1 zombie / manhole
        ArrayList<Manhole> manholes = board.getManholes();
        for(Manhole manhole : manholes){
            assertEquals(1, manhole.getZombies().size());
        }

    }
}