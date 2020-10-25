package persistence;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
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
            ToDoListProgram toDoListProgram = new ToDoListProgram();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoListProgram.json");
            writer.open();
            writer.write(toDoListProgram);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoListProgram.json");
            ToDoListProgram checkToDo = reader.read();
            // TODO test more situations

            checkToDoListProgram(checkToDo, toDoListProgram);

        } catch (IOException | InvalidDateException | ListFullException e) {
            fail("Exception should not have been thrown");
        }
    }

}
