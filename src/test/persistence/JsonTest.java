package persistence;

import model.BasicList;
import model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// checks if every elements are the same basicList wise
public class JsonTest {
    // EFFECTS: checks if every elements are equal to the element in task object
    protected void checkTask(String title, List<String> step, List<Boolean> isStepComplete, String todayDate,
                             String dueDay, String createdDate, String note, boolean isImportant,
                             boolean isComplete, boolean isVisible, boolean isOverDue, Task task) {

        assertEquals(title, task.getTitle());
        checkStep(step, task);
        checkIsStepComplete(isStepComplete, task);
        assertEquals(isStepComplete, task.getIsStepComplete());
        assertEquals(todayDate, task.getTodayDate());
        assertEquals(dueDay, task.getDueDay());
        assertEquals(createdDate, task.getCreatedDate());
        assertEquals(note, task.getNote());
        assertEquals(isImportant, task.isImportant());
        assertEquals(isComplete, task.isComplete());
        assertEquals(isVisible, task.isVisible());
        assertEquals(isOverDue, task.isOverDue());

    }

    // EFFECTS: compare every element in step with task.getStep(). Assert they are equal
    private void checkStep(List<String> step, Task task) {
        assertEquals(step.size(), task.getStep().size());

        for (int i = 0; i < step.size(); i++) {
            assertEquals(step.get(i), task.getStep().get(i));
        }
    }

    // EFFECTS: compare every element in stepStatus with task.getIsStepComplete(). Assert they are equal
    private void checkIsStepComplete(List<Boolean> stepStatus, Task task) {
        assertEquals(stepStatus.size(), task.getStep().size());

        for (int i = 0; i < stepStatus.size(); i++) {
            assertEquals(stepStatus.get(i), task.getIsStepComplete().get(i));
        }
    }

    // EFFECTS: checks if every elements are equal to the element in basicList object
    protected void checkBasicList(int max_length, List<Task> taskList, List<Task> completedTaskList,
                                  String listTitle, boolean isVisible, BasicList basicList) {
        assertEquals(max_length, BasicList.MAX_LENGTH);

    }

    private void checkTaskList(List<Task> taskList, BasicList basicList) {
        assertEquals(taskList, basicList.getTaskList());
        assertEquals(taskList.size(), basicList.getTaskList().size());
        for (int i = 0; i < taskList.size(); i++) {
//            checkTask(taskList.get(i).getTitle(), taskList.get(i).getTitle(), taskList.get(i).getTitle(), taskList.get(i).getTitle(), basicList.getTaskList().get(i));
            assertEquals(taskList, basicList.getTaskList());
        }
    }
}
