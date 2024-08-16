package zombicide.actor.zombie;


public class Abomination extends Zombie{

	/**
	 * build an abomination.
	 */
	public Abomination() {
		super(6,3,1, ZombieType.ABOMINATION);
	}

	/**
	 * Method to return a string to represent the zombie. It corresponds to his type
	 * @return the type of the zombie
	 */
	public String toString(){
		return "Abomination";
	}

}
