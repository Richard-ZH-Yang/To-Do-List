package persistence;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoListProgram checkToDo = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (ListFullException | InvalidDateException e) {
            fail("This should not occur");
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyToDoListProgram.json");
        try {
            ToDoListProgram checkToDo = reader.read();
            checkToDoListProgram(checkToDo, new ToDoListProgram());
        } catch (IOException | ListFullException | InvalidDateException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() throws FileNotFoundException {
        // TODO to be done
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            ToDoListProgram checkToDo = reader.read();
            checkToDoListProgram(checkToDo, new ToDoListProgram());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (ListFullException | InvalidDateException e) {
            fail("This should not occur");
        }
    }
}