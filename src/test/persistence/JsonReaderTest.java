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
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        ToDoListProgram toDoListProgram = new ToDoListProgram();

        try {
            toDoListProgram.getDefaultList().get(0).addTask(new Task("task1 in my day"));
            toDoListProgram.getCustomizedList().add(new BasicList("list1 in customized list"));
            toDoListProgram.getCustomizedList().add(new BasicList("list2"));
            toDoListProgram.getCustomizedList().get(0).getCompletedTaskList().add(new Task("task1 c1 l1"));
            toDoListProgram.getCustomizedList().get(0).getCompletedTaskList().get(0).addStep("s1");
            toDoListProgram.getCustomizedList().get(0).getCompletedTaskList().get(0).addStep("s2");
            toDoListProgram.getCustomizedList().get(0).getCompletedTaskList().get(0).completeStep(1);
            toDoListProgram.getCustomizedList().get(0).getCompletedTaskList().get(0).setComplete(true);

            ToDoListProgram checkToDo = reader.read();
            checkToDoListProgram(checkToDo, toDoListProgram);
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (ListFullException | InvalidDateException e) {
            fail("This should not occur");
        }
    }
}