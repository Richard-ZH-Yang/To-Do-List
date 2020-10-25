package exceptions;

// this exception occurs when the list is empty
public class EmptyListException extends Exception {

    // constructor
    // EFFECTS: construct a new EmptyListException
    public EmptyListException() {}

    // constructor
    // EFFECTS: construct a new  EmptyListException, and send the message to Exception's constructor
    public EmptyListException(String message) {
        super(message);
    }

}
