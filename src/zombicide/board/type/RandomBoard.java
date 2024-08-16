package zombicide.board.type;

import zombicide.board.Board;
import zombicide.board.NotEnoughRoomsException;
import zombicide.board.generator.*;
import zombicide.util.Position;
import zombicide.zone.Room;
import zombicide.zone.Zone;
import zombicide.zone.room.Continental;
import zombicide.zone.room.Pharmacy;
import zombicide.zone.street.Manhole;
import zombicide.zone.street.NormalStreet;


public class RandomBoard extends Board {
	private final String seed;

	private final String fullSeed;

	/**
	 * Constructor of the board. It initializes the board at the creation. We have to indicate the size by putting x and y.
	 * The constructor also needs a correct seed to work properly.
	 * @param x The x size of the board
	 * @param y	The y size of the board
	 * @param pharmacyAmount The number of pharmacies we want to put in the board
	 * @param continentalAmount The number of continental we want to put in the board
	 * @throws NotEnoughRoomsException If we ask for more building that there is rooms in the base seed.
	 */
	public RandomBoard(int x,int y,int pharmacyAmount,int continentalAmount) throws NotEnoughRoomsException{
		super(x, y);

		MapGenerator mapGen = new MapGenerator(x,y,pharmacyAmount,continentalAmount);
		this.fullSeed = mapGen.getSeed();
		this.seed = this.fullSeed.substring(0,this.fullSeed.length()-6);

		this.initializeBoard();
	}
	/**
	 * Constructor of the board. It initializes the board at the creation. We have to indicate the size by putting x and y.
	 * The constructor also needs a correct seed to work properly.
	 * Using this constructor, we have 1 pharmacy and 1 continental in the whole board.
	 * @param x The x size of the board
	 * @param y	The y size of the board
	 */
	public RandomBoard(int x,int y) {
		super(x, y);
		MapGenerator mapGen = new MapGenerator(x,y);
		this.fullSeed = mapGen.getSeed();
		this.seed = this.fullSeed.substring(0,this.fullSeed.length()-6);
		this.initializeBoard();
	}
	/**
	 * Constructor of the board. It initializes the board at the creation using an already known seed
	 * @param seed The seed of the board we want
	 */
	public RandomBoard(String seed) {
		super(Board.extractDimensionsFromSeed(seed)[0], Board.extractDimensionsFromSeed(seed)[1]);
		this.fullSeed = seed;
		this.seed = this.fullSeed.substring(0,this.fullSeed.length()-6);
		this.initializeBoard();
	}


	/**
	 * Method that initialise the board. It transforms the seed into a 2 dimensionnal tab.
	 * It also put the manholes where they have to be (= at the end of each street
	 */
	public void initializeBoard()  {
		this.seedToTab();
		this.putManholes();
		this.updateNeighbours();
	}
	/**
	 * Method that put the manholes in the board. The board needs to be initialized with the method "seedToTab" before using this.
	 * It works on the tab and not on the seed.
	 * It checks all the NormalStreets on the edges, and replace it with new objects Manholes.
	 */
	private void putManholes(){
		for (int i=0;i<this.dy;i++){
			for(int j=0;j<this.dx;j++){
				// Check on the top side
				if (board[0][j] instanceof NormalStreet){
					board[0][j] = new Manhole(new Position(j, 0));
				}
				//Check on the bottom side
				if (board[this.dy-1][j] instanceof NormalStreet){
					board[this.dy-1][j] = new Manhole( new Position(j, this.dy-1));
				}
				//Check on the left side
				if (board[i][0] instanceof NormalStreet){
					board[i][0] = new Manhole(new Position(0,i));
				}
				//Check on the right side
				if (board[i][this.dx-1] instanceof NormalStreet){
					board[i][this.dx-1] = new Manhole(new Position(this.dx-1, i));
				}
			}
		}
	}

	/**
	 * Method that creates the elements of the board. It adds the rooms, the streets, and the special rooms, depending on the seed.
	 * In the seed, '0' refers to a normal street, '1' refers a normal Room, '2' refers to a pharmacy, '3' refers to a continental.
	 * If we want to add more type of special rooms, this is where we have to do changes.
	 */
	private void seedToTab(){
		int i=0;
		int j=0;
		for (char bit: this.seed.toCharArray()){
			if (bit == '1'){
				board[i][j] = new Room(new Position(j, i));
			}
			if (bit == '0'){
				board[i][j] = new NormalStreet(new Position(j, i));
			}
			if (bit == '2'){
				board[i][j] = new Pharmacy(new Position(j, i));
			}
			if (bit == '3'){
				board[i][j] = new Continental(new Position(j, i));
			}
			j = j+1;
			if (j%this.dx==0){
				j = 0;
				i = i+1;
			}
		}
	}



	/**
	 * Rapid display to show the board in a clear way. It uses emojis so if you don't understand what correspond to what then you are kinda idiot :>
	 */
	public void fastDisplay(){
		System.out.println(this.dx);
		System.out.println(this.dy);
		System.out.println(this.fullSeed);
		System.out.println(this.seed);
		for(int i = 0; i < this.dy; i++){
			for(int j = 0; j < this.dx ; j++){
				System.out.print(areaToString(board[i][j]));
			}
			System.out.println();

		}
	}

	/**
	 * Method that can return a string depending on the zone we use in parameter. It checks the type of the Zone in parameter, and returns the corresponding emoji
	 * @param zone The zone we want to put in string
	 * @return The string corresponding to the zone entered in parameters.
	 */
	private String areaToString(Zone zone){
		String res = "";
		if (zone instanceof NormalStreet){
			res = "⬜\uFE0F";
		}
		else if (zone instanceof Manhole){
			res = "\uD83D\uDD73\uFE0F";
		}
		else if (zone instanceof Pharmacy){
			res = "\uD83C\uDFE5";
		}
		else if (zone instanceof Continental){
			res = "\uD83C\uDFE8";
		}
		else if (zone instanceof Room){
			res = "⬛\uFE0F";
		}
		return res;
	}
}
