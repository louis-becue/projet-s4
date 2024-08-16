package zombicide.actor.zombie;


public class Runner extends Zombie{

	/**
	 * Build a runner.
	 */
	public Runner() {
		super(2,1,2, ZombieType.RUNNER);
	}

	/**
	 * Method to return a string to represent the zombie. It corresponds to his type
	 * @return the type of the zombie
	 */
	public String toString(){
		return "Runner";
	}
}
