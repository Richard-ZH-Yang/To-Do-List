package ui;

import exceptions.InvalidDateException;

import exceptions.ListFullException;
import persistence.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import model.*;

// create an instance of this class to start the to do list program
public class Console {
    private static final String JSON_STORE = "./data/toDoListProgram.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private ToDoListProgram toDoListProgram;
    private Scanner keyboard;
    private List<BasicList> defaultList;
    private List<BasicList> customizedList;

    // EFFECTS: run this method to start the Console Program
    public static void main(String[] args) {
        new Console();
    }

    // constructor
    // EFFECTS: construct a new Console, initialize toDoListProgram, set defaultList to toDoListProgram's defaultList,
    //          set customizedList to toDoListProgram's customizedList, set keyboard to read from user input,
    //          initialize jsonWriter and jsonReader to the file file directory: JSON_STORE. Run the toDoListProgram
    public Console() {
        toDoListProgram = new ToDoListProgram();
        keyboard = new Scanner(System.in);  // for user input
        defaultList = toDoListProgram.getDefaultList();
        customizedList = toDoListProgram.getCustomizedList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDoListProgram();
    }

    // MODIFIES: this
    // EFFECTS: ask user if they want to load first, then start the toDoListProgram. User can choose to end the program,
    //          load, save, and operate in defaultList or customizedList. It will catch any expected exception.
    //          It will keep going until user choose to end the program
    private void runToDoListProgram() {
        loadFirst();
        do {
            displayHomeScreen();
            try {
                int whichModule = Integer.parseInt(keyboard.nextLine());
                toDoListProgram.isValid(-3,1,whichModule);
                if (whichModule == -3) {
                    loadToDoListProgram();
                } else if (whichModule == -2) {
                    saveToDoListProgram();
                } else if (whichModule == -1) {
                    toDoListProgram.setEndProgram(true);
                    saveBeforeQuit();
                } else if (whichModule == 0) {
                    operationInDefaultListModule();
                } else if (whichModule == 1) {
                    operationInCustomizedListModule();
                }
            } catch (RuntimeException | ListFullException | InvalidDateException exception) {
                System.out.println(exception.getMessage() + "\nPlease try again");
            }
        } while (!toDoListProgram.isEndProgram());
    }

    // EFFECTS: ask user if they want to save before leaving, if yes, save the program
    public void saveBeforeQuit() {
        System.out.println("Would you like to save before leaving? (0 = don't save, 1 = save)");
        int isSaving = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0,1,isSaving);
        if (isSaving == 1) {
            saveToDoListProgram();
        }

