package persistence;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.BasicList;
import model.Task;
import model.ToDoListProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// reference:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoListProgram toDoListProgram = new ToDoListProgram();
            JsonWriter writerMistake = new JsonWriter("./data/my\0illegal:fileName.json");
            writerMistake.open();
            fail("IOException should throw");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ToDoListProgram toDoListProgram = new ToDoListProgram();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoListProgram.json");
            writer.open();
            writer.write(toDoListProgram);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoListProgram.json");
            ToDoListProgram checkToDo = reader.read();
            checkToDoListProgram(checkToDo, toDoListProgram);

        } catch (IOException | ListFullException | InvalidDateException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoListProgram.json");
            ToDoListProgram toDoListProgram = new ToDoListProgram();

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

            toDoListProgram.getDefaultList().get(0).getTaskList().add(task1);
            toDoListProgram.getDefaultList().get(0).getTaskList().add(task2);

            BasicList inCustomizedList = new BasicList();
            inCustomizedList.addTask(task1);
            inCustomizedList.addTask(task2);
            inCustomizedList.addTask(new Task());
            inCustomizedList.finishTask(2);
            toDoListProgram.getCustomizedList().add(inCustomizedList);

            writer.open();
            writer.write(toDoListProgram);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoListProgram.json");
            ToDoListProgram checkToDo = reader.read();
            checkToDoListProgram(checkToDo, toDoListProgram);

        } catch (IOException | InvalidDateException | ListFullException e) {
            fail("Exception should not have been thrown");
        }
    }

}
