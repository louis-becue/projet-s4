package zombicide.zone.street;

import zombicide.util.Position;
import zombicide.zone.Street;

/**
 * Represents a manhole extending the Street class, capable of spawning zombies.
 */
public class Manhole extends Street{

    /**
     * build a manhole
     *
     * @param p the position of the manhole
     */
    public Manhole(Position p){
        super(p);
    }


}
