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
public class JsonWriterTest {

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
            toDoListProgram = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numThingies());
        } catch (IOException | ListFullException | InvalidDateException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            wr.addThingy(new Thingy("saw", Category.METALWORK));
            wr.addThingy(new Thingy("needle", Category.STITCHING));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            List<Thingy> thingies = wr.getThingies();
            assertEquals(2, thingies.size());
            checkThingy("saw", Category.METALWORK, thingies.get(0));
            checkThingy("needle", Category.STITCHING, thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
