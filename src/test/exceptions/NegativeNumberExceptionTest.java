package exceptions;

import org.junit.jupiter.api.Test;

public class NegativeNumberExceptionTest {
    @Test
    public void testNegativeNumberExceptionConstructor () {
        try {
            throw new NegativeNumberException();
        } catch (NegativeNumberException e) {
            // pass
        }

        try {
            throw new NegativeNumberException("Message");
        } catch (NegativeNumberException e) {
            // pass
        }
    }
}
