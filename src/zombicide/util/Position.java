	package zombicide.util;

	import java.util.ArrayList;
	import java.util.List;

	public class Position {
	   private int x;
	   private int y;

	   /**
		* creates a position corresponding to given coordinates
		*
		* @param x the x coordinate
		* @param y the y coordinate
		*/
	   public Position(int x, int y) {
		  this.x = x;
		  this.y = y;
	   }

	   /**
		* @return the x coordinate
		*/
	   public int getX() {
		  return this.x;
	   }


	   /**
		* @return the y coordinate
		*/
	   public int getY() {
		  return this.y;
	   }

	   /**
		* two positions are equals if they have same coordinates
		*/
	   public boolean equals(Object o) {
		  if (o instanceof Position other) {
			  return this.x == other.x && this.y == other.y;
		  } else {
			 return false;
		  }
	   }
	   /**
		* convert in String a position
		*/
	   public String toString() {
		  return "(" + this.x + ", " + this.y + ")";
	   }

	   /**
		* Returns a list of directions with the order of what are the best to reach the wanted position.
		* The direction at index 0 is the most favorable. Then the one at index 1 is slightly less good, etc...
		*
		* @param pos the pos we want to calculate the way to go to
		* @return List of directions. The first direction is the best one to go to reach the wanted position.
		* None if it is the same pos
		*/
	   public Direction calculateDirection(Position pos) {
		   if (this.equals(pos)) return null;
		   int horizontal = this.x-pos.x;
		   int vertical = this.y-pos.y;
		   Direction direction = Direction.NORTH;

		   if (Math.abs(horizontal) > Math.abs(vertical)){
			   if (horizontal < 0) {
				   direction = Direction.EAST;

			   }
			   else {
				   direction = Direction.WEST;
			   }

		   }
		   else {
			   if (vertical<0) {
				   direction = Direction.SOUTH;
			   }
		   }
		   return direction;
	   }

		/**
		 * Return neighbours index candidates for a given position
		 * @param x the x coordinate
		 * @param y the y coordinate
		 * @return candidate positions for neighbours of the given coordinates
		 */
	   public static List<Position> getNeighboursIndex(int x, int y) {
		   List<Position> candidates = new ArrayList<>();
		   candidates.add(new Position(x + 1, y));
		   candidates.add(new Position(x, y + 1));
		   candidates.add(new Position(x - 1, y));
		   candidates.add(new Position(x, y - 1));
		   return candidates;
	   }

		/**
		 * Return the direction to take, to go from current position to a given target but doesn't count diagonals
		 * @param other the target
		 * @return a direction to reach the target
		 */
	   public Direction getDirectionToTarget(Position other) {
		   if (this.y < other.getY()){
			   return Direction.SOUTH;
		   }

		   else if (this.y > other.getY()){
			   return Direction.NORTH;
		   }

		   else if(this.x < other.getX()){
			   return Direction.EAST;
		   }

		   else if (this.x > other.getX()) {
			   return Direction.WEST;
		   }
		   return null;
	   }
	}
