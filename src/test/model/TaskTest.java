package model;

import exceptions.InvalidDateException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    String todayDate;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    public void setup() throws InvalidDateException {
        setTodayDate();
        task1 = new Task();
        task2 = new Task();
        task3 = new Task("Buy coffee");

        task1.setTitle("This is task 1");
        task1.setNote("Note for task 1");
        task1.setImportant(false);
        task1.setComplete(false);
        task1.setVisible(true);

        task2.setTitle("This is task 2");
        task2.setNote("Note for task 2");
        task2.setImportant(true);
        task2.setComplete(false);
        task2.setVisible(false);
        task2.addStep("Step 1");
        task2.addStep("Step 2");
        task2.addStep("Step 3");

        task3.setTitle("This is task 3");
        task3.setNote("Note for task 3");
        task3.setImportant(false);
        task3.setComplete(true);
        task3.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: set today's date to the date in Vancouver timezone with String format like "2020-10-13"
    public void setTodayDate() {
        ZoneId zonedId = ZoneId.of("America/Vancouver");
        LocalDate today = LocalDate.now(zonedId);
        todayDate = today.toString().substring(0, 10);
    }

    @Test
    public void testSetDueDateWithinRange() {
        int dueDateTenYearsLaterNum = Task.decodeDate(todayDate) + 100000;
        String dueDateTenYearsLater = Task.parseDate(dueDateTenYearsLaterNum);
        try {
            task1.setDueDay(dueDateTenYearsLater);
        } catch (InvalidDateException invalidDateException) {
            fail("This should have not thrown an exception.");
        }
        assertEquals(dueDateTenYearsLater, task1.getDueDay());
        assertEquals(false, task1.isOverDue());

        int dueDateTenYearsEarlierNum = Task.decodeDate(todayDate) - 100000;
        String dueDateTenYearsEarlier = Task.parseDate(dueDateTenYearsEarlierNum);
        try {
            task1.setDueDay(dueDateTenYearsEarlier);
        } catch (InvalidDateException invalidDateException) {
            fail("This should have not thrown an exception.");
        }
        assertEquals(dueDateTenYearsEarlier, task1.getDueDay());
        assertEquals(true, task1.isOverDue());

        try {
            task1.setDueDay("NA");
        } catch (InvalidDateException invalidDateException) {
            fail("This should have not thrown an exception.");
        }
        assertEquals("NA", task1.getDueDay());
        assertEquals(false, task1.isOverDue());
    }

    @Test
    public void testSetDueDateOutsideRange() {
        int dueDateTenNOneLaterNum = Task.decodeDate(todayDate) + 100001;
        String dueDateTenNOneLater = Task.parseDate(dueDateTenNOneLaterNum);
        try {
            task2.setDueDay(dueDateTenNOneLater);
            fail("This should have thrown an exception");
        } catch (InvalidDateException invalidDateException) {
            // pass
        }
        assertEquals("NA", task2.getDueDay());
        assertEquals(false, task2.isOverDue());


        int dueDateTenNOneEarlierNum = Task.decodeDate(todayDate) - 100001;
        String dueDateTenNOneEarlier = Task.parseDate(dueDateTenNOneEarlierNum);
        try {
            task2.setDueDay(dueDateTenNOneEarlier);
            fail("This should have thrown an exception");
        } catch (InvalidDateException invalidDateException) {
            // pass
        }
        assertEquals("NA", task2.getDueDay());
        assertEquals(false, task2.isOverDue());

    }

    @Test
    public void testToggleImportance() {
        assertEquals(true,task2.isImportant());
        task2.toggleImportance();
        assertEquals(false, task2.isImportant());
        task2.toggleImportance();
        assertEquals(true,task2.isImportant());
    }

    @Test
    public void testSetTodayDate() {
        Task.setTodayDate();
        assertEquals(task1.getTodayDate(), task2.getTodayDate());
        assertEquals(task1.getTodayDate(), task3.getTodayDate());
        assertEquals(10, task1.getTodayDate().length());

        assertEquals(todayDate, task3.getTodayDate());
    }

    @Test
    public void testGetters() {
        assertEquals("This is task 1", task1.getTitle());
        assertEquals(0, task1.getStep().size());
        assertEquals(0, task1.getIsStepComplete().size());
        assertEquals(todayDate, task1.getCreatedDate());
        assertEquals("NA", task1.getDueDay());
        assertEquals("Note for task 1", task1.getNote());
        assertEquals(todayDate, task1.getTodayDate());
        assertTrue(task1.isVisible());
        assertFalse(task1.isComplete());
        assertFalse(task1.isImportant());
        assertFalse(task1.isOverDue());
    }

    @Test
    public void testAddStep() {
        assertEquals(0, task1.getStep().size());
        task1.addStep("Step 1");
        assertEquals(1, task1.getStep().size());
        assertEquals(false, task1.getIsStepComplete().get(0));
        assertEquals("Step 1", task1.getStep().get(0));
    }

    @Test
    public void testDeleteStep() {
        assertEquals(3, task2.getStep().size());
        try {
            task2.deleteStep(1);
        } catch (IndexOutOfBoundsException IndexOutOfBoundsException) {
            fail("This should have not thrown an exception.");
        }
        assertEquals(2, task2.getStep().size());
        assertEquals(2, task2.getIsStepComplete().size());
        assertEquals("Step 3", task2.getStep().get(1));
        assertEquals(false, task2.getIsStepComplete().get(1));
        assertEquals("Step 1", task2.getStep().get(0));
        assertEquals(false, task2.getIsStepComplete().get(0));

        try {
            task2.deleteStep(100);
            fail("This should have throw an IndexOutOfBoundsException.");
        } catch (IndexOutOfBoundsException IndexOutOfBoundsException) {
            System.out.println(IndexOutOfBoundsException.getMessage());
        }


        try {
            task2.deleteStep(-4);
            fail("This should have throw an IndexOutOfBoundsException.");
        } catch (IndexOutOfBoundsException IndexOutOfBoundsException) {
            System.out.println(IndexOutOfBoundsException.getMessage());
        }

        try {
            task2.deleteStep(2);
            fail("This should have throw an IndexOutOfBoundsException.");
        } catch (IndexOutOfBoundsException IndexOutOfBoundsException) {
            System.out.println(IndexOutOfBoundsException.getMessage());
        }

        try {
            task2.deleteStep(1);
        } catch (IndexOutOfBoundsException exception) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(1, task2.getStep().size());
            assertEquals("Step 1", task2.getStep().get(0));
        }
    }

    @Test
    public void testCompleteStep() {
        try {
            task2.completeStep(100);
            fail("This should have throw an IndexOutOfBoundsException.");
        } catch (IndexOutOfBoundsException IndexOutOfBoundsException) {
            System.out.println(IndexOutOfBoundsException.getMessage());
        }


        try {
            task2.completeStep(-4);
            fail("This should have throw an IndexOutOfBoundsException.");
        } catch (IndexOutOfBoundsException IndexOutOfBoundsException) {
            System.out.println(IndexOutOfBoundsException.getMessage());
        }

        try {
            task2.completeStep(3);
            fail("This should have throw an IndexOutOfBoundsException.");
        } catch (IndexOutOfBoundsException IndexOutOfBoundsException) {
            System.out.println(IndexOutOfBoundsException.getMessage());
        }

        assertEquals(false, task2.getIsStepComplete().get(2));

        try {
            task2.completeStep(2);
        } catch (IndexOutOfBoundsException exception) {
            fail("This should have not thrown an exception.");
        } finally {
            assertEquals(3, task2.getStep().size());
            assertEquals(3, task2.getIsStepComplete().size());
            assertEquals("Step 3", task2.getStep().get(2));
            assertEquals(true, task2.getIsStepComplete().get(2));
            assertEquals(false,task2.getIsStepComplete().get(1));
        }

    }

    @Test
    public void testInCompleteStepWithException() {
        task2.completeStep(1);
        assertEquals(true, task2.getIsStepComplete().get(1));
        assertEquals(3, task2.getIsStepComplete().size());

        try {
            task2.inCompleteStep(-1);
            fail("Should throw exception");
        } catch (IndexOutOfBoundsException e) {
            //pass
        }

        try {
            task2.inCompleteStep(3);
            fail("Should throw exception");
        } catch (IndexOutOfBoundsException e) {
            //pass
        }
    }

    @Test
    public void testInCompleteStepNOException() {
        task2.completeStep(2);

        try {
            task2.inCompleteStep(2);
        } catch (IndexOutOfBoundsException e) {
            fail("This should not occur");
        }

        assertEquals(false, task2.getIsStepComplete().get(2));
    }

    @Test
    public void testDecodeDate() {
        String testDate1 = "2020-03-06";
        String testDate2 = "0020-00-02";
        assertEquals(20200306, Task.decodeDate(testDate1));
        assertEquals(200002, Task.decodeDate(testDate2));
    }

    @Test
    public void testParseDate() {
        assertEquals("2020-10-13", Task.parseDate(20201013));
        assertEquals("2234-10-13", Task.parseDate(22341013));
        assertEquals("2020-10-13", Task.parseDate(2020101365));
    }

    @Test
    public void testSetOverDue() {
        try {
            task1.setDueDay("2018-10-25");
        } catch (InvalidDateException invalidDateException) {
            fail("check today's date");
        }
        task1.setOverDue();
        assertEquals(true, task1.isOverDue());

        try {
            task1.setDueDay("2029-10-25");
        } catch (InvalidDateException invalidDateException) {
            fail("check today's date");
        }

        task1.setOverDue();
        assertEquals(false, task1.isOverDue());
    }

    @Test
    public void testSetCreatedDate() {
        task1.setCreatedDate();
        assertEquals(todayDate, task1.getCreatedDate());
    }
}
