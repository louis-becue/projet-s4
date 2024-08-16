package zombicide.board;

import zombicide.actor.zombie.Zombie;
import zombicide.util.*;
import zombicide.zone.*;
import zombicide.zone.room.Continental;
import zombicide.zone.room.Pharmacy;
import zombicide.zone.street.Manhole;
import zombicide.zone.street.NormalStreet;

import java.util.*;


public abstract class Board{
	protected final int dx;
    protected final int dy;
	protected Zone[][] board;

	/**
	 * Create a board.
	 * 
	 * @param x number of row
	 * @param y number of cologne
	*/
	public Board (int x, int y){
        this.dx = x;
        this.dy = y;
        this.board = new Zone[y][x];
	}

	/**
	 * Get the Board on a list struct.
	 *
	 * @return the board
	 */
	public Zone[][] getBoardList() {
		return this.board;
	}

	/**
	 * Extract the dimensions from the seed. In the seed, the 6 last characters correspond to the dimensions. 3 for the width, 3 for the height
	 *
	 * @param seed The seed of the board
	 *
	 * @return Two integers that corresponds to the width and the height
	 */
	public static int[] extractDimensionsFromSeed(String seed) {
		int x = Integer.parseInt(seed.substring(seed.length() - 6, seed.length() - 3));
		int y = Integer.parseInt(seed.substring(seed.length() - 3));
		return new int[]{x, y};
	}
	/**
	 * Get the zone a the position p.
	 *
	 * @param p the position of the cell.
	 *
	 * @return the zone at the position.
	 */
	public Zone getZone(Position p) {
		return this.board[p.getY()][p.getX()];
	}

	public List<Zombie> getAllZombies(){
		ArrayList<Zombie> zombies = new ArrayList<>();
		for (int i = 0; i < this.dy;i++){
			for (int j = 0; j < this.dx;j++){
				zombies.addAll(this.board[i][j].getZombies());
			}
		}
		return zombies;
	}

	/**
	 * Clean the board by removing all the actor at 0 or less HP, and reset the noise level to 0
	 */
	public void cleanBoard(){
		for (int i = 0; i < this.dy;i++){
			for (int j = 0; j < this.dx;j++){
				Zone zone = this.getZone(new Position(j,i));
				zone.resetNoiseLevel();
			}
		}
	}


	/**
	 * Get the row's number of the board.
	 *
	 * @return the number of row.
	 */
	public int getDx() {
		return this.dx;
	}

	/**
	 * Get the cologne's number of the board.
	 *
	 * @return the number of cologne.
	 */
	public int getDy() {
		return this.dy;
	}

	/**
	 * Method used to return every manhole that is in the board. This method is usefully when we want to make zombies spawn (in Game class)
	 *
	 * @return ArrayList that contains every manhole in the board
	 */
	public ArrayList<Manhole> getManholes(){
		ArrayList<Manhole> manholes = new ArrayList<>();
		for (int i = 0; i < this.dy;i++){
			for (int j = 0; j < this.dx;j++){
				if (this.board[i][j] instanceof Manhole){
					manholes.add((Manhole) this.board[i][j]);
				}
			}
		}
		return manholes;
	}

	/**
	 * Method to find the zone with the highest noise level. If there is no noise in the board, it returns the middle street
	 * If there are many zones that has the same noise level, it returns one randomly among the zones that has the highest noise level
	 *
	 * @return The zone with the highest noise level
	 */
	public Zone getNoisiestZone(){
		int highestNoise = 0;
		ArrayList<Zone> zones = new ArrayList<>();
		zones.add(this.getMiddleStreet());


		for (int i = 0; i < this.dy;i++){
			for (int j = 0; j < this.dx;j++){
				Zone tempZone = this.getZone(new Position(j ,i));
				if (tempZone.getNoiseLevel() == highestNoise){
					zones.add(tempZone);
				}
				if ( tempZone.getNoiseLevel() > highestNoise){
					zones = new ArrayList<>();
					zones.add(tempZone);
					highestNoise = tempZone.getNoiseLevel();
				}

			}
		}
		Random rd = new Random();
		int randomNumber = rd.nextInt(zones.size());
        return zones.get(randomNumber);
	}

