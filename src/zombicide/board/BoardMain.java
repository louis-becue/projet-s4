package zombicide.board;

import zombicide.board.type.RandomBoard;
import zombicide.board.type.TrainingBoard;
import zombicide.util.Position;
import zombicide.zone.Zone;


public class BoardMain {
    public static void main(String[] args){
        Board map = new TrainingBoard();
        map.display(true);
        String seed = "1101101111110130111100000000001101101111110110111100000000001111011111111101121111110111111111011111";
        RandomBoard board = new RandomBoard(100,100);
        board.fastDisplay();
        board.display(true);
        Zone z1;
        z1 = board.getZone(new Position(0,0));
        Zone z2;
        z2 = board.getZone(new Position(1,1));
        board.displayNeighbours(z2, true);
        board.displayNeighbours(z1, true);
        try{
            Zone m = board.getMiddleStreet();
            System.out.println(m.getPosition());
            board.displayNeighbours(m, true);
        }
        catch (Exception e){
            System.out.println("error :(");
        }

    }
}
