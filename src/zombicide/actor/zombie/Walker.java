package zombicide.actor.zombie;


public class Walker extends Zombie{

	/**
	 * build a Walker.
	 */
	public Walker() {
		super(1,1,1, ZombieType.WALKER);
	}

	/**
	 * Method to return a string to represent the zombie. It corresponds to his type
	 * @return the type of the zombie
	 */
	public String toString(){
		return "Walker";
	}
}

