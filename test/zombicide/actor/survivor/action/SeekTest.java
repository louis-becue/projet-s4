package zombicide.actor.survivor.action;

import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.item.items.HealPotion;
import zombicide.util.Position;
import zombicide.zone.Zone;
import zombicide.Game;

import java.util.ArrayList;
import java.util.List;

public class SeekTest {
    @Test
    void testActionSeek() throws ActionExecuteException {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);

        Board board = new TrainingBoard();

        Survivor survivor = new Survivor("Valentin", roles);
        List<Survivor> survivors = new ArrayList<>();
        survivors.add(survivor);
        for (int i=0; i<5; i++) {
            survivor.addItem(new HealPotion(survivor));
        }

        Game game = new Game(board, survivors, true);

        Zone zone = board.getZone(new Position(4,3));
        zone.addActorInZone(survivor);
        System.out.println(survivor.getZone());

        Seek action = new Seek();

        action.doAction(survivor);
        System.out.println(survivor.getItems());
    }
}
