package exceptions;

// this exception occurs when a negative number shows up in an inappropriate place
public class NegativeNumberException extends Exception {

    // constructor
    // EFFECTS: construct a new NegativeNumberException
    public NegativeNumberException() {}

    // constructor
    // EFFECTS: construct a new  NegativeNumberException, and send the message to Exception's constructor
    public NegativeNumberException(String message) {
        super(message);
    }

}
