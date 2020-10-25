package exceptions;

// this exception occurs when the date is invalid in terms of format. Date format should be "year-mn-dy" in numbers
public class InvalidDateException extends Exception {

    // constructor
    // EFFECTS: construct a new InvalidDateException
    public InvalidDateException() {}

    // constructor
    // EFFECTS: construct a new  InvalidDateException, and send the message to Exception's constructor
    public InvalidDateException(String message) {
        super(message);
    }

}
