package model;

import exceptions.InvalidDateException;
import exceptions.InvalidIndexException;
import exceptions.ListFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicListTest {
    BasicList list1;
    BasicList list2;
    BasicList list3;

    Task task1;
    Task task2;
    Task task3;
    Task task4;
    Task task5;

    @BeforeEach
    public void setup() throws InvalidDateException {
        list1 = new BasicList();
        list2 = new BasicList();
        list3 = new BasicList();

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

    // EFFECTS: add four tasks to list 1 taskList
    public void addTasksToList1() {
        // taskList : 1,2,5
        // completedList: 3,4
        try {
            list1.addTask(task1);
            list1.addTask(task2);
            list1.addTask(task3);
            list1.addTask(task4);
            list1.addTask(task5);
        } catch (Exception exception) {
            fail("This should have not thrown an exception.");
        }
    }

    @Test
    public void testGetters() {
        assertEquals("Untitled List", list1.getListTitle());
        assertEquals(true, list1.isVisible);

        addTasksToList1();

        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(task1);
        expectedTaskList.add(task2);
        expectedTaskList.add(task5);
        for (int i = 0; i < expectedTaskList.size(); i++) {
            assertEquals(expectedTaskList.get(i), list1.getTaskList().get(i));
        }

        List<Task> expectedCompletedTaskList = new ArrayList<>();
        expectedCompletedTaskList.add(task3);
        expectedCompletedTaskList.add(task4);
        for (int i = 0; i < expectedCompletedTaskList.size(); i++) {
            assertEquals(expectedCompletedTaskList.get(i), list1.getCompletedTaskList().get(i));
        }

    }

    @Test
    public void testAddTask() {
        try {
            list1.addTask(task3);
            list1.addTask(task4);
        } catch (ListFullException listFullException) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(2, list1.getCompletedTaskList().size());
            assertEquals(task3, list1.getCompletedTaskList().get(0));
            assertEquals(task4, list1.getCompletedTaskList().get(1));
        }

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

    @Test
    public void testFinishTask() {
        addTasksToList1();
        assertEquals(3, list1.taskList.size());
        assertEquals(2, list1.getCompletedTaskList().size());
        try {
            assertEquals(false, list1.taskList.get(1).isComplete());
            list1.finishTask(1);
        } catch (InvalidIndexException invalidIndexException) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(2, list1.taskList.size());
            assertEquals(task5, list1.taskList.get(1));
            assertEquals(3, list1.completedTaskList.size());
            assertEquals(task2, list1.completedTaskList.get(2));
            assertEquals(true, list1.completedTaskList.get(2).isComplete());
        }

        try {
            list1.finishTask(2);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        }

        try {
            list1.finishTask(-2);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        }
    }

    @Test
    public void testUndoFinishTask() {
        addTasksToList1();
        try {
            list1.undoFinishTask(1);
        } catch (InvalidIndexException invalidIndexException) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(4, list1.taskList.size());
            assertEquals(task4, list1.taskList.get(3));
            assertEquals(1, list1.completedTaskList.size());
            assertEquals(task3, list1.completedTaskList.get(0));
            assertEquals(true, list1.completedTaskList.get(0).isComplete());
            assertEquals(false, list1.taskList.get(3).isComplete());
        }

        try {
            list1.undoFinishTask(1);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        }

        try {
            list1.undoFinishTask(-2);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        }
    }

    @Test
    public void testRemoveTaskValidValid() {
        addTasksToList1();
        try {
            list1.removeTask(0, 0);
        } catch (InvalidIndexException invalidIndexException) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(task2, list1.getTaskList().get(0));
            assertEquals(2, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }

        try {
            list1.removeTask(1, 1);
        } catch (InvalidIndexException invalidIndexException) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(task3, list1.getCompletedTaskList().get(0));
            assertEquals(2, list1.getTaskList().size());
            assertEquals(1, list1.getCompletedTaskList().size());
        }
    }

    @Test
    public void testRemoveTaskFirstInvalid() {
        addTasksToList1();
        try {
            list1.removeTask(2, 0);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(task1, list1.getTaskList().get(0));
            assertEquals(3, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }

    }

    @Test
    public void testRemoveTaskSecondInvalid() {
        addTasksToList1();

        try {
            list1.removeTask(0, 3);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(3, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }

        try {
            list1.removeTask(0, 4);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(3, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }


        try {
            list1.removeTask(0, -1);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(3, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }

        // invalid index number for whichList = 1
        try {
            list1.removeTask(1, 2);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(3, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }

        try {
            list1.removeTask(1, 3);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(3, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }


        try {
            list1.removeTask(1, -1);
        } catch (InvalidIndexException invalidIndexException) {
            System.out.println(invalidIndexException.getMessage());
        } finally {
            assertEquals(3, list1.getTaskList().size());
            assertEquals(2, list1.getCompletedTaskList().size());
        }


    }

    @Test
    public void testSortListAlphabeticallyAscending () {
        task1.setTitle("A1");
        task2.setTitle("C3");
        task3.setTitle("B3");
        task4.setTitle("A4");
        task5.setTitle("B5");
        addTasksToList1();

        assertEquals(task2, list1.getTaskList().get(1));
        list1.sortListAlphabeticallyAscending();
        assertEquals(task5, list1.getTaskList().get(1));
        assertEquals(task3, list1.getCompletedTaskList().get(1));
        assertEquals(task4, list1.getCompletedTaskList().get(0));
    }

    @Test
    public void testSortListAlphabeticallyDescending () {
        task1.setTitle("A1");
        task2.setTitle("C3");
        task3.setTitle("B3");
        task4.setTitle("A4");
        task5.setTitle("B5");
        addTasksToList1();

        assertEquals(task2, list1.getTaskList().get(1));
        list1.sortListAlphabeticallyDescending();
        assertEquals(task5, list1.getTaskList().get(1));
        assertEquals(task3, list1.getCompletedTaskList().get(0));
        assertEquals(task4, list1.getCompletedTaskList().get(1));
    }

    @Test
    public void testSortListDueDateAscending () {
        try {
            task1.setDueDay("2020-10-15");
            task2.setDueDay("2020-10-14");
            task3.setDueDay("2020-10-15");
            task4.setDueDay("2020-10-14");
            task5.setDueDay("2020-10-19");
            addTasksToList1();
        } catch (InvalidDateException ignored) { }

        assertEquals(task1, list1.getTaskList().get(0));
        list1.sortListDueDateAscending();
        assertEquals(task2, list1.getTaskList().get(0));
        assertEquals(task4, list1.getCompletedTaskList().get(0));
    }

    @Test
    public void testSortListDueDateDescending() {
        try {
            task1.setDueDay("2020-10-15");
            task2.setDueDay("2020-10-14");
            task3.setDueDay("2020-10-15");
            task4.setDueDay("2020-10-14");
            task5.setDueDay("2020-10-19");
            addTasksToList1();
        } catch (InvalidDateException ignored) { }

        assertEquals(task1, list1.getTaskList().get(0));
        list1.sortListDueDateDescending();
        assertEquals(task5, list1.getTaskList().get(0));
        assertEquals(task3, list1.getCompletedTaskList().get(0));

    }

    @Test
    public void testSortSingleListImportance() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);
        assertEquals(task1, taskList.get(0));

        BasicList.sortSingleListImportance(taskList);
        assertEquals(5, taskList.size());
        assertEquals(true, taskList.get(0).isImportant());
        assertEquals(true, taskList.get(1).isImportant());
        assertEquals(true, taskList.get(2).isImportant());
        assertEquals(false, taskList.get(3).isImportant());
        assertEquals(false, taskList.get(4).isImportant());
        assertEquals(task2, taskList.get(0));
        assertEquals(task3, taskList.get(4));
    }

    @Test
    public void testSortListImportance() {
        addTasksToList1();
        list1.sortListImportance();

        assertEquals(task2, list1.getTaskList().get(0));
        assertEquals(task5, list1.getTaskList().get(1));
        assertEquals(task1, list1.getTaskList().get(2));

        assertEquals(task4, list1.getCompletedTaskList().get(0));
        assertEquals(task3, list1.getCompletedTaskList().get(1));

    }

    @Test
    public void testSetVisibleTrue() {
        addTasksToList1();
        list1.getTaskList().get(0).setVisible(false);
        assertEquals(false, list1.getCompletedTaskList().get(1).isVisible());
        assertEquals(false, list1.getTaskList().get(0).isVisible());
        assertEquals(true, list1.isVisible);

        list1.setVisible(true);
        assertEquals(true, list1.getCompletedTaskList().get(1).isVisible());
        assertEquals(true, list1.getTaskList().get(0).isVisible());
        assertEquals(true, list1.isVisible);
    }

    @Test
    public void testSetVisibleFalse() {
        addTasksToList1();
        list1.getCompletedTaskList().get(1).setVisible(true);
        assertEquals(true, list1.isVisible);

        list1.setVisible(false);
        assertEquals(false, list1.getCompletedTaskList().get(0).isVisible());
        assertEquals(false, list1.getTaskList().get(0).isVisible());
        assertEquals(false, list1.isVisible);
    }

    @Test
    public void testSetListTittle() {
        assertEquals("Untitled List", list1.getListTitle());
        list1.setListTitle("List 1");
        assertEquals("List 1", list1.getListTitle());
    }

}
