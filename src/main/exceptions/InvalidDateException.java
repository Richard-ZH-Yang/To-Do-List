package exceptions;

// this exception occurs when the date is invalid
public class InvalidDateException extends Exception {

    public InvalidDateException() {}

    public InvalidDateException(String message) {
        super(message);
    }

}
