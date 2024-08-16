package zombicide.actor.survivor;
import zombicide.actor.survivor.action.Action;

import java.util.*;

public enum Role {
	BASE(5,3,1,0),NOSY(5,3,1,0), FIGHTER(5,3,1,1), LUCKY(5,3,2,0), HEALER(5,3,1,0);
	private int health;
	private int actionPoints;
	private ArrayList<Action> possibleActions;
	private int diceThrows;
	private int baseAttack;

	public static final ArrayList<Role> NOSYROLES = new ArrayList<>() {{add(Role.BASE);add(Role.NOSY);}};
	public static final ArrayList<Role> FIGHTERS = new ArrayList<>() {{add(Role.BASE);add(Role.FIGHTER);}};
	public static final ArrayList<Role> LUCKYROLES = new ArrayList<>() {{add(Role.BASE);add(Role.LUCKY);}};
	public static final ArrayList<Role> HEALERROLES = new ArrayList<>() {{add(Role.BASE);add(Role.HEALER);}};


	/**
	 * 
	 * @param health The amount of health points for the role
	 * @param actionPoints	The amount of possible actions for the role
	 * @param diceThrows	The amount of possible dice throws per turn for the role
	 * @param baseDamage	The amount of additional base damage per attack for the role
	 */
	 Role(int health,int actionPoints,int diceThrows,int baseDamage) {

		this.health = health;
		this.actionPoints = actionPoints;
		this.diceThrows = diceThrows;
		this.baseAttack = baseDamage;
		this.possibleActions = new ArrayList<>();

	}
	public int getHealth() {
		return this.health;
	}
	public int getActionPoints() {
		return this.actionPoints;
	}
	public ArrayList<Action> getPossibleActions() {
		return this.possibleActions;
	}
	public int getDiceThrows() {
		return this.diceThrows;
	}
	public int getBaseDamage() {
		return this.baseAttack;
	}
	public void addAction(Action action) {
		this.possibleActions.add(action);
	}
	
}
