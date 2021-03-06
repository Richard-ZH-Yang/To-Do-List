package model;

import exceptions.ListFullException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// Represents the list inside the ToDoListProgram. It contains different tasks and the information for that list
public class BasicList implements Writable, Iterable<Task> {
    public static final int MAX_LENGTH = 1000;  // the maximum length a List<Task> can have
    protected List<Task> taskList;  // uncompleted tasks
    protected List<Task> completedTaskList; // completed tasks
    protected String listTitle; // the tile of the task list
    protected boolean isVisible;    // decide whether the completed task can be seen

    // constructor
    // EFFECTS: construct a new BasicList, initialize two Task List, set isVisible to true, listTitle to "Untitled List"
    public BasicList() {
        taskList = new ArrayList<>();
        completedTaskList = new ArrayList<>();
        isVisible = true;
        listTitle = "Untitled List";
    }

    // constructor
    // EFFECTS: construct a new BasicList, set listTitle to parameter String,
    //          initialize two Task List, set isVisible to true, listTitle to "Untitled List"
    public BasicList(String listTitle) {
        this();
        setListTitle(listTitle);
    }

    // EFFECTS: convert the basicList to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskList", taskListToJson());
        json.put("completedTaskList", completedTaskListToJson());
        json.put("listTitle", listTitle);
        json.put("isVisible", isVisible);
        return json;
    }

    // EFFECTS: returns taskList as a JSON array
    private JSONArray taskListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : taskList) {
            jsonArray.put(t.toJson());
        }


        return jsonArray;
    }

    // EFFECTS: returns completedTaskList as a JSON array
    private JSONArray completedTaskListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : completedTaskList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


    // MODIFIES: this
    // EFFECTS: It will throw ListFullException if taskList is full. otherwise it will add the task to uncompleted list
    public void addTask(Task task) throws ListFullException {
        if (taskList.size() >= MAX_LENGTH) {
            throw new ListFullException("List is full, cannot add another task");
        } else if (task.isComplete()) {
            completedTaskList.add(task);
        } else {
            taskList.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: throw IndexOutOfBoundsException if index is not valid. Otherwise take the parameter int as an index from
    //         taskList, set the task status to complete. Add that task to completedTaskList and remove it from taskList
    public void finishTask(int index) {
        if (index < 0 || index >= taskList.size()) {
            throw new IndexOutOfBoundsException("index for uncompleted list is invalid, out of the bound. "
                    + "Index starts with 0");
        } else {
            taskList.get(index).setComplete(true);
            Task myTask = taskList.get(index);
            completedTaskList.add(myTask);
            taskList.remove(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: throw IndexOutOfBoundsException if index is not valid. Otherwise set the task status to complete.
    //           Add that task to completedTaskList and remove it from taskList
//    public void finishTask(Task task) {
//        for (int i = 0; i < taskList.size(); i++) {
//            if (taskList.get(i).equals(task)) {
//                finishTask(i);
//            }
//        }
//    }

    // MODIFIES: this
    // EFFECTS: throw IndexOutOfBoundsException if index is not valid. Otherwise take the parameter int as an index from
    //          completedTaskList, set the task status to incomplete. Add that task to taskList and remove it from
    //          completedTaskList
    public void undoFinishTask(int index) {
        if (index < 0 || index >= completedTaskList.size()) {
            throw new IndexOutOfBoundsException("index for uncompleted list is invalid, out of the bound. "
                    + "Index starts with 0");
        } else {
            completedTaskList.get(index).setComplete(false);
            Task myTask = completedTaskList.get(index);
            taskList.add(myTask);
            completedTaskList.remove(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: throw IndexOutOfBoundsException if index is not valid. Otherwise set the task status to incomplete.
    //          Add that task to taskList and remove it from completedTaskList
//    public void undoFinishTask(Task task) {
//        for (int i = 0; i < completedTaskList.size(); i++) {
//            if (completedTaskList.get(i).equals(task)) {
//                undoFinishTask(i);
//            }
//        }
//    }


    // MODIFIES: this
    // EFFECTS: Firstly it will check if the index is 0 or 1, if not, it will throw IndexOutOfBoundsException.
    //          And then it will throw another IndexOutOfBoundsException if index for that list is out of bound
    //          If everything is valid, it will remove the task from that list
    public void removeTask(int whichList, int index) {
        switch (whichList) {
            case 0:
                if (index < 0 || index >= taskList.size()) {
                    throw new IndexOutOfBoundsException("index for uncompleted list is invalid, out of the bound. "
                            + "Index starts with 0");
                } else {
                    taskList.remove(index);
                }
                break;
            case 1:
                if (index < 0 || index >= completedTaskList.size()) {
                    throw new IndexOutOfBoundsException("index for completedTaskList is invalid, out of the bound. "
                            + "Index starts with 0");
                } else {
                    completedTaskList.remove(index);
                }
                break;
            default:
                throw new IndexOutOfBoundsException("whichList is invalid, it has to be 0 or 1."
                        + " 0 for uncompleted tasks "
                        + "and 1 for completed tasks.");
        }

    }

    // MODIFIES: this
    // EFFECTS: sort both the taskList and completedTaskList Alphabetically in ascending order
    public void sortListAlphabeticallyAscending() {
        taskList.sort(Comparator.comparing(Task::getTitle));
        completedTaskList.sort(Comparator.comparing(Task::getTitle));
    }

    // MODIFIES: this
    // EFFECTS: sort both the taskList and completedTaskList Alphabetically in descending order
    public void sortListAlphabeticallyDescending() {
        taskList.sort(Comparator.comparing(Task::getTitle).reversed());
        completedTaskList.sort(Comparator.comparing(Task::getTitle).reversed());
    }

    // MODIFIES: this
    // EFFECTS: sort both the taskList and completedTaskList based on due date in ascending order
    public void sortListDueDateAscending() {
        taskList.sort(Comparator.comparing(Task::getDueDay));
        completedTaskList.sort(Comparator.comparing(Task::getDueDay));
    }

    // MODIFIES: this
    // EFFECTS: sort both the taskList and completedTaskList based on due date in descending order
    public void sortListDueDateDescending() {
        taskList.sort(Comparator.comparing(Task::getDueDay).reversed());
        completedTaskList.sort(Comparator.comparing(Task::getDueDay).reversed());
    }

    // MODIFIES: this
    // EFFECTS: sort both the taskList and completedTaskList based on the importance. The first part of list is
    //          important tasks and the rest are not important tasks
    public void sortListImportance() {
        sortSingleListImportance(taskList);
        sortSingleListImportance(completedTaskList);
    }

    // MODIFIES: parameter
    // EFFECTS: sort the parameter list in the order that important tasks is first
    public static void sortSingleListImportance(List<Task> taskList) {
        List<Task> newList = new ArrayList<>();
        List<Task> list2 = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).isImportant()) {
                newList.add(taskList.get(i));
            } else {
                list2.add(taskList.get(i));
            }
            taskList.remove(i);
        }
        taskList.addAll(newList);
        taskList.addAll(list2);
    }


    // MODIFIES: this
    // EFFECTS: if parameter visible is true, set the filed to visible and set and both taskList and completedTaskList
    //          corresponding boolean value.
    public void setVisible(boolean visible) {
        isVisible = visible;
        for (Task task : this) {
            task.setVisible(isVisible);
        }
    }

    // MODIFIES: this
    // EFFECTS: set the title
    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }


    // simple getters methods, haven't deep copied

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<Task> getCompletedTaskList() {
        return completedTaskList;
    }

    public String getListTitle() {
        return listTitle;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public Iterator<Task> iterator() {
        return new TaskIterator();
    }

    private class TaskIterator implements Iterator<Task> {
        private Iterator<Task> inCompleteTaskIterator = taskList.iterator();
        private Iterator<Task> completeTaskIterator = completedTaskList.iterator();

        @Override
        public boolean hasNext() {
            return inCompleteTaskIterator.hasNext() || completeTaskIterator.hasNext();
        }

        @Override
        public Task next() {
            return inCompleteTaskIterator.hasNext() ? inCompleteTaskIterator.next() : completeTaskIterator.next();
        }
    }


}
