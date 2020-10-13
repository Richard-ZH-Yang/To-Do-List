package model;

import exceptions.ListFullException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        isVisible = true;
        listTitle = "ATitle";
    }

    public void addTask(Task task) throws ListFullException {
        if (taskList.size() >= MAX_LENGTH) {
            throw new ListFullException();
        } else {
            taskList.add(task);
        }
    }

    public void removeTask(int index) {
        taskList.remove(index);
    }


    public void sortListAlphabetically() {
        taskList.sort(Comparator.comparing(Task::getTitle));
    }

    public void sortListDueDate() {
        taskList.sort(Comparator.comparing(Task::getDueDay));
    }

    public void sortListCreationDate() {
        taskList.sort(Comparator.comparing(Task::getCreatedDate).reversed());
    }

    public void sortListImportance() {
        Task temporary;
        for (Task task : taskList) {
            if (task.isImportant()) {
                temporary = task;
                for (Task task1 : taskList) {
                    if (!task1.isImportant()) {
                        task = task1;
                        task1 = temporary;
                    }
                }
            }
        }
    }

//    public void changeTheme() {
//
//    }
//
//    public void changeTheme(int r, int g, int b) {
//
//    }

    public void saveInTextFile() throws IOException {
        String fileNameLocation = "../data/savedToDoList/" + listTitle + "_unfinishedTasks-" + taskList.size();
        PrintWriter outputFile = new PrintWriter(fileNameLocation);
        outputFile.println(listTitle);
        outputFile.println("Unfinished tasks:");
        for (Task task : taskList) {
            outputFile.println(task.getTitle());
        }
        outputFile.println("Finished tasks:");
        for (Task task : completedTaskList) {
            outputFile.println(task.getTitle());
        }
        outputFile.close();
    }

    public void toggleCompleteTaskVisibility() {
        isVisible = !isVisible;
        for (Task t : taskList) {
            if (isVisible) {
                t.setVisible(true);
            } else {
                t.setVisible(!t.isComplete());
            }
        }
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
