package exceptions;

import org.junit.jupiter.api.Test;

public class ListFullExceptionTest {
    @Test
    public void testListFullExceptionConstructor () {
        try {
            throw new ListFullException();
        } catch (ListFullException e) {
            // pass
        }

        try {
            throw new ListFullException("Message");
        } catch (ListFullException e) {
            // pass
        }
    }
}
