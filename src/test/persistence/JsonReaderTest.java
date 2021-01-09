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
            Task task1 = new Task();
            task1.setOverDue(false);
            task1.setVisible(true);
            task1.setComplete(false);
            task1.setImportant(true);
            task1.setNote("This is the note for task 1");
            task1.setDueDay("2020-10-30");
            task1.setTitle("task1");
            task1.addStep("This is task 1 step 1");
            task1.addStep("step 2");
            task1.addStep("step 3");
            task1.completeStep(1);

            Task task2 = new Task();
            task2.setOverDue(false);
            task2.setVisible(false);
            task2.setComplete(false);
            task2.setImportant(false);
            task2.setNote("This is the note for task 2");
            task2.setDueDay("2020-10-30");
            task2.setTitle("task2");
            task2.addStep("This is task 2 step 1");
            task2.addStep("step 2");
            task2.completeStep(0);

            toDoListProgram.getSpecificBasicList(0).getTaskList().add(task1);
            toDoListProgram.getSpecificBasicList(0).getTaskList().add(task2);

            BasicList inCustomizedList = new BasicList();
            inCustomizedList.addTask(task1);
            inCustomizedList.addTask(task2);
            inCustomizedList.addTask(new Task());
            inCustomizedList.finishTask(2);
            toDoListProgram.addBasicList(inCustomizedList);


            ToDoListProgram checkToDo = reader.read();
            checkToDoListProgram(checkToDo, toDoListProgram);
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (ListFullException | InvalidDateException e) {
            fail("This should not occur");
        }
    }
}