	/**
	 * Make a representation of a room in 2d.
	 *
	 * @param zone the zone.
	 * @param displayZombie - true if the zombie will be display.
	 *                      - false otherwise.
	 *
	 * @return a list of string.
	 */
	public ArrayList<String> roomImage(Zone zone, boolean displayZombie){
		ArrayList<String> out = new ArrayList<>();
		if (zone.nbZombieInZone() >=1 && displayZombie)
			if(zone instanceof Pharmacy)
				out.add("| ⚕  "+ zone.nbZombieInZone() +"Z    ");
			else if(zone instanceof Continental)
				out.add("| \uD83C\uDFE8  "+ zone.nbZombieInZone() +"Z    ");
			else
				out.add("|    "+ zone.nbZombieInZone() +"Z    ");
		else
			if(zone instanceof Pharmacy)
				out.add("| ⚕        ");
			else if(zone instanceof Continental)
				out.add("|  \uD83C\uDFE8      ");
			else
				out.add("|          ");
		if (zone.nbPlayerInZone() >=1)
			out.add("|     " + zone.nbPlayerInZone() +"S   ");
		else
			out.add("|          ");
		out.add("| __ __ __ ");
		return out;
	}

	/**
	 * Return a representation of normal street in 2d.
	 *
	 * @param zone the zone
	 * @param streetOnBottom - true if there is a street on the bottom of the zone.
	 *                       - false otherwise.
	 * @param streetOnLeft - true if there is a street on the left of the zone.
	 *                      - false otherwise.
	 * @param displayZombie - true if the zombie will be display.
	 *                      - false otherwise.
	 *
	 * @return a list of string.
	 */
	public ArrayList<String> normalStreetImage(Zone zone, boolean streetOnBottom, boolean streetOnLeft, boolean displayZombie){
		ArrayList<String> out = new ArrayList<>();
		if (streetOnLeft){
			if (zone.nbZombieInZone() >=1 && displayZombie)
				out.add("     "+ zone.nbZombieInZone() +"Z    ");
			else
				out.add("           ");
			if (zone.nbPlayerInZone() >=1)
				out.add("      " + zone.nbPlayerInZone() +"S   ");
			else
				out.add("           ");
			if (streetOnBottom)
				out.add("           ");
			else
				out.add("  __ __ __ ");
		}
		else {
			if (zone.nbZombieInZone() >=1 && displayZombie)
				out.add("|    "+ zone.nbZombieInZone() +"Z    ");
			else
				out.add("|          ");
			if (zone.nbPlayerInZone() >=1)
				out.add("|     " + zone.nbPlayerInZone() +"S   ");
			else
				out.add("|          ");
			if (streetOnBottom)
				out.add("|          ");
			else
				out.add("| __ __ __ ");
		}
		return out;
	}

	/**
	 * Return a representation of the manhole in 2d.
	 *
	 * @param zone the zone
	 * @param RoomOnBottom - true if there is a room on the bottom of the zone.
	 *                     - false otherwise.
	 * @param RoomOnLeft - true if there is a room on the left of the zone.
	 *                   - false otherwise.
	 * @param displayZombie - true if the zombie will be display.
	 *                      - false otherwise.
	 *
	 * @return a list of string.
	 */
	public ArrayList<String> manholeImage(Zone zone, boolean RoomOnBottom, boolean RoomOnLeft, boolean displayZombie){
		ArrayList<String> out = new ArrayList<>();
		if (RoomOnLeft){
			if (zone.nbZombieInZone() >=1 && displayZombie)
				out.add("|    "+ zone.nbZombieInZone() +"Z    ");
			else
				out.add("|          ");
			if (zone.nbPlayerInZone() >=1)
				out.add("|    X" + zone.nbPlayerInZone() +"S   ");
			else
				out.add("|    X     ");
			if (RoomOnBottom)
				out.add("| __ __ __ ");
			else
				out.add("|          ");
		}
		else {
			if (zone.nbZombieInZone() >=1 && displayZombie)
				out.add("     "+ zone.nbZombieInZone() +"Z    ");
			else
				out.add("           ");
			if (zone.nbPlayerInZone() >=1)
				out.add("     X" + zone.nbPlayerInZone() +"S   ");
			else
				out.add("     X     ");
			if (RoomOnBottom)
				out.add("  __ __ __ ");
			else
				out.add("           ");
		}
		return out;
	}

