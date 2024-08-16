package zombicide;
import zombicide.actor.survivor.*;
import zombicide.actor.survivor.action.*;
import zombicide.actor.zombie.Zombie;
import zombicide.board.Board;
import zombicide.item.Weapon;
import zombicide.item.weapons.WeaponType;
import zombicide.util.*;
import zombicide.util.listchooser.*;
import zombicide.zone.Room;
import zombicide.zone.Zone;
import zombicide.zone.street.Manhole;

import java.util.*;


/**
 * Represents a game instance with a board, players, and actions.
 */
public class Game {
    private final Board board;
    private List<Survivor> survivors;

    private Map<String, Action> allActions;

    private final boolean ia;

    public static ListChooser<Object> lc;
    public static Dice dice;

    /**
     * Create a game
     * @param board the board
     * @param players the players
     */
    public Game(Board board, List<Survivor> players, boolean ia){
        this.board = board;
        this.survivors = players;
        this.ia = ia;
        if (ia) {
            Game.lc = new RandomListChooser<>();
        } else {
            Game.lc = new InteractiveListChooser<>();
        }
        Game.dice = new Dice(6);
        this.initializeGame();

    }

    /**
     * Simulate a turn in game
     * @throws ActionExecuteException Exception for a wrong execution of an action
     */
    public void play() throws ActionExecuteException {
        int nbTurn = 0;

        while (!isEnded() || nbTurn<1){
            // All the survivors play
            this.makeSurvivorsPlay();

            // All the zombies play
            this.makeZombiesPlay();

            // Clean the board, and reset the noise level of each zone
            this.board.cleanBoard();

            // We spawn zombies
            this.spawnZombies(4);
            this.board.display();


            nbTurn++;
        }
        if (this.isWon()){
            System.out.println("The game is won by survivors !");
        }
        else{
            System.out.println("The game is lost...");
        }
    }

    private void makeSurvivorsPlay() throws ActionExecuteException {
        for(Survivor survivor : this.survivors){
            if (survivor.canPlay()){
                survivor.playTurn();
            }
        }
    }
    private void makeZombiesPlay() {
        Zone noisiestZone = this.board.getNoisiestZone();
        for(Zombie zombie : this.board.getAllZombies()){
            if (zombie.canPlay()){
                zombie.playTurn(noisiestZone);
                this.actualizeSurvivorsArrays(); // For each zombie, we actualise survivors array in case one of them die (to avoid zombie attacking an already dead survivor)
            }
        }
    }



    /**
     * Set up the items on the board, using their associated loot tables
     */
    public void generateItemsOnBoard() {
        for (int i = 0; i < this.board.getDy(); i++) {
            for (int j = 0; j < this.board.getDx(); j++) {
                Zone zone = this.board.getZone(new Position(j, i));
                if (zone instanceof Room r) {
                    r.spawnItemsOnGround();
                }
            }
        }
    }

    /**
     * Initialize all survivors in the game
     * Place survivors, give items and actions
     */
    public void initializeGame(){
        this.generateItemsOnBoard();
        this.initializeAllActions();
        this.setActionsInRoles();
        this.putSurvivorsInMiddle(this.survivors);
        for (Survivor survivor : this.survivors){
            Weapon gun = new Weapon(WeaponType.GUN);
            survivor.addItem(gun);
            survivor.setItemInHand(gun);
            survivor.setActions();
            survivor.resetRemainingActionPoints();

        }
    }


    /**
     * @return true if the game is won. Else, false
     */
    public boolean isWon(){
        return ((this.board.getAllZombies().isEmpty()) || (this.getSumLevelOfSurvivors() >= 30));
    }

    /**
     * @return true if the game is lost (if there is no survivor alive). Else, false
     */
    public boolean isLost(){
        return this.survivors.isEmpty();
    }

    /**
     * @return true if the game is finished. Else, false
     */
    public boolean isEnded(){
        return this.isWon() || this.isLost();
    }

    /**
     * Remove all dead survivors from the game
     */
    private void actualizeSurvivorsArrays(){
        this.survivors.removeIf(survivor -> survivor.getHealth() <= 0);
    }
    /** Method that initialise the HashMap containing all the possible actions in the game.
     * It creates one object per different action
     */
    private void initializeAllActions (){
        this.allActions = new HashMap<>();

        this.allActions.put("NosySeek", new NosySeek());
        this.allActions.put("Heal", new Heal());
        this.allActions.put("Attack", new Attack());
        this.allActions.put("Move", new Move());
        this.allActions.put("MakeNoise", new MakeNoise());
        this.allActions.put("OpenDoor", new OpenDoor());
        this.allActions.put("LookAround", new LookAround());
        this.allActions.put("Seek", new Seek());
        this.allActions.put("TakeInHand", new TakeInHand());
        this.allActions.put("UseItem", new UseItem(board)); // we have to pass board to UseItem class for
                                                            // map item and infrared glasses
    }

