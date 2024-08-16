package zombicide.item.items;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.actor.zombie.Abomination;
import zombicide.actor.zombie.Zombie;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.item.Item;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapItemTest {

    @Test
    void MapNoiseAndDisplayTest() throws ItemUsesException {
        // Creation of Board
        Board board = new TrainingBoard();

        // Creation of Actors
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.BASE);
        Survivor survivor = new Survivor("Base", role);

        Zombie zombie = new Abomination();

        // Creation of zone
        Zone zone = board.getZone(new Position(0, 0));
        zone.addActorInZone(survivor);
        zone.addActorInZone(zombie);

        // Creation of Item
        MapItem map = new MapItem(survivor);
        survivor.addItem(map);

        survivor.setItemInHand(map);
        survivor.useItem(board, survivor, Direction.NORTH); // SHOULD DISPLAY THE BOARD

        assertEquals(zone.getNoiseLevel(), 2);
    }
}
