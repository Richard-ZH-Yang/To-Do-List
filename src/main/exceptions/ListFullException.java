package exceptions;

//this exception occurs when an basicList is already full
public class ListFullException extends Exception {

    // constructor
    // EFFECTS: construct a new ListFullException
    public ListFullException() {}

    // constructor
    // EFFECTS: construct a new  ListFullException, and send the message to Exception's constructor
    public ListFullException(String message) {
        super(message);
    }

}
