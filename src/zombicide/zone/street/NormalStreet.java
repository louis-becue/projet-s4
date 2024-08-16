package zombicide.zone.street;

import zombicide.util.Position;
import zombicide.zone.Street;

/**
 * class for NormalStreet
 */
public class NormalStreet extends Street {

    /**
     * build a normal street
     * @param p the position of the normalStreet in the board
     */
    public NormalStreet(Position p) {
        super(p);
    }

    /**
     * @param o Object to check
     * @return boolean, true if o is equal to this, else: false
     */
    public boolean equals(Object o){
        if (o instanceof NormalStreet other){
            return this.getPosition() == other.getPosition();
        }
        return false;
    }
}