package zombicide.zone;

import java.util.*;

import zombicide.actor.Actor;
import zombicide.actor.zombie.*;
import zombicide.util.*;
import zombicide.actor.survivor.Survivor;
import zombicide.zone.room.Continental;

/**
 * Abstract class representing a zone in a game, with players, zombies, and neighbours.
 */
public abstract class Zone{
	protected Map<Direction, Map<Zone, Boolean>> neighbours;
	protected ArrayList<Survivor> playerInZone;
	protected ArrayList<Zombie> zombieInZone;
	protected int noiseLevel;
	protected boolean hostile;
	public Position position;
	private static final int NBTYPEZOMBIE = 4;

	/**
	 * Create a Zone
	 * @param p the position of the zone
	 */
	public Zone(Position p) {
		this.noiseLevel = 0;
		this.hostile = true;
		this.position = p;
		this.neighbours = new HashMap<>();
		// Instantiate neighbours keys with all directions
		for (Direction direction : Direction.values()) {
			this.neighbours.put(direction, new HashMap<>());
		}

		this.playerInZone = new ArrayList<>();
		this.zombieInZone = new ArrayList<>();
	}

	/**
	 * @return the zombie in the zone
	 */
	public ArrayList<Zombie> getZombies(){
		return this.zombieInZone;
	}

	/**
	 * @return the survivors in the zone
	 */
	public ArrayList<Survivor> getSurvivors() {return this.playerInZone; }

	/**
	 * @return the other zone next this zone
	 */
	public Map<Direction, Map<Zone, Boolean>> getNeighbours(){
		return this.neighbours;
	}


	public void setNeighbours(Map<Direction, Map<Zone, Boolean>> theNeighbours){
		this.neighbours = theNeighbours;
	}

	/**
	 * Remove the specified actor from the zone
	 * @param actor the actor to remove
	 */
	public void removeActorFromZone(Actor actor) {
		if (actor instanceof Survivor && this.playerInZone.contains(actor)) {
			this.playerInZone.remove(actor);
		} else if (actor instanceof Zombie && this.zombieInZone.contains(actor)) {
			this.zombieInZone.remove(actor);
		} else {
			throw new IllegalArgumentException("Couldn't remove actor : given actor is not handled by Zone class");
		}
	}

	/**
	 * Add the specified actor to the zone
	 * @param actor the actor to add
	 */
	public void addActorInZone(Actor actor) {
		if (actor instanceof Survivor) {
			this.playerInZone.add((Survivor) actor);
			actor.setZone(this);
		} else if (actor instanceof Zombie) {
			this.zombieInZone.add((Zombie) actor);
			actor.setZone(this);
		} else {
			throw new IllegalArgumentException("Couldn't add actor : given actor is not handled by Zone class");
		}
	}

	/**
	 * @return the number of player in the zone
	 */
	public int nbPlayerInZone(){
		return this.playerInZone.size();
	}

	public Survivor getRandomSurvivor() throws NotEnoughSurvivorException{

		int nb = this.nbPlayerInZone();
		if ( nb > 0 ){
			Random rd = new Random();
            return this.getSurvivors().get(rd.nextInt(nb));
		}
		throw new NotEnoughSurvivorException("There is no survivor in the zone");
	}
	/**
	 * @return the numbers of zombie in the zone
	 */
	public int nbZombieInZone(){
		return this.zombieInZone.size();
	}

	/**
	 * @return the noise level
	 */
	public int getNoiseLevel() {
		return this.noiseLevel;
	}

	/**
	 * Increase the noise level of this zone
	 * @param amount the amount we want to increase the noise level
	 */
	public void increaseNoiseLevel(int amount){
		this.noiseLevel = this.noiseLevel + amount;
	}

	/**
	 * Method to reset the noise level (put it back to 0)
	 */
	public void resetNoiseLevel() {this.noiseLevel = 0;}
	/**
	 * @return true if the zone is hostile
	 */
	public boolean getHostile() {
		return this.hostile;
	}

	/**
	 * @return the position on the board
	 */
	public Position getPosition(){
		return this.position;
	}

	/**
	 * @param direction the direction we want to check the neighbour
	 * @return the neighbour in the given direction
	 */
	public Zone getZoneInDirection(Direction direction) {
		try {
			Set<Zone> infoInDirection = this.getNeighbours().get(direction).keySet();
			for(Zone zone : infoInDirection) {
				return zone;
			}
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}



	/**
	 * @param direction the direction we want to check the neighbour
	 * @return boolean, true: if both of the neighbour given by the direction and this are open
	 */
	public boolean getBooleanInDirection(Direction direction) {
		Zone targetZone = this.getZoneInDirection(direction);
		if (targetZone == null) {
			return false;
		}
		return this.neighbours.get(direction).get(targetZone) && targetZone.neighbours.get(direction.getOpposite()).get(this);
	}

	/**
	 * @param direction direction we want to check the neighbour
	 * @param newValue boolean, true for open, false for close
	 */
	public void setBooleanInDirection(Direction direction, boolean newValue) {
		Zone targetZone = this.getZoneInDirection(direction);
		this.neighbours.get(direction).put(targetZone, newValue);
		targetZone.neighbours.get(direction.getOpposite()).put(this, newValue);
	}

	/**
	 * Open the door in the given direction
	 * @param direction direction of zone to open the door
	 */
	public void openDoor(Direction direction){
		Zone zoneOpen = this.getZoneInDirection(direction);
		boolean spawn = true;
		if (!(this instanceof Continental)) {
			for (Direction d : Direction.values()) {
				if (this.getBooleanInDirection(d)) {
					spawn = false;
				}
			}
			if (spawn) {
				Random random = new Random();
				int r = random.nextInt(1, 4);
				zoneOpen.spawnZombies(r);
			}
		}
		this.setBooleanInDirection(direction, true);
	}

	/**
	 * Return a list of all neighbour's zones
	 * @return a list of all neighbour's zones
	 */
	public List<Zone> getNeighboursList() {
		List<Zone> neighboursList = new ArrayList<>();
		for (Direction direction : Direction.values()) {
			neighboursList.add(this.getZoneInDirection(direction));
		}
		return neighboursList;
	}


	/**
	 * Spawns zombies in the manhole zone.
	 */
	public void spawnZombies(int nbOfZombiesToSpawn){
		Random r = new Random();
		for (int i = 0; i < nbOfZombiesToSpawn; i++){
			int random = r.nextInt(NBTYPEZOMBIE);
			switch(random){
				case 0:
					this.spawnZombie(ZombieType.ABOMINATION);
					break;

				case 1:
					this.spawnZombie(ZombieType.RUNNER);
					break;

				case 2:
					this.spawnZombie(ZombieType.FATTY);
					break;

				default:
					this.spawnZombie(ZombieType.WALKER);
			}
		}
	}

	/**
	 * New version of spawnZombies method
	 * @param type the type of zombie to spawn
	 */
	public void spawnZombie(ZombieType type) {
		switch (type) {
			case FATTY -> this.addActorInZone(new Fatty());
			case ABOMINATION -> this.addActorInZone(new Abomination());
			case RUNNER -> this.addActorInZone(new Runner());
			default -> this.addActorInZone(new Walker());
		}
	}
}