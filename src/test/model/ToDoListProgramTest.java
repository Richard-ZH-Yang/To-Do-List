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
    }

    @Test
    public void testSetCustomizedList() {
        List<BasicList> basicLists = new ArrayList<>();
        basicLists.add(new BasicList("List name"));

        toDoListProgram.setCustomizedList(basicLists);
        assertEquals(1, toDoListProgram.getCustomizedList().size());
        assertEquals("List name", toDoListProgram.getCustomizedList().get(0).getListTitle());
    }

    @Test
    public void testSetDefaultList() {
        List<BasicList> basicLists = new ArrayList<>();
        basicLists.add(new BasicList("List name"));

        toDoListProgram.setDefaultList(basicLists);
        assertEquals(1, toDoListProgram.getDefaultList().size());
        assertEquals("List name", toDoListProgram.getDefaultList().get(0).getListTitle());
    }
}
