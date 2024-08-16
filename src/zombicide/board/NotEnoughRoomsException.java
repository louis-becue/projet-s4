package zombicide.board;

/**
 * Exception that is thrown when trying to add more specials rooms that there is rooms in the board
 */
public class NotEnoughRoomsException extends Exception {
    public NotEnoughRoomsException(String errorMessage){
        super(errorMessage);
    }
}
