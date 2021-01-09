package persistence;

import model.BasicList;
import model.Task;
import model.ToDoListProgram;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// checks if every elements are the same basicList wise
public class JsonTest {
    // EFFECTS: checks if every elements are equal to the element in task object
    private void checkTask(Task checkTask, Task task) {

        assertEquals(checkTask.getTitle(), task.getTitle());
        checkStepAndStatus(checkTask, task);
        assertEquals(checkTask.getTodayDate(), task.getTodayDate());
        assertEquals(checkTask.getDueDay(), task.getDueDay());
        assertEquals(Task.todayDate, task.getCreatedDate());
        assertEquals(checkTask.getNote(), task.getNote());
        assertEquals(checkTask.isImportant(), task.isImportant());
        assertEquals(checkTask.isComplete(), task.isComplete());
        assertEquals(checkTask.isVisible(), task.isVisible());
        assertEquals(checkTask.isOverDue(), task.isOverDue());
    }

    // EFFECTS: compare every element in step with task.getStep(). Assert they are equal
    private void checkStepAndStatus(Task checkTask, Task task) {
        assertEquals(checkTask.getStep().size(), task.getStep().size());
        assertEquals(checkTask.getIsStepComplete().size(), task.getIsStepComplete().size());

        assertEquals(checkTask.getStep().size(), checkTask.getIsStepComplete().size()); // expect to see step and status
                                                                                        // same size
        for (int i = 0; i < checkTask.getStep().size(); i++) {
            assertEquals(checkTask.getStep().get(i), task.getStep().get(i));
            assertEquals(checkTask.getIsStepComplete().get(i), task.getIsStepComplete().get(i));
        }
    }

    protected void checkToDoListProgram(ToDoListProgram checkToDo, ToDoListProgram toDoListProgram) {

        assertEquals(checkToDo.getBasicListsSize(), toDoListProgram.getBasicListsSize());


        for (int i = 0; i < checkToDo.getBasicListsSize(); i++) {
            checkBasicList(checkToDo.getSpecificBasicList(i), toDoListProgram.getSpecificBasicList(i));
        }

    }


    // EFFECTS: checks if every elements are equal to the element in basicList object
    private void checkBasicList(BasicList checkBasicList, BasicList basicList) {
        assertEquals(checkBasicList.getListTitle(), basicList.getListTitle());
        assertEquals(checkBasicList.isVisible(), basicList.isVisible());

        assertEquals(checkBasicList.getTaskList().size(), basicList.getTaskList().size());
        assertEquals(checkBasicList.getCompletedTaskList().size(), basicList.getCompletedTaskList().size());

        for (int i = 0; i < checkBasicList.getTaskList().size(); i++) {
            checkTask(checkBasicList.getTaskList().get(i), basicList.getTaskList().get(i));
        }

        for (int i = 0; i < checkBasicList.getCompletedTaskList().size(); i++) {
            checkTask(checkBasicList.getCompletedTaskList().get(i), basicList.getCompletedTaskList().get(i));
        }
    }


}
