package zombicide.zone.room;

import zombicide.util.Position;
import zombicide.zone.Room;

/**
 * Represents a continental room, extending the Room class.
 */
public class Continental extends Room{

    /**
     * Constructor for the Continental class.
     * @param p The position of the continental room.
     */
    public Continental(Position p){
        super(p);
        this.hostile = false;
    }

    /**
     * @return true if actors can shoot in a Continental zone
     */
    public boolean canAttack(){
        return this.hostile;
    }
}