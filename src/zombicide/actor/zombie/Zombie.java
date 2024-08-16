package zombicide.actor.zombie;

import java.util.Random;

import zombicide.actor.Actor;
import zombicide.actor.survivor.Survivor;
import zombicide.util.Direction;
import zombicide.zone.NotEnoughSurvivorException;
import zombicide.zone.Zone;
import zombicide.zone.room.Continental;

public abstract class Zombie extends Actor {
	protected int damage;
	protected int numberActions;
	protected ZombieType type;

	protected int getDamage() {
		return this.damage;
	}

	/**
	 * build a Zombie.
	 *
	 * @param health the number of point of live of a zombie
	 * @param damage the damage cause by the zombie during an attack.
	 * @param numberActions the number of action that a zombie
	 */
	protected Zombie(int health, int damage, int numberActions, ZombieType type){
		super(health);
		this.damage = damage;
		this.numberActions = numberActions;
		this.type = type;
	}


	/**
	 * @return the numbers of action that the zombie can do on a turn.
	 */
	protected int getNumberActions() {
		return this.numberActions;
	}

	/**
	 * Display the information of the zombie for the survivor.
	 */
	protected void displayInformation(){
		System.out.println("It's a zombie with " + this.health + " health point and he can make " + this.damage + " damage.");
	}

	/**
	 * Attack a survivor entered in parameters.
	 * The damage is calculated from the zombie base damage.
	 *
	 * @param survivor The survivor who is attack.
	 */
	public void attack(Survivor survivor){
		if (this.getZone().getHostile()) {
			Random rd = new Random();
			int rdmNumber = rd.nextInt(3);
			survivor.isAttacked(this.damage + rdmNumber);
			System.out.println(this.toString() + " attacked " + survivor.toString() + " for " + (this.damage + rdmNumber) + " health points");
		}
	}


	/**
	 * Method that make the zombie play his turn. First, it checks if the zombie is alive. If he is not, this method does nothing.
	 * Otherwise, he tries to attack a survivor in his zone, and if there are none, then he moves to the noisiest zone of the board.
	 * @param noisiestZone The noisiest zone of the board.
	 */
	public void playTurn(Zone noisiestZone){

		if (this.isAlive()) {
			try {
				Survivor survivor = this.zone.getRandomSurvivor();
				this.attack(survivor);

			} catch (NotEnoughSurvivorException e) {

				Direction direction = this.zone.getPosition().calculateDirection(noisiestZone.getPosition());
				if (this.getZone().getBooleanInDirection(direction)) {
					this.move(direction);
				}
			}
		}
	}

	public boolean canPlay(){
		return this.isAlive();
	}

	public ZombieType getType() {
		return this.type;
	}

	/**
	 * Returns the zone to go to reach the noisiest zone in the board. This zone is a zone attached to the initial one.
	 * It uses the method calculateMove from Position, that calculates the easiest way to go from a pos to another one.
	 * The method from Position gives us an ArrayList with all the directions, sorted so the direction at index 0 is better than others
	 *
	 * @param noisiestZone the noisiest zone of the board.
	 * @return the adjacent zone that leads to the noisiest zone
	 */
	/*
	protected Zone calculateMove(Zone noisiestZone) {
		ArrayList<Direction> directions = this.zone.getPosition().calculateDirection(noisiestZone.getPosition());
		Zone res;
		res = this.zone.getNeighbors().get(Direction.EAST);
		Direction dir = directions.get(0);
		if (this.zoneNeighbors.get(dir) != null) {
			res = this.zone.getNeighbors().get(dir);
		}
		return res;
	}
	*/
	
	
	
}