	/**
	 * Build a list of the representation of the zone with these neighbours by line.
	 *
     * @param zone the zone who will be display with these neighbours.
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
	 *
	 * @return a tab of line of the zone and these neighbours.
	 */
	public String[] buildDisplayTabNeighbours(Zone zone, boolean displayZombie) {
        int positionY = zone.getPosition().getY();
        int positionX = zone.getPosition().getX();
        int ny, nx, startX, startY;
        if (positionY == 0 || positionY == this.dy-1)
            ny = 2;
        else
            ny = 3;
        if (positionX == 0 || positionX == this.dx-1)
            nx = 2;
        else
            nx = 3;
        if (positionX == 0)
    		startX = 0;
    	else
            startX = positionX-1;
        if (positionY == 0)
            startY = 0;
        else
            startY = positionY-1;
        String[] tab = new String[ny * 3];
        for (int y = startY; y < (startY+ny); y++) {
            for (int x = startX; x < (startX+nx); x++) {
                List<String> zoneImage = buildZone(x, y, startX+nx, startY+ny, displayZombie);
                for (int z = 0; z < 3; z++) {
                    tab[(y-startY)*3+z] += zoneImage.get(z);
                }
            }
        }
        return tab;
    }

    /**
     * Build a representation of the zone.
     *
     * @param x the position x of the zone.
     * @param y the position y of the zone.
     * @param maxX the size max of the position X .
     * @param maxY the size max of the position X .
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
	 *
     * @return a List of string who represent the zone.
     */
    List<String> buildZone(int x, int y, int maxX, int maxY, boolean displayZombie){
        Zone Zone = this.board[y][x];
        ArrayList<String> zoneImage;
        if (Zone instanceof Room) {
            zoneImage = roomImage(Zone, displayZombie);
        } else if (Zone instanceof NormalStreet) {
            boolean StreetOnBottom = ((y != maxY - 1) && ((this.board[y + 1][x] instanceof NormalStreet) || (this.board[y + 1][x] instanceof Manhole)));
            boolean StreetOnLeft = ((x != 0)  && ((this.board[y][x - 1] instanceof NormalStreet) || (this.board[y][x - 1] instanceof Manhole)));
            zoneImage = normalStreetImage(Zone, StreetOnBottom, StreetOnLeft, displayZombie);
        } else {
            boolean RoomOnBottom = (y == maxY - 1 || (this.board[y + 1][x] instanceof Room));
            boolean RoomOnLeft = (x == 0 || (this.board[y][x - 1] instanceof Room));
            zoneImage = manholeImage(Zone, RoomOnBottom, RoomOnLeft, displayZombie);
        }
        return zoneImage;
    }

    /**
     * Build a list of the representation of the map by line.
	 *
	 * @param displayZombie - true if the zombie will be display.
	 *                      - false otherwise.
     *
     * @return an array of line of the map
     */
    public String[] buildDisplayTab(boolean displayZombie){
        String[] tab = new String[this.dy*3];
        for (int y=0; y < this.dy; y++){
            for (int x=0; x < this.dx; x++){
				List<String> zoneImage = buildZone(x,y,this.dx, this.dy, displayZombie);
                for(int z=0; z < 3; z++){
                    tab[y*3+z] += zoneImage.get(z);
                }
            }
        }
        return tab;
    }

	/**
	 * Update the neighbours of each zone in the board
	 */
	public void updateNeighbours(){
		for (int i = 0; i < this.dy; i++){
			for (int j = 0; j < this.dx; j++){
				setAllBooleanNeighbours(i, j);
			}
		}
	}

