package zombicide.zone;

import zombicide.util.Position;

/**
 * class for Street
 */
public abstract class Street extends Zone {
	/**
	 * Constructor for Street
	 * @param p Position of the Street in the board
	 */
	public Street(Position p) {
		super(p);
	}
}