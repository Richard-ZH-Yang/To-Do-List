package exceptions;

import org.junit.jupiter.api.Test;

public class EmptyListExceptionTest {
    @Test
    public void testEmptyListExceptionConstructor() {
        try {
            throw new EmptyListException();
        } catch (EmptyListException e) {
            // pass
        }

        try {
            throw new EmptyListException("Message");
        } catch (EmptyListException e) {
            // pass
        }
    }
}