	/**
	 * Set the boolean for all neighbours of the gi-ven Zone to False. True if the zone and the neighbour are Street
	 *
	 * @param p position of zone to update boolean
	 * @param neighbours list of neighbours to a given zone
	 * @param i cord x of the checked zone
	 * @param j cord y of the checked zone
	 */
	public void setBooleanNeighbours(Map<Direction, Map<Zone, Boolean>> neighboursWithDirection, Position zonePosition, Position p, Map<Zone, Boolean> neighbours, int i, int j){
		if (p.getY() >= 0 && p.getX() >= 0) {
			if ((this.board[p.getY()][p.getX()] instanceof Street) && (this.board[i][j] instanceof Street)) {
				neighbours.put(this.board[p.getY()][p.getX()], true);
			} else {
				neighbours.put(this.board[p.getY()][p.getX()], false);
			}
			Direction directionToTarget = zonePosition.getDirectionToTarget(p);
			neighboursWithDirection.put(directionToTarget, neighbours);
		}
	}

	/**
	 * @param i y coordinate of the current zone
	 * @param j x coordinate of the current zone
	 */
	public void setAllBooleanNeighbours(int i, int j){
		Zone zone = this.board[i][j];
		Map<Direction, Map<Zone, Boolean>> neighboursWithDirection = new HashMap<>();
		Position zonePosition = zone.getPosition();
		for (Position p : Position.getNeighboursIndex(j, i)){
			Map<Zone, Boolean> neighbours = new HashMap<>();

			try{
				setBooleanNeighbours(neighboursWithDirection, zonePosition, p, neighbours, i, j);
			}
			catch (Exception e){
			}
		}
		setInvalidZoneNeighbours(zone, neighboursWithDirection);
		this.board[i][j].setNeighbours(neighboursWithDirection);
	}

	/**
	 * @param zone given zone to put null on its neighbours if neighbour's index is invalid
	 * @param neighboursWithDirection map of direction and list of neighbours with boolean to a given zone
	 *
	 * Set at null all the neighbours of the given zone if index of neighbours are invalid
	 */
	public void setInvalidZoneNeighbours(Zone zone, Map<Direction, Map<Zone, Boolean>> neighboursWithDirection) {
		for (Direction d : Direction.values()) {
			if (!(zone.getNeighbours().containsKey(d))) {
				Map<Zone, Boolean> neighboursNull = new HashMap<>();
				neighboursNull.put(null, null);
				neighboursWithDirection.put(d, neighboursNull);
			}
		}
	}

	/**
	 * Return the most central street on the board
	 *
	 * @return the most central street
	 */
	public Zone getMiddleStreet(){
		Zone res;
		Queue<Zone> queue = new LinkedList<>();
		queue.offer(this.board[dy/2][dx/2]);
		while (!(queue.isEmpty())) {
			res = queue.poll();
			if (res instanceof Street)
				return res;
			Position p = res.getPosition();
			for (int x = -1; x<2; x++){
				for (int y = -1; y<2; y++){
					Zone t = this.board[p.getY()+y][p.getX()+x];
					if (!queue.contains(t))
						queue.offer(t);
				}
			}
		}
		return this.board[dy/2][dx/2];
    }

	/**
	 * Display the Board.
	 */
	public void display(){
		Display displayMap = new Display(this);
		displayMap.display(true);
	}

	/**
	 * Display the Board.
	 *
	 * @param displayZombie - true if the zombie will be display.
	 *                      - false otherwise.
	 */
	public void display(boolean displayZombie){
		Display displayMap = new Display(this);
		displayMap.display(displayZombie);
	}

	/**
	 * Display a zone with these neighbours.
	 *
	 * @param zone the zone who will be display.
	 * @param displayZombie - true if the zombie will be display.
	 *                      - false otherwise.
	 */
	public void displayNeighbours(Zone zone, boolean displayZombie){
		Display displayMap = new Display(this);
		displayMap.displayNeighbours(zone, displayZombie);
	}


}
