package zombicide.actor;

import zombicide.actor.zombie.Abomination;
import zombicide.actor.zombie.Fatty;
import zombicide.util.Direction;
import zombicide.zone.Zone;

public abstract class Actor {
    protected int health;
    protected Zone zone;


    /**
     * Build an actor.
     *
     * @param health the number of life point for the actor
     */
    public Actor(int health){
        this.health = health;
    }

    /**
     * @return the zone if the survivor.
     */
    public Zone getZone() {
        return this.zone;
    }

    /**
     * Set actors zone to given zone
     * @param zone the new zone for the actor
     */
    public void setZone(Zone zone) {this.zone = zone; }

    /**
     * @return the health level of the survivor.
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * Return if the actor is alive.
     *
     * @return - True if the actor is alive.
     *         - False if the actor is dead.
     */
    public boolean isAlive(){
        return (this.health > 0);
    }

    /**
     * Change the value of the health when the actor is attack.
     *
     * @param damage the damage cause during the attack
     */
    public void isAttacked(int damage){
        if (this instanceof Fatty || this instanceof Abomination)
            return;
        this.health = this.health - damage;
        if (this.getHealth() <= 0){
            this.getZone().removeActorFromZone(this);
            System.out.println(this.toString() + " died !");
        }
    }

    /**
     * Move actor in given zone.
     * Side effect : modify zone attribute for actor
     * @param target the zone to move the actor in
     */
    public void move(Zone target) {
        Zone previousZone = this.zone;
        this.zone = target;

        if (previousZone != null){
            previousZone.removeActorFromZone(this);
        }
        target.addActorInZone(this);
    }

    /**
     * Move actor in the given direction
     * @param direction the direction to move the actor
     */
    public void move(Direction direction) {
        this.move(this.zone.getZoneInDirection(direction));
    }

    /**
     * Remove the actor from the board (i.e. from the zone he's currently in)
     */
    public void removeFromBoard() {
        this.zone.removeActorFromZone(this);
        this.setZone(null);
    }
}
