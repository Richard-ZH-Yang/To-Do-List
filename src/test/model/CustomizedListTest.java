package model;

import exceptions.InvalidDateException;
import model.CustomizedList;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomizedListTest {
    CustomizedList list1;

    Task task1;
    Task task2;
    Task task3;
    Task task4;
    Task task5;

    @BeforeEach
    public void setup() throws InvalidDateException {
        list1 = new CustomizedList();


        task1 = new Task("Task1");
        task2 = new Task("Task2");
        task3 = new Task("Task3");
        task4 = new Task("Task4");
        task5 = new Task("Task5");

        task1.setNote("Note for task 1");
        task1.setImportant(false);
        task1.setComplete(false);
        task1.setVisible(true);

        task2.setNote("Note for task 2");
        task2.setImportant(true);
        task2.setComplete(false);
        task2.setVisible(true);
        task2.addStep("Task 2 Step 1");
        task2.addStep("Task 2 Step 2");
        task2.addStep("Task 2 Step 3");

        task3.setNote("Note for task 3");
        task3.setImportant(false);
        task3.setComplete(true);
        task3.setVisible(true);

        task4.setNote("Note for task 4");
        task4.setImportant(true);
        task4.setComplete(true);
        task4.setVisible(false);

        task5.setNote("Note for task 5");
        task5.setImportant(true);
        task5.setComplete(false);
        task5.setVisible(true);
    }

    @Test
    public void testSetListTittle() {
        assertEquals("Untitled List", list1.getListTitle());
        list1.renameList("List 1");
        assertEquals("List 1", list1.getListTitle());
    }
}
