package zombicide.item.items;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Room;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AidKitTest {
    @Test
    void AidHealTest() throws ItemUsesException {
        // Creation of Survivors
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.BASE);
        Survivor survivor1 = new Survivor("heal",role);
        Survivor survivor2 = new Survivor("healed", role);

        // Creation of Items
        AidKit aidHeal = new AidKit(survivor1);

        survivor1.addItem(aidHeal);
        survivor1.setItemInHand(aidHeal);

        // Creation of Board
        Board board = new TrainingBoard();

        // Creation of zones
        Room room = new Room(new Position(0,0));

        room.addActorInZone(survivor1);
        room.addActorInZone(survivor2);

        // Creation of Direction
        Direction d = Direction.SOUTH;

        // Test
        survivor2.isAttacked(2);
        survivor1.useItem(board, survivor2, d);
        assertEquals(survivor2.getHealth(), 4);
    }
}
