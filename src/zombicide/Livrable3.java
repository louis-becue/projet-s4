package zombicide;

import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.actor.survivor.action.Action;
import zombicide.actor.zombie.Zombie;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.item.Item;
import zombicide.item.Weapon;
import zombicide.item.items.HealPotion;
import zombicide.item.weapons.WeaponType;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Livrable3 {

    private static Random random = new Random();
    public static void main(String[] args) {

        String[] noms = {"Valentin", "Hugo", "Paul", "Louis", "Bruno"};
        // training board creation
        Board board = new TrainingBoard();

        // players list initialization (one per role).
        int i = 0;
        List<Survivor> survivors = new ArrayList<>();
        for (Role role : Role.values()) {
            List<Role> survivorRoles = new ArrayList<>();
            survivorRoles.add(role);
            survivorRoles.add(Role.BASE);
            Survivor survivor = new Survivor(noms[(i++)%noms.length], survivorRoles);
            survivors.add(survivor);
        }

        // game creation.
        Game game = new Game(board, survivors, true);

        // creating a new zombie in each zone of the board.
        System.out.println("Livrable 3");
        for (int z = 0; z < board.getDy();z++){
            for (int y = 0; y < board.getDx();y++){
                Zone zone = board.getZone(new Position(z,y));
                zone.spawnZombies(1);
            }
        }

        // Move the survivors to the north of the manhole.
        for (Survivor survivor : game.getSurvivors()) {
            survivor.move(Direction.NORTH);
        }

        // display the city.
        game.getBoard().display();

        // adding an axe to the hand of the survivor2 and a Heal Potion to the hand of the survivor3.
        int c = 1;
        for (Survivor survivor : game.getSurvivors()) {
            if (c == 2) {
                Weapon axe = new Weapon(WeaponType.AXE, survivor);
                survivor.addItem(axe);
                survivor.setItemInHand(axe);
                System.out.println("Item dans la main de " + survivor.getName() + " : " + survivor.getItemInHand());
            }
            if (c == 3){
                Item healPotion = new HealPotion(survivor);
                survivor.addItem(healPotion);
                survivor.setItemInHand(healPotion);
                System.out.println("Item dans la main de " + survivor.getName() + " : " + survivor.getItemInHand());
            }
            c++;
        }


        // Make a one action for each survivor.
        for (Survivor survivor : game.getSurvivors()) {
            List<Action> actions = survivor.getPossibleActions();
            try {
                Action action = actions.get(random.nextInt(actions.size()));
                action.doAction(survivor);
                System.out.println(survivor.getName() + " make " + action);
            }
            catch (Exception e){
                System.out.println("No available action for " + survivor.getName() + actions);
            }
        }

        // we use the classic method to make zombies spawn in manholes (here, one more in the middle of the board)
        game.spawnZombies(1);
        game.getBoard().display();


        // Make the action to Attack and move for each Zombie.
        Zone zone = game.getBoard().getNoisiestZone();
        for (Zombie zombie: game.getZombies()) {
            zombie.playTurn(zone);
        }

        game.getBoard().display();

        // display all the survivor.
        for (Survivor survivor : game.getSurvivors()) {
            System.out.println(survivor.getInformations());
        }
    }
}
