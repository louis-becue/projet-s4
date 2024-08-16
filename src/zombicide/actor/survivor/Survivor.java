package zombicide.actor.survivor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;


import zombicide.Game;
import zombicide.actor.Actor;
import zombicide.actor.survivor.action.Action;
import zombicide.actor.survivor.action.ActionExecuteException;
import zombicide.actor.zombie.Zombie;
import zombicide.board.Board;
import zombicide.item.Item;
import zombicide.item.Weapon;
import zombicide.item.items.*;
import zombicide.util.Direction;
import zombicide.zone.room.Continental;

import static java.lang.Math.max;

public class Survivor extends Actor {
	private int remainingActionPoints;
	private int actionPoints;
	private List<Action> actions;
	private List<Action> possibleActions;
	private int diceThrows;
	private int baseDamage;

	private String name;
	private List<Role> roles;
	private List<Item> items;
	private int level;
	private final int maxBagSize;
	private Item itemInHand;

	private int maxHealth;


	/**
	 * Build a survivor.
	 *
	 * @param listOfRoles the list of the roles of the survivor.
	 */
	public Survivor(String name, List<Role> listOfRoles){
		super(5);
		this.name = name;
		this.items = new ArrayList<>();
		this.roles = listOfRoles;
		this.level = 1;
		this.maxBagSize = 5;
		this.itemInHand = null;
		this.possibleActions = new ArrayList<>();
		this.setAttributes();
	}

	/**
	 * Getter that returns the base action points of the survivor. It does not return the remaining action points, but the amount that is reset at every turn
	 * @return The BASE action points (the amount that is reset at every turn)
	 */
	public int getBaseActionPoints(){
		return this.actionPoints;
	}

	/**
	 * Return the base damage to the survivor
	 * @return The base damage to the survivor
	 */
	public int getBaseDamage(){
		return this.baseDamage;
	}

	/**
	 * Return the number of dices the player can throw at each turn
	 * @return The number of dices the player can throw at each turn
	 */
	public int getDiceThrows(){
		return this.diceThrows;
	}

	/**
	 * Return the max health of the survivor
	 * @return The max health of the survivor
	 */
	public int getMaxHealth(){ return this.maxHealth;}
	/**
	 * Return the actions possible for the survivor.
	 *
	 * @return a list of the actions possible
	 */
	public List<Action> getPossibleActions(){
		return this.possibleActions;
	}

	/**
	 * @return the amount of remaining actions points of the survivor.
	 */
	public int getRemainingActionPoints(){
		return this.remainingActionPoints;
	}

	/**
	 * Remove the given amount of action points to the survivor
	 * @param cost the amount of action points to remove to the survivor
	 */
	public void useActionPoints(int cost) {this.remainingActionPoints = this.remainingActionPoints - cost; }

	/**
	 * Reset the amount of action points of the survivor
	 */
	public void resetRemainingActionPoints() {this.remainingActionPoints = this.actionPoints; }

	/**
	 * @return the survivor's level.
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * @return an item's list of the survivor.
	 */
	public List<Item> getItems(){
		return this.items;
	}

	/**
	 * @return the role(s) of the survivor.
	 */
	public List<Role> getRoles() {
		return this.roles;
	}

	/**
	 * add an Item on the item's list of the survivor.
	 *
	 * @param e the items to add
	 */
	public void addItem(Item e){
		if (this.items.size() < this.maxBagSize){
			this.items.add(e);
			e.setPossessor(this);
		} else {
			System.out.println("Cet item n'a pas pu être ajouté ! Votre sac est plein");
		}
	}

	/**
	 * add an action on the action's list of the survivor.
	 *
	 * @param e the items to add
	 */
	public void addAction(Action e){
		this.actions.add(e);
	}

	/**
	 * Increment the level by 1, and add an action point if the level is part of [3,7,11]
	 */
	public void upgradeLevel(){
		this.level ++;
		int[] upgradeLevels = new int[]{3,7,11};

		if (Arrays.binarySearch(upgradeLevels,this.level) >= 0){
			this.actionPoints++;
		}
	}


	/**
	 * Attack the specified zombie with the weapon in hand
	 *
	 * @param weapon the weapon to attack the zombie with
	 * @param zombie the zombie to attack
	 */
	public void attack(Weapon weapon, Zombie zombie) {
		zombie.isAttacked(weapon.getDamage());
		if (!zombie.isAlive()) {
			this.upgradeLevel(); // survivor earns one level each time he kills a zombie
		}
	}

	/**
	 * Change the value of the health with a new value.
	 *
	 * @param x the new value of the health
	 */
	public void setHealth(int x){
		this.health = x;
	}

	public void increaseHealth(int amount) {this.health += amount; }

