package model;

import exceptions.InvalidDateException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    Task task;

    @BeforeEach
    public void setup() throws InvalidDateException {
        task = new Task();
    }

    @Test
    public void testSetTodayDate() {
        assertEquals("2020-10-12", task.getTodayDate());
    }

}
