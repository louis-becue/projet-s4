package zombicide;

import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.actor.survivor.action.ActionExecuteException;
import zombicide.actor.zombie.Walker;
import zombicide.board.Board;
import zombicide.board.type.RandomBoard;
import zombicide.util.Position;
import zombicide.zone.Street;
import zombicide.zone.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class SurvivorInteractiveMain {
    public static void main(String[] args) throws ActionExecuteException {

        if (args.length < 3){
            System.out.println("Usage : zombicide <width of board> <height of board> <number of survivors>");
        }

        else {
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            int nbPlayers = Integer.parseInt(args[2]);

            List<Survivor> survivors = new ArrayList<>();
            for (int i = 0; i <  nbPlayers;i++) {
                List<Role> survivorRoles = new ArrayList<>();
                survivorRoles.add(Role.BASE);
                Survivor survivor = new Survivor(("Survivor "+i), survivorRoles);
                survivors.add(survivor);
            }

            RandomBoard board = new RandomBoard(width, height);

            List<Street> streetList = new ArrayList<>();

            for(int i = 0; i < height;i++){
                for(int j = 0; j < width; j++){
                    Zone zone = board.getZone(new Position(j,i));
                    if (zone instanceof Street){
                        streetList.add((Street) zone);
                    }
                }
            }
            Random rd = new Random();
            for(int i = 0; i < width + height;i++){
                Street street = streetList.get(rd.nextInt(streetList.size()));
                street.spawnZombies(1);
            }


            Game game = new Game(board, survivors, false);
            board.display();
            game.play();
        }
    }
}
