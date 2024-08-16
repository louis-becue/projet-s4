package zombicide.item.items;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Zone;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MasterKeyTest {

    @Test
    void MasterKeyOpenDoorTest() throws ItemUsesException {
        // Creation of Board
        Board board = new TrainingBoard();

        // Creation of Survivor
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.BASE);
        Survivor survivor = new Survivor("Base",role);

        // Creation of Zone
        Position p = new Position(0,0);
        Zone zone = board.getZone(p);
        zone.addActorInZone(survivor);

        // Creation of Item
        MasterKey key = new MasterKey(survivor);

        survivor.setItemInHand(key);

        // Creation of Direction
        Direction d = Direction.EAST;

        // Test
        assertFalse(board.getZone(p).getBooleanInDirection(d));
        survivor.useItem(board, survivor, d);
        Zone targetZone = zone.getZoneInDirection(d);
        targetZone.setBooleanInDirection(d.getOpposite(), true);
        assertTrue(zone.getBooleanInDirection(d));
    }
}
