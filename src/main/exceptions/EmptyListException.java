package exceptions;

// this exception occurs when the list is empty
public class EmptyListException extends Exception {

    public EmptyListException() {}

    public EmptyListException(String message) {
        super(message);
    }

}
