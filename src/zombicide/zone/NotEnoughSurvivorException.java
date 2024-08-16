package zombicide.zone;


/**
 * Exception that is thrown when trying to add more specials rooms that there is rooms in the board
 */
public class NotEnoughSurvivorException extends Exception{
    public NotEnoughSurvivorException(String errorMessage) { super(errorMessage);}
}
