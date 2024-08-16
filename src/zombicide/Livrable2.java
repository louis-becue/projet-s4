package zombicide;

import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.item.Item;
import zombicide.item.items.HealPotion;
import zombicide.item.items.MapItem;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Zone;

import java.util.ArrayList;
import java.util.List;

public class Livrable2 {
    public static void main(String[] args){
        String[] noms = {"Valentin", "Hugo", "Paul", "Louis", "Bruno"};
        // training board creation
        Board board = new TrainingBoard();

        // players list initialization (one per role)
        int i = 0;
        List<Survivor> survivors = new ArrayList<>();
        for (Role role : Role.values()) {
            List<Role> survivorRoles = new ArrayList<>();
            survivorRoles.add(role);
            Survivor survivor = new Survivor(noms[(i++)%noms.length], survivorRoles);
            survivors.add(survivor);
        }

        // game creation
        Game game = new Game(board, survivors, true);

        // adding map to survivors backpack
        for (Survivor survivor : game.getSurvivors()) {
            Item mapItem = new MapItem(survivor);
            survivor.addItem(mapItem);
            Item healPotion = new HealPotion(survivor);
            survivor.addItem(healPotion);
            survivor.setItemInHand(healPotion);

            /* IF YOU WANT TO TRY MAP ITEM
            try {
                survivor.useItem(board, null);
            } catch (ItemUsesException e) {
                System.out.println("Cannot use item : it is not in survivors hand :(");
            } */

            System.out.println("Items dans le sac de " + survivor.getName() + " : " + survivor.getItems());
            System.out.println("Item dans la main de " + survivor.getName() + " : " + survivor.getItemInHand());
        }
        game.getBoard().display();
        for (Survivor survivor : game.getSurvivors()) {
            System.out.println("Moving " + survivor.getName());
            survivor.move(Direction.NORTH);
        }
        game.getBoard().display();

        // creating a new zombie in each zone of the board
        System.out.println("New zombies are coming !");
        for (int z = 0; z < board.getDy();z++){
            for (int y = 0; y < board.getDx();y++){
                Zone zone = board.getZone(new Position(z,y));
                zone.spawnZombies(1);
            }
        }

        // we use the classic method to make zombies spawn in manholes (here, one more in the middle of the board)
        game.spawnZombies(1);
        game.getBoard().display();
    }
}
