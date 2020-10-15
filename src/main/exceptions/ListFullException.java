package exceptions;

//this exception occurs when an array list is already full
public class ListFullException extends Exception {

    public ListFullException() {}

    public ListFullException(String message) {
        super(message);
    }

}
