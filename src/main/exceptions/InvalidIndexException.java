package exceptions;

// this exception occurs when the index of a array list is out of bound or negative
public class InvalidIndexException extends Exception {

    public InvalidIndexException() {}

    public InvalidIndexException(String message) {
        super(message);
    }
}
