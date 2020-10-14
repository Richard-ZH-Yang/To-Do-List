package model;

import exceptions.InvalidDateException;
import exceptions.InvalidIndexException;
import exceptions.ListFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BasicListTest {
    BasicList list1;
    BasicList list2;
    BasicList list3;

    Task task1;
    Task task2;
    Task task3;
    Task task4;

    @BeforeEach
    public void setup() throws InvalidDateException {
        list1 = new TasksList();
        list2 = new TasksList();
        list3 = new TasksList();

        task1 = new Task("Task1");
        task2 = new Task("Task2");
        task3 = new Task("Task3");
        task4 = new Task("Task4");

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
        task4.setComplete(false);
        task4.setVisible(false);
    }

    @Test
    public void testGetters() {

    }

    @Test
    public void testAddTask() {
        assertEquals(0, list1.getTaskList().size());
        for (int i = 0; i < BasicList.MAX_LENGTH; i ++) {
            try {
                Task myTask = new Task();
                list1.addTask(myTask);
                assertEquals(myTask, list1.getTaskList().get(i));
            } catch (InvalidDateException | ListFullException exception) {
                fail("This should have not thrown an exception.");
            }
        }
        assertEquals(BasicList.MAX_LENGTH, list1.getTaskList().size());

        try {
            list1.addTask(new Task());
        } catch (InvalidDateException invalidDateException) {
            fail("This should have not thrown an exception.");
        } catch (ListFullException listFullException) {
            System.out.println(listFullException.getMessage());
        } finally {
            assertEquals(BasicList.MAX_LENGTH, list1.getTaskList().size());
        }
    }

    public void fullList() {

    }


    @Test
    public void testRemoveTask() {
        testAddTask(); // a full list1, taskList
        Task taskIndex1 = list1.taskList.get(1); // assign the second object to taskIndex1
                                                // after removing first one, it should become
                                                // the first element
        try {
            list1.removeTask(0, 0);
        } catch (InvalidIndexException invalidIndexException) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(taskIndex1, list1.getTaskList().get(0));
            assertEquals(BasicList.MAX_LENGTH - 1, list1.getTaskList().size());
        }

        // invalid whichList number
        try {
            list1.removeTask(2, 0);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(taskIndex1, list1.getTaskList().get(0));
            assertEquals(BasicList.MAX_LENGTH - 1, list1.getTaskList().size());
        }

        // invalid index number for whichList = 0
        try {
            list1.removeTask(0, BasicList.MAX_LENGTH - 1);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(taskIndex1, list1.getTaskList().get(0));
            assertEquals(BasicList.MAX_LENGTH - 1, list1.getTaskList().size());
        }

        // invalid index number for whichList = 1
        try {
            list1.removeTask(0, 0);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(0, list2.getTaskList().size());
        }

        // TODO task1 add completedTaskList

    }


}
