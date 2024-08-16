package zombicide.actor.survivor.action;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.RandomBoard;
import zombicide.board.type.TrainingBoard;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Room;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testDoAction() throws ActionExecuteException {
        ArrayList<Role> roles= new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Valentin",roles);

        Board board = new RandomBoard("1101101111110130111100000000001101101111110110111100000000001111011111111101121111110111111111011111010010");
        board.getZone(new Position(5,4)).addActorInZone(survivor);
        // We add the survivor at the position 5,4.
        // In this position, there are closed doors at left and right, and streets at bottom and top
        Move action = new Move();

        survivor.resetRemainingActionPoints();
        action.executeAction(survivor, Direction.NORTH);
        // In this case, survivor must be at position (4,4)
        assertEquals(survivor.getZone().getPosition(), new Position(5, 3));

        survivor.resetRemainingActionPoints();
        action.executeAction(survivor, Direction.SOUTH);
        // In this case, survivor must return to initial position, (5,4)
        assertEquals(survivor.getZone().getPosition(), new Position(5, 4));

        survivor.resetRemainingActionPoints();
        assertThrows(ActionExecuteException.class,()->{action.executeAction(survivor, Direction.EAST);});
        survivor.resetRemainingActionPoints();
        assertThrows(ActionExecuteException.class,()->{action.executeAction(survivor, Direction.WEST);});
        // In this case, survivor can't go EAST, neither WEST

    }

    @Test
    void testSpawnPotionWhenMoveInPharmacy() throws ActionExecuteException {
        // Creation of dependencies
        ArrayList<Role> roles= new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Valentin",roles);

        Board board = new TrainingBoard();
        board.getZone(new Position(3,4)).addActorInZone(survivor);
        Move action = new Move();

        // get the pharmacy in bottom-right corner of training board
        Room pharmacy = (Room) board.getZone(new Position(4,4));
        // Pharmacy shouldn't have item on ground
        assertEquals(pharmacy.getLootOnGround().size(), 0);

        // Open door between pharmacy and pharmacy's right zone
        pharmacy.setBooleanInDirection(Direction.WEST, true);

        // Make the survivor move in pharmacy
        action.executeAction(survivor, Direction.EAST);

        // Because the survivor moved in pharmacy, one potion is added on pharmacy's ground
        assertEquals(pharmacy.getLootOnGround().size(), 1);

        // Make the survivor go out and re-move into pharmacy
        action.executeAction(survivor, Direction.WEST);
        action.executeAction(survivor, Direction.EAST);

        // A new Potion should have been created and added in pharmacy's ground
        assertEquals(pharmacy.getLootOnGround().size(), 2);

    }
}