	/**
	 * Method to make the survivor play his turn.
	 */
	public void playTurn() throws ActionExecuteException {
		Scanner scanner = new Scanner(System.in);
		while (this.canPlay()){
			System.out.println(this.getInformations());
			System.out.println(this.getRemainingActionPoints() + " remaining action points for this turn.");
			try {
				Object choice = Game.lc.choose("Quelle action ?", this.possibleActions);
				Action action = (Action) choice;
				action.doAction(this);
			} catch (ActionExecuteException e){
				System.out.println(e.getMessage());
			} catch (NullPointerException e){
				System.out.println();
			}
		}
		this.resetRemainingActionPoints();
	}



	/**
	 * Method to check if the survivor can play
	 * @return True if the survivor can play
	 */
	public boolean canPlay(){
		return this.isAlive()&&this.remainingActionPoints>0;
	}

	/**
	 * @return the item in survivor's hand
	 */
	public Item getItemInHand() {
		return this.itemInHand;
	}

	public int getMaxBagSize(){
		return this.maxBagSize;
	}

	/**
	 * @param role the role for the survivor
	 * add a role in the list of roles for this survivor and add actions for the given role
	 */
	public void addRole(Role role){
		this.roles.add(role);
		this.setActions();
		this.setAttributes();
	}

	/**
	 * @param role the role to delete from survivor's role list
	 * remove role from survivor's role list
	 */
	public void removeRole(Role role){
		this.roles.remove(role);
		this.possibleActions = new ArrayList<>();
		this.setActions();
		this.setAttributes();
	}

	/**
	 * @param i item to put in survivor's hand
	 * put the given item in the survivor's hand if it's in the survivor's inventory
	 */
	public void setItemInHand(Item i) {
		if (this.items.contains(i)) {
			this.itemInHand = i;
			this.items.remove(i);
		}
	}

	/**
	 * remove the current item in the survivor's hand
	 */
	public void removeItemInHand(){
		this.itemInHand = null;
	}

	public void removeItem(Item i){
		if (this.getItems().contains(i)){
			this.items.remove(i);
		}
	}

	/**
	 * @param other item in inventory which we want to be in the survivor's hand
	 * Remove the current item in survivor's hand and replace item with the item other
	 * To use if survivor's inventory is full
	 */
	public void replaceItemInHandToItems(Item other){
		// TODO : rework this method because of the condition
		if (this.items.size() == 5 && this.items.contains(other)){
			Item i = this.itemInHand;
			this.removeItemInHand();
			this.addItem(i);
			this.setItemInHand(other);
			this.items.remove(i);
		}
	}

	/**
	 * Use the item in survivors hand. board and target parameters HAVE to be passed even if
	 * unused, because this method do the item distinction to use the appropriate effect method
	 * @param board the board (for MapItem)
	 * @param target the target (for AidKit)
	 * @throws ItemUsesException if item can't be used
	 */
	public void useItem(Board board, Survivor target, Direction d) throws ItemUsesException {
		if (this.itemInHand != null) {
			if (this.itemInHand instanceof MapItem item) {
				item.effect(board);
			} else if (this.itemInHand instanceof InfraredGlasses item) {
				item.effect(board);
			} else if (this.itemInHand instanceof AidKit item) {
				item.effect(target);
			} else if (this.itemInHand instanceof MasterKey item){
				item.effect(d);
			} else {
				this.itemInHand.effect();
			}

		}
	}

	/**
	 * Set possibleActions attribute for survivor by using its given roles. Make sure to update
	 * survivors roles before !
	 */
	public void setActions() {
		List<Action> result = new ArrayList<>();
		for (Role role : this.roles) {
			ArrayList<Action> possibleRoleActions = role.getPossibleActions();
			for (Action possibleAction : possibleRoleActions) {
				// two roles can have the same action, so we have to check if it is already
				// in the result or not
				if (!result.contains(possibleAction)) {
					result.add(possibleAction);
				}
			}
		}
		this.possibleActions = result;
	}

	/**
	 * Method to initialize all the attributes of the survivor, depending on his roles. This method is called in the constructor.
	 * For each role the survivor has, we check all the attributes, and take the best.
	 */
	private void setAttributes(){
		int health = 0;
		int baseDamage = 0;
		int diceThrows = 0;
		int actionPoints = 0;

		for (Role role : this.roles){
			health = max(health, role.getHealth());
			baseDamage = max(baseDamage,role.getBaseDamage());
			diceThrows = max(diceThrows,role.getDiceThrows());
			actionPoints = max(actionPoints,role.getActionPoints());
		}
		this.health = health;
		this.maxHealth = health;
		this.baseDamage = baseDamage;
		this.diceThrows = diceThrows;
		this.actionPoints = actionPoints;
		this.remainingActionPoints = actionPoints;
	}

	/**
	 * @return the name, the level, the role, the number of health points and the item in survivor's hand
	 */
	public String getInformations() {
		return "The survivor " + this.name + " with a level of " + this.level + ", he is a " + this.roles + ",he has " + this.health + " health points, and has in this hand : " + this.itemInHand;
	}

	public String toString() {
		return this.name;
	}

	public String getName() {
		return this.name;
	}
}
