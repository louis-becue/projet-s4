package zombicide.actor.survivor.action;

public class ActionExecuteException extends Exception {
    public ActionExecuteException(String errorMsg) {super(errorMsg);}

    public static String generateErrorMsg(String actionName, String errorType) {return "Cannot execute " + actionName + " action : " + errorType;}
}
