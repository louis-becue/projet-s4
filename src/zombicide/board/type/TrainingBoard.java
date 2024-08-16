package zombicide.board.type;

import zombicide.util.Position;
import zombicide.zone.*;
import zombicide.board.*;
import zombicide.zone.room.Pharmacy;
import zombicide.zone.street.Manhole;
import zombicide.zone.street.NormalStreet;

public class TrainingBoard extends Board{

    /**
     * Build a training board.
     */
	public TrainingBoard(){
        super(5, 5);
        for (int i = 0; i < this.getDy(); i++){
            for (int j = 0; j < this.getDx(); j++){
                if (i == 2 && j == 2){
                    this.board[i][j] = new Manhole( new Position(j, i));
                }
                else if (i == 2 || j == 2){
                    this.board[i][j] = new NormalStreet(new Position(j, i));
                }
                else if (i == 4 && j == 4){
                    this.board[i][j] = new Pharmacy(new Position(j, i));
                }
                else {
                    this.board[i][j] = new Room(new Position(j, i));
                }
            }
        }

        this.updateNeighbours();
	}

  
}
