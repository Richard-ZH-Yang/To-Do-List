package exceptions;

// this exception occurs when a negative number shows up in an inappropriate place
public class NegativeNumberException extends Exception {

    public NegativeNumberException() {}

    public NegativeNumberException(String message) {
        super(message);
    }

}
