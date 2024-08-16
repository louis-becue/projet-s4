package zombicide.actor.survivor.action;

import org.junit.jupiter.api.Test;
import zombicide.Game;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.RandomBoard;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MakeNoiseTest {

    @Test
    void testDoAction() throws ActionExecuteException {
        ArrayList<Role> roles= new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Marcel",roles);

        Zone zone = new Room(new Position(0,0));
        zone.addActorInZone(survivor);

        assertEquals(zone.getNoiseLevel(),0);
        MakeNoise action1 = new MakeNoise();
        action1.doAction(survivor);
        // MakeNoise increase noise level by 4
        assertEquals(zone.getNoiseLevel(),4);

    }
}