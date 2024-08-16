package zombicide.actor.survivor.action;

import zombicide.Game;
import zombicide.actor.survivor.Survivor;
import zombicide.actor.zombie.Zombie;
import zombicide.item.Weapon;
import zombicide.util.Direction;
import zombicide.zone.Zone;
import zombicide.zone.room.Continental;
import zombicide.actor.survivor.Survivor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Heal extends Action {
    public Heal() {
        this.cost = 1;
        this.title = "Heal";
    }
    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {

            ArrayList<Survivor> survivorsToHeal = survivor.getZone().getSurvivors();
            Survivor survivorToHeal = (Survivor) Game.lc.choose("Survivor to heal : ", survivorsToHeal  );

            if(survivorToHeal.getHealth() < survivorToHeal.getMaxHealth()){
                survivorToHeal.increaseHealth(1);
                System.out.println(survivorToHeal.toString() + " healed for 1 health points. He now has "+ survivorToHeal.getHealth());

            }
            else{
                throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Heal", "This survivor is already at max health"));

            }
        }
        else{
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("Heal", "Not enough action points"));
        }
    }
}
