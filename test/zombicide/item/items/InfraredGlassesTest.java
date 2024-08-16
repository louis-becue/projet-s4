package zombicide.item.items;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.util.Position;
import zombicide.zone.Zone;
import zombicide.item.Item;

import java.util.ArrayList;

public class InfraredGlassesTest{

    @Test
    void InfraredGlassesDisplayTest() throws ItemUsesException {
        // Creation of dependencies
        Board board = new TrainingBoard();
        Zone middleZone = board.getZone(new Position(2, 2));
        Zone UpLeftCorner = board.getZone(new Position(0, 0));
        Zone UpRightCorner = board.getZone(new Position(4, 0));
        Zone DownLeftCorner = board.getZone(new Position(0, 4));
        Zone DownRightCorner = board.getZone(new Position(4, 4));

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        Survivor survivor = new Survivor("Azerty", roles);
        middleZone.addActorInZone(survivor);

        Item i = new InfraredGlasses(survivor);
        survivor.addItem(i);
        survivor.setItemInHand(i);
        // should display the zone around the middle zone of the training board
        survivor.useItem(board, null, null);

        survivor.addItem(i);
        survivor.setItemInHand(i);
        UpLeftCorner.addActorInZone(survivor);

        // should display the neighbours of the up-left-corner zone of the training board
        survivor.useItem(board, null, null);

        survivor.addItem(i);
        survivor.setItemInHand(i);
        UpRightCorner.addActorInZone(survivor);

        // should display the neighbours of the up-right-corner zone of the training board
        survivor.useItem(board, null, null);

        survivor.addItem(i);
        survivor.setItemInHand(i);
        DownLeftCorner.addActorInZone(survivor);

        // should display the neighbours of the down-left-corner zone of the training board
        survivor.useItem(board, null, null);

        survivor.addItem(i);
        survivor.setItemInHand(i);

        DownRightCorner.addActorInZone(survivor);

        // should display the neighbours of the down-right-corner zone of the training board
        survivor.useItem(board, null, null);
    }
}