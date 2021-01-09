package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ToDoListProgramTest {
    ToDoListProgram toDoListProgram;

    @BeforeEach
    public void setup() {
        toDoListProgram = new ToDoListProgram();
    }

    @Test
    public void testIsValidNoException() {
        try {
            toDoListProgram.isValid(0, 1, 1);
        } catch (IndexOutOfBoundsException e) {
            fail("This should not occur");
        }
    }

    @Test
    public void testIsValidWithException() {
        try {
            toDoListProgram.isValid(0, 1, 2);
            fail("Should throw IndexOutOfBoundsException ");
        } catch (IndexOutOfBoundsException e) {
            //pass
        }

        try {
            toDoListProgram.isValid(0, 1, -1);
            fail("Should throw IndexOutOfBoundsException ");
        } catch (IndexOutOfBoundsException e) {
            //pass
        }
    }

}
