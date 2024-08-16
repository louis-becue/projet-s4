package zombicide.actor.survivor.action;
import zombicide.actor.survivor.*;
public class MakeNoise extends Action{

    /**
     * Constructor for class MakeNoise
     */
    public MakeNoise() {
        this.cost = 1;
        this.title = "MakeNoise";
    }

    /**
     * Increase the amount of noise in the zone where the survivor is
     * @param survivor The survivor that makes noise
     */
    public void doAction(Survivor survivor) throws ActionExecuteException {
        if (this.canDoAction(survivor)) {
            survivor.getZone().increaseNoiseLevel(4);
            survivor.useActionPoints(this.cost);
        } else {
            throw new ActionExecuteException(ActionExecuteException.generateErrorMsg("MakeNoise", "Not enough action points"));
        }
    }
}
