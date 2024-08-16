package zombicide.util;

public enum Direction {
	NORTH,
	SOUTH, 
	EAST, 
	WEST;

	/**
	 * Return the opposite of the direction (i.e. SOUTH if direction is NORTH, EAST if direction is WEST, ...)
	 * @return the opposite of the direction
	 */
	public Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case WEST -> EAST;
            default -> NORTH;
        };
	}
}
