package exceptions;

import org.junit.jupiter.api.Test;

public class InvalidDateExceptionTest {
    @Test
    public void testInvalidDateExceptionConstructor () {
        try {
            throw new InvalidDateException();
        } catch (InvalidDateException e) {
            // pass
        }

        try {
            throw new InvalidDateException("Message");
        } catch (InvalidDateException e) {
            // pass
        }
    }
}
