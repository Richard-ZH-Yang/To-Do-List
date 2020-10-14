package model;

import exceptions.InvalidIndexException;
import exceptions.ListFullException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BasicList {
    public static final int MAX_LENGTH = 1000;
    protected List<Task> taskList;
    protected List<Task> completedTaskList;
    protected String listTitle;
    protected boolean isVisible;

    public BasicList() {
        taskList = new ArrayList<>();
        completedTaskList = new ArrayList<>();
        isVisible = true;
        listTitle = "Untitled List";
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
    // EFFECTS: throw InvalidIndexException if index is not valid. Otherwise take the parameter int as an index from
    //         taskList, set the task status to complete. Add that task to completedTaskList and remove it from taskList
    public void finishTask(int index) throws InvalidIndexException {
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidIndexException("index for uncompleted list is invalid, out of the bound. "
                    + "Index starts with 0");
        } else {
            taskList.get(index).setComplete(true);
            Task myTask = taskList.get(index);
            completedTaskList.add(myTask);
            taskList.remove(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: throw InvalidIndexException if index is not valid. Otherwise take the parameter int as an index from
    //          completedTaskList, set the task status to incomplete. Add that task to taskList and remove it from
    //          completedTaskList
    public void undoFinishTask(int index) throws InvalidIndexException {
        if (index < 0 || index >= completedTaskList.size()) {
            throw new InvalidIndexException("index for uncompleted list is invalid, out of the bound. "
                    + "Index starts with 0");
        } else {
            completedTaskList.get(index).setComplete(false);
            Task myTask = completedTaskList.get(index);
            taskList.add(myTask);
            completedTaskList.remove(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: Firstly it will check if the index is 0 or 1, if not, it will throw InvalidIndexException. And then it
    //          will throw another InvalidIndexException if index for that list is out of bound
    //          If everything is valid, it will remove the task from that list
    public void removeTask(int whichList, int index) throws InvalidIndexException {
        switch (whichList) {
            case 0:
                if (index < 0 || index >= taskList.size()) {
                    throw new InvalidIndexException("index for uncompleted list is invalid, out of the bound. "
                            + "Index starts with 0");
                } else {
                    taskList.remove(index);
                }
                break;
            case 1:
                if (index < 0 || index >= completedTaskList.size()) {
                    throw new InvalidIndexException("index for completedTaskList is invalid, out of the bound. "
                            + "Index starts with 0");
                } else {
                    completedTaskList.remove(index);
                }
                break;
            default:
                throw new InvalidIndexException("whichList is invalid, it has to be 0 or 1. 0 for uncompleted tasks "
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

//    public void changeTheme() {
//
//    }
//
//    public void changeTheme(int r, int g, int b) {
//
//    }

//    public void saveInTextFile() throws IOException {
//        String fileNameLocation = "../data/savedToDoList/" + listTitle + "_unfinishedTasks-" + taskList.size();
//        PrintWriter outputFile = new PrintWriter(fileNameLocation);
//        outputFile.println(listTitle);
//        outputFile.println("Unfinished tasks:");
//        for (Task task : taskList) {
//            outputFile.println(task.getTitle());
//        }
//        outputFile.println("Finished tasks:");
//        for (Task task : completedTaskList) {
//            outputFile.println(task.getTitle());
//        }
//        outputFile.close();
//    }

//    // MODIFIES: this
//    // EFFECTS:
//    public void toggleCompleteTaskVisibility() {
//        isVisible = !isVisible;
//        for (Task t : taskList) {
//            if (isVisible) {
//                t.setVisible(true);
//            } else {
//                t.setVisible(!t.isComplete());
//            }
//        }
//    }


    // MODIFIES: this
    // EFFECTS: if parameter visible is true, set the filed to visible and set and both taskList and completedTaskList
    //          corresponding boolean value.
    public void setVisible(boolean visible) {
        isVisible = visible;
        for (Task task : taskList) {
            task.setVisible(isVisible);
        }
        for (Task task : completedTaskList) {
            task.setVisible(isVisible);
        }
    }

    // MODIFIES: this
    // EFFECTS: set the title
    protected void setListTitle(String listTitle) {
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

}