    /** Put the Action objects (already initialized before with initializeAlActions()) in the roles possible actions.
     * BASE Role correspond to the action every survivor can do.
     *
     */
    public void setActionsInRoles(){
        //BASE Role
        Role.BASE.addAction(this.allActions.get("Attack"));
        Role.BASE.addAction(this.allActions.get("Move"));
        Role.BASE.addAction(this.allActions.get("MakeNoise"));
        Role.BASE.addAction(this.allActions.get("OpenDoor"));
        Role.BASE.addAction(this.allActions.get("LookAround"));
        Role.BASE.addAction(this.allActions.get("Seek"));
        Role.BASE.addAction(this.allActions.get("TakeInHand"));
        Role.BASE.addAction(this.allActions.get("UseItem"));

        //HEALER Role
        Role.HEALER.addAction((this.allActions.get("Heal")));

        //NOSY Role
        Role.NOSY.addAction(this.allActions.get("NosySeek"));
    }
    //Getters

    /**
     * Return the game board.
     * @return The game board.
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * @return true if the game is played by the computer. Else, false
     */
    public boolean isIA() {return this.ia;}

    /**
     * Initialize all survivors in the middle street of the board
     * @param survivors the list of survivors in the game
     */
    public void putSurvivorsInMiddle(List<Survivor> survivors){
        Zone middleZone = this.board.getMiddleStreet();
        for (Survivor survivor : survivors){
            survivor.move(middleZone);
        }
    }

    /**
     * Return the list of players.
     * @return The list of players.
     */
    public List<Survivor> getSurvivors(){
        return this.survivors;
    }


    /**
     * Return the list of Zombies on the board.
     *
     * @return The list of Zombies.
     */
    public List<Zombie> getZombies(){
        ArrayList<Zombie> zombies = new ArrayList<>();
        for (int z = 0; z < this.board.getDy();z++) {
            for (int y = 0; y < this.board.getDx(); y++) {
                Zone zone = board.getZone(new Position(z,y));
                zombies.addAll(zone.getZombies());
            }
        }
        return zombies;
    }


    /**
     * Return the list of all actions available in the game.
     * @return The list of all actions available in the game.
     */
    public Map<String,Action> getAllActions(){
        return this.allActions;
    }

    /**
     * Checks if there are survivors still alive on the board.
     * @return True if at least one survivor is still alive, False otherwise.
     */
    public Boolean survivorStillAlive(){
        for (int i = 0; i < this.board.getDx(); i++){
            for (int j = 0; j < this.board.getDy(); j++) {
                if (this.board.getZone(new Position(i, j)).nbPlayerInZone() < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are zombies still alive on the board.
     * @return True if at least one zombie is still alive, False otherwise.
     */
    public Boolean zombieStillAlive(){
        for (int i = 0; i < this.board.getDx(); i++){
            for (int j = 0; j < this.board.getDy(); j++) {
                if (this.board.getZone(new Position(i, j)).nbZombieInZone() < 0) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Checks if a player can move in a specified direction from a given zone.
     * @param zone The zone from which the player intends to move.
     * @param direction The direction in which the player wants to move.
     * @return True if the move is possible, False otherwise.
     */
    public Boolean canMove(Zone zone, Direction direction){
        return null;
    }

    /**
     * @return the sum of level of all survivors in the game
     */
    private int getSumLevelOfSurvivors(){
        int sum = 0;
        for(Survivor survivor : this.survivors){
            sum += survivor.getLevel();
        }
        return sum;
    }

    /**
     * @return the average survivor's level in the game
     */
    private int getAverageLevelOfSurvivors(){
        int sum = 0;
        for(Survivor survivor : this.survivors){
            sum += survivor.getLevel();
        }
        if (this.survivors.isEmpty()){
            return 0;
        }
        else {
            return sum / this.survivors.size();
        }
    }


    /**
     * Method to make zombies spawn, depending on the average level of survivors. It spawns the average level of survivor, divided by 3 numbers of zombies.
     * It spawns this number of zombies at a chose number of manholes
     * @param nbSpawningManholes Number of manholes we want zombies to spawn
     */
    public void spawnZombies(int nbSpawningManholes) {
        double amount = Math.ceil((double) this.getAverageLevelOfSurvivors() / 3);
        int nbZombies = (int) amount;
        List<Manhole> manholes = pickNRandom(this.board.getManholes(), nbSpawningManholes);
        for (Manhole manhole : manholes) {
            manhole.spawnZombies(nbZombies);
        }
    }

    /**
     * Method to pick a random number of elements in an array of Manhole items.
     * @param lst The complete list of manholes of the board
     * @param n The number of manholes we want to pick randomly in the initial list
     * @return A list containing the desired number of random manholes of the board
     */
    public static List<Manhole> pickNRandom(List<Manhole> lst, int n) {
        List<Manhole> copy = new ArrayList<>(lst);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

}


