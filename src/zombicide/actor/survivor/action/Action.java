package zombicide.actor.survivor.action;

import zombicide.actor.survivor.Survivor;

public abstract class Action {

    protected int cost;
    protected String title;


    /**
     * Check if a survivor can execute action (has enough action points)
     * @param survivor tne survivor to execute the action
     * @return true if survivor has enough action points, throw exception otherwise
     */

    protected boolean canDoAction(Survivor survivor) {
        return (survivor.getRemainingActionPoints() >= this.cost);
    }
    public void doAction(Survivor survivor) throws ActionExecuteException  {}

    public String toString(){
        return this.title;
    }
}

