package zombicide.actor.zombie;


public class Fatty extends Zombie{

	/**
	 * build a fatty.

	 */
	public Fatty() {
		super(4,2,1, ZombieType.FATTY);
	}
	/**
	 * Method to return a string to represent the zombie. It corresponds to his type
	 * @return the type of the zombie
	 */
	public String toString(){
		return "Fatty";
	}

}
