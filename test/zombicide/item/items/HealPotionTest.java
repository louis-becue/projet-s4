package zombicide.item.items;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.util.Direction;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealPotionTest {

    @Test
    void HealTest() throws ItemUsesException {
        // Creation of Survivor
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.BASE);
        Survivor survivor = new Survivor("Base",role);

        // Creation of Items
        HealPotion heal = new HealPotion(survivor);

        survivor.addItem(heal);
        survivor.setItemInHand(heal);

        // Creation of Board
        Board board = new TrainingBoard();

        // Creation of Direction
        Direction d = Direction.SOUTH;

        // Test
        survivor.isAttacked(2);
        survivor.useItem(board, survivor, d);
        assertEquals(survivor.getHealth(), 4);
    }
}