        System.out.println("Thank you for using this program");
    }

    // EFFECTS: ask user if they want to load before starting, if yes, load the file
    public void loadFirst() {
        System.out.println("Would you like to load before using this program? (0 = don't load, 1 = load)");
        int isLoading = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0,1,isLoading);
        if (isLoading == 1) {
            loadToDoListProgram();
        }
    }

    // EFFECTS: save the toDoListProgram to JSON_STORE directory
    private void saveToDoListProgram() {
        try {
            jsonWriter.open();
            jsonWriter.write(toDoListProgram);
            jsonWriter.close();
            System.out.println("Saved successfully to " + JSON_STORE);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Unable to write to file: " + JSON_STORE + "\nFile is not found!");
        }
    }

    // MODIFIES: this
    // EFFECTS: load the toDoListProgram from JSON_STORE directory
    private void loadToDoListProgram() {
        try {
            toDoListProgram = jsonReader.read();
            defaultList = toDoListProgram.getDefaultList();
            customizedList = toDoListProgram.getCustomizedList();
            System.out.println("Load successfully from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (ListFullException listFullException) {
            System.out.println("Failed! List is full");
        } catch (InvalidDateException invalidDateException) {
            System.out.println("Failed! Date in the file is invalid");
        }

    }


    // EFFECTS: Display the home screen, including every task list titles from default module and customized module
    public void displayHomeScreen() {
        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("○ Lists inside Default List:");
        for (BasicList list: defaultList) {
            System.out.println(list.getListTitle());
        }
        System.out.println("\n○ Lists inside Customized List:");
        displayAllCustomizedListIndexNTitle();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Which module of list do you want to get access? (-3 = load,  -2 = Save,  "
                + "-1 = End the program, 0 = Default List, 1 = Customized List)");

    }

    // EFFECTS: if the customized module is empty, display empty. Other wise display all the task list title in
    //          customized module with index
    public void displayAllCustomizedListIndexNTitle() {
        if (customizedList.size() == 0) {
            System.out.println("-- empty --");
        }
        for (int i = 0; i < customizedList.size(); i++) {
            System.out.println(i + ". " + customizedList.get(i).getListTitle());
        }
    }

    // EFFECTS: display and let user choose the unique operations that can only preform in customized module
    public void operationInCustomizedListModule() throws ListFullException, InvalidDateException {
        System.out.println("0 - Add a customized list, 1 - delete a customized list, 2 - rename a customized list"
                + ", 3 - edit the list");
        int whichCustomizedListOperation = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0, 3, whichCustomizedListOperation);
        if (whichCustomizedListOperation == 0) {
            addCustomizedList();
        } else if (whichCustomizedListOperation == 1) {
            deleteCustomizedList();
        } else if (whichCustomizedListOperation == 2) {
            renameCustomizedList();
        } else if (whichCustomizedListOperation == 3) {
            System.out.println("Which list do you want to operate:   ");
            displayAllCustomizedListIndexNTitle();
            int whichList = Integer.parseInt(keyboard.nextLine());
            toDoListProgram.isValid(0, customizedList.size() - 1, whichList);
            BasicList targetedList = customizedList.get(whichList);
            operationInList(targetedList);
        }
    }

    // MODIFIES: this
    // EFFECTS: add a new customized task list to customized module. And let user decide the task list title
    public void addCustomizedList() {
        System.out.println("What's the name of the new Customized List?");
        customizedList.add(new BasicList(keyboard.nextLine()));
    }

    // MODIFIES: this
    // EFFECTS: delete a customized task list in the customized module. If customized module is not empty,
    //          let user decide which task list to delete, then delete it.
    public void deleteCustomizedList() {
        if (customizedList.size() == 0) {
            System.out.println("Sorry, the list is empty");
        } else {
            displayAllCustomizedListIndexNTitle();
            System.out.println("What's the index of the list to delete? (starts from 0)");
            int indexToDelete = Integer.parseInt(keyboard.nextLine());
            toDoListProgram.isValid(0, customizedList.size() - 1, indexToDelete);
            customizedList.remove(indexToDelete);
        }
    }

    // MODIFIES: this
    // EFFECTS: rename a customized list in the customized module. If the customized module is not empty,
    //          let user rename the task list based on the index and new name.
    public void renameCustomizedList() {
        if (customizedList.size() == 0) {
            System.out.println("Sorry, the list is empty");
        } else {
            displayAllCustomizedListIndexNTitle();
            System.out.println("What's the index of the list to rename? (starts from 0)");
            int indexToRename = Integer.parseInt(keyboard.nextLine());
            toDoListProgram.isValid(0, customizedList.size() - 1, indexToRename);
            System.out.println("What's the new list name?");
            String listName = keyboard.nextLine();
            customizedList.get(indexToRename).setListTitle(listName);
        }
    }

    // EFFECTS:: display all the task list in default module first, then
    //          let user choose which task list inside the default module that is need to operate
    public void operationInDefaultListModule() throws ListFullException, InvalidDateException {
        System.out.println("Which list do you want to operate:   ");
        for (int i = 0; i < defaultList.size(); i++) { // display the title for each list
            System.out.println(i + " - " + defaultList.get(i).getListTitle() + ",       ");
        }
        int whichList = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0, defaultList.size() - 1, whichList);

        BasicList targetedList = defaultList.get(whichList);
        operationInList(targetedList);
    }

    // EFFECTS: used by both default module and customized module. Able to perform basic operations in a task list.
    public void operationInList(BasicList targetedList) throws InvalidDateException, ListFullException {
        System.out.println("which operation do you want to do in this List? \n"
                + "(0 - add task, 1 - delete task, 2 - edit task, 3 - sort the list, "
                + "4 - complete a task, 5 - undo complete a task, 6 - display all the task information)");
        int totalTaskNumStarts0 = displayEachTasks(targetedList);
        int whichOperation = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0, 6, whichOperation); // NOW IT HAS THREE OPERATIONS, If change the switch below,
        // need to change this and String literal above

        if (whichOperation == 0) { // add task
            addTaskOperation(targetedList);
        } else if (whichOperation == 1) { // delete task
            removeTaskOperation(targetedList, totalTaskNumStarts0);
        } else if (whichOperation == 2) { // edit the specific task
            editTaskOperation(targetedList, totalTaskNumStarts0);
        } else if (whichOperation == 3) { // sort list
            sortListOperation(targetedList);
        } else if (whichOperation == 4) { // complete task
            finishTask(targetedList);
        } else if (whichOperation == 5) { // undo complete a task
            undoFinishTask(targetedList);
        } else if (whichOperation == 6) {
            displayAllTheTaskInformation(targetedList);
        } else {
            System.out.println("ERROR! check operationInDefaultList");
        }
    }

    // EFFECTS: display every task's title in a task list, differed by whether the task is complete.
    //          And then return how (have many tasks in complete and incomplete combined - 1 )
    public int displayEachTasks(BasicList list) {
        if (list.getTaskList().size() == 0 && list.getCompletedTaskList().size() == 0) {
            System.out.println("---------empty list ------------");
            return 0;
        } else {
            int index = 0;
            for (int i = 0; i < list.getTaskList().size(); i++) {
                System.out.println(index + ". " + list.getTaskList().get(i).getTitle());
                index++;
            }

            for (int i = 0; i < list.getCompletedTaskList().size(); i++) {
                System.out.println(index + ". " + list.getCompletedTaskList().get(i).getTitle());
                index++;
            }
            return index;
        }
    }

    // EFFECTS: if the task list is not empty,
    //          display every task's details with task's index, differed by whether the task is complete.
    public void displayAllTheTaskInformation(BasicList targetedList) {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("\n\n------------------Uncompleted Tasks------------------");
            if (targetedList.getTaskList().size() != 0) {
                for (int i = 0; i < targetedList.getTaskList().size(); i++) {
                    System.out.println("\n" + i);
                    displayAllInformation(targetedList.getTaskList().get(i));
                }
            } else {
                System.out.println("----------------empty uncompleted task list----------------");
            }

            System.out.println("\n------------------Completed Tasks------------------");

            if (targetedList.getCompletedTaskList().size() != 0) {
                for (int i = 0; i < targetedList.getCompletedTaskList().size(); i++) {
                    System.out.println("\n" + i);
                    displayAllInformation(targetedList.getCompletedTaskList().get(i));
                }
            } else {
                System.out.println("----------------empty completed task list----------------");
            }
        }
    }

    // EFFECTS: display all the information about this targetedTask. Including steps and steps status
    public void displayAllInformation(Task targetedTask) {
        System.out.println("Task Title: " + targetedTask.getTitle() + "\nCompleted?    "
                + targetedTask.isComplete() + "\nImportant?   " + targetedTask.isImportant()
                + "\nVisible?   " + targetedTask.isVisible() + "\nOverdue?   " + targetedTask.isOverDue());
        if (targetedTask.getStep().size() == 0) {
            System.out.println("-- no steps --");
        } else {
            for (int i = 0; i < targetedTask.getStep().size(); i++) {
                System.out.println(i + ". " + targetedTask.getStep().get(i) + "       Finished?  "
                        + targetedTask.getIsStepComplete().get(i));
            }
        }
        System.out.println("Due Date: " + targetedTask.getDueDay()
                + "\nNotes: " + targetedTask.getNote() + "\n Created date: " + targetedTask.getCreatedDate());
    }

    // MODIFIES: this
    // EFFECTS: add a new Task into the targetedList. let user decide the title for the new task
    public void addTaskOperation(BasicList targetedList) throws InvalidDateException, ListFullException {
        System.out.println("What's the new task title?");
        String taskTitle = keyboard.nextLine();
        targetedList.addTask(new Task(taskTitle));
    }

    // MODIFIES: this
    // EFFECTS: if the targetedList is not empty, remove the task from the targetedList based on the new index that
    //          includes all the completed and in completed tasks
    public void removeTaskOperation(BasicList targetedList, int totalTaskNumStarts0) {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to delete?");
            int taskIndexToDelete = Integer.parseInt(keyboard.nextLine());
            toDoListProgram.isValid(0, totalTaskNumStarts0, taskIndexToDelete);

            if (taskIndexToDelete >= targetedList.getTaskList().size()) {
                targetedList.removeTask(1, taskIndexToDelete - targetedList.getTaskList().size());
            } else {
                targetedList.removeTask(0, taskIndexToDelete);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if the targetedList is not empty, set a task's isComplete to true from the targetedList based on
    //          the new index that includes all the completed and in completed tasks
    public void finishTask(BasicList targetedList) {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to complete?");
            int taskIndexToComplete = Integer.parseInt(keyboard.nextLine());
            toDoListProgram.isValid(0, targetedList.getTaskList().size() - 1, taskIndexToComplete);

            targetedList.finishTask(taskIndexToComplete);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the targetedList is not empty, set a task's isComplete to false from the targetedList based on
    //          the new index that includes all the completed and in completed tasks
    public void undoFinishTask(BasicList targetedList) {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to undo finish?");
            int taskIndexToIncomplete = Integer.parseInt(keyboard.nextLine()) - targetedList.getTaskList().size();
            toDoListProgram.isValid(0, targetedList.getCompletedTaskList().size() - 1, taskIndexToIncomplete);

            targetedList.undoFinishTask(taskIndexToIncomplete);
        }
    }

    // EFFECTS: let user choose which way to sort the targetedList
    public void sortListOperation(BasicList targetedList) {
        System.out.println("How do you want your list sorted? (0 - sortListAlphabeticallyAscending, "
                + "1 - sortListAlphabeticallyDescending, 2 - sortListDueDateAscending, 3 - sortListDueDateDescending, "
                + "4 - sortListImportance)");
        int sortOption = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0, 4, sortOption);
        switch (sortOption) {
            case 0:
                targetedList.sortListAlphabeticallyAscending();
                break;
            case 1:
                targetedList.sortListAlphabeticallyDescending();
                break;
            case 2:
                targetedList.sortListDueDateAscending();
                break;
            case 3:
                targetedList.sortListDueDateDescending();
                break;
            case 4:
                targetedList.sortListImportance();
                break;
            default:
        }
    }

    // EFFECTS: let user choose which task from targetedList that needs to bew edited.
    public void editTaskOperation(BasicList targetedList, int totalTaskNumStarts0) throws InvalidDateException {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to edit?");
            int taskIndexToEdit = Integer.parseInt(keyboard.nextLine());
            toDoListProgram.isValid(0, totalTaskNumStarts0, taskIndexToEdit);

            Task targetedTask;
            if (taskIndexToEdit >= targetedList.getTaskList().size()) {
                // completed Task List
                int completedListIndex = taskIndexToEdit - targetedList.getTaskList().size();
                targetedTask = targetedList.getCompletedTaskList().get(completedListIndex);
            } else {
                targetedTask = targetedList.getTaskList().get(taskIndexToEdit);
            }
            editTaskOptions(targetedTask);
        }

    }

    // EFFECTS: let user choose which operation to perform on targetedTask
    public void editTaskOptions(Task targetedTask) throws InvalidDateException {
        System.out.println("0 - change task title, 1 - add step, 2 - delete step, 3 - complete a step, "
                + "4 - change due date, 5 - change importance, 6 - change note");
        int whichEditTaskOption = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0, 6, whichEditTaskOption);
        if (whichEditTaskOption == 0) {
            System.out.println("What's the new title?");
            targetedTask.setTitle(keyboard.nextLine());
        } else if (whichEditTaskOption == 1) {
            System.out.println("What's the step");
            targetedTask.addStep(keyboard.nextLine());
        } else if (whichEditTaskOption == 2) {             // TODO this needs improvement, situation where step is empty
            deleteStep(targetedTask);
        } else if (whichEditTaskOption == 3) {
            completeStep(targetedTask);
        } else if (whichEditTaskOption == 4) {
            System.out.println("What's the new date?(in the format of \"year-mn-dy\" please, total is 10 digits");
            targetedTask.setDueDay(keyboard.nextLine());
        } else if (whichEditTaskOption == 5) {
            changeImportance(targetedTask);
        } else if (whichEditTaskOption == 6) {
            System.out.println("What's the new note?");
            targetedTask.setNote(keyboard.nextLine());
        }
    }

    // MODIFIES: targetedTask
    // EFFECTS: mark the step to complete in the targetedTask
    public void completeStep(Task targetedTask) {
        displayAllSteps(targetedTask);
        System.out.println("Which step to complete? (index start with 0)");
        int stepIndexComplete = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0, targetedTask.getStep().size() - 1, stepIndexComplete);
        targetedTask.completeStep(stepIndexComplete);
    }

    // MODIFIES: targetedTask
    // EFFECTS: delete the step from targetedTask
    public void deleteStep(Task targetedTask) {
        displayAllSteps(targetedTask);
        System.out.println("Which step to delete? (index start with 0)");
        int stepIndex = Integer.parseInt(keyboard.nextLine());
        toDoListProgram.isValid(0, targetedTask.getStep().size() - 1, stepIndex);
        targetedTask.deleteStep(stepIndex);
    }

    // REQUIRES: user only can input true or false
    // MODIFIES: targetedTask
    // EFFECTS: change the importance of targetedTask
    public void changeImportance(Task targetedTask) {
        System.out.println("Please enter true or false");
        targetedTask.setImportant(Boolean.parseBoolean(keyboard.nextLine()));
    }

    // EFFECTS: display all the steps and whether or not it is finished from the task parameter
    public void displayAllSteps(Task task) {
        for (int i = 0; i < task.getStep().size(); i++) {
            System.out.println(i + ". " + task.getStep().get(i) + " -- " + task.getIsStepComplete().get(i));
        }
    }

}
