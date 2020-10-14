package ui;

import exceptions.InvalidDateException;
import exceptions.InvalidIndexException;
import exceptions.ListFullException;
import model.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoList {
    List<BasicList> defaultList;
    List<BasicList> customizedList;
    boolean endProgram = false;
    Scanner keyboard = new Scanner(System.in);

    public ToDoList() {
        initialize();
        do {
            displayHomeScreen();
            try {
                int whichModule = Integer.parseInt(keyboard.nextLine());
                switch (whichModule) {
                    case 0:
                        endProgram = true;
                        System.out.println("Thank you for using this program");
                        break;
                    case 1:
                        operationInDefaultListModule();
                        break;
                    case 2:
                        break;
                    default:
                        throw new InvalidIndexException("Number out of bound");
                }
            } catch (RuntimeException | InvalidIndexException | ListFullException | InvalidDateException exception) {
                System.out.println(exception.getMessage());
                System.out.println("Please try again");
            }

        } while (!endProgram);

    }

    public void initialize() {
        defaultList = new ArrayList<>();
        customizedList = new ArrayList<>();

        BasicList important = new DefaultList("Important");
        BasicList tasks = new DefaultList("Tasks");
        BasicList myDay = new DefaultList("My Day");
        BasicList planned = new DefaultList("Planned");
        defaultList.add(myDay);
        defaultList.add(important);
        defaultList.add(planned);
        defaultList.add(tasks);

    }

    public void displayHomeScreen() {
        System.out.println("Which module of list do you want to get access? (0 - End the program,"
                + "1 - Default List, 2 - Customized List)");
        System.out.println("Lists inside Default List:");
        for (BasicList list: defaultList) {
            System.out.println(list.getListTitle());
        }
        System.out.println("Lists inside Customized List:");
        for (BasicList list: customizedList) {
            System.out.println(list.getListTitle());
        }

    }

    public int inputNumber(int lower, int upper, String tipsForInput) throws InvalidIndexException {
        System.out.println(tipsForInput);
        int number = Integer.parseInt(keyboard.nextLine());
        isValid(lower, upper, number);
        return number;
    }

    public void isValid(int lower, int upper, int value) throws InvalidIndexException {
        boolean isWorking = (value >= lower) && (value <= upper);
        if (!isWorking) {
            throw  new InvalidIndexException("Number out of bound");
        }
    }

    public void operationInDefaultListModule() throws InvalidIndexException, ListFullException, InvalidDateException {
        System.out.println("Which list do you want to operate:   ");
        for (int i = 0; i < defaultList.size(); i++) { // display the title for each list
            System.out.println(i + " - " + defaultList.get(i).getListTitle() + ",       ");
        }
        int whichList = Integer.parseInt(keyboard.nextLine());
        isValid(0, defaultList.size() - 1, whichList);
        operationInDefaultList(whichList);


    }

    public int displayEachTasks(BasicList list) {
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

    public void operationInDefaultList(int whichList) throws InvalidIndexException,
            InvalidDateException, ListFullException {
        BasicList targetedList = defaultList.get(whichList);
        System.out.println("which operation do you want to do in this List? \n"
                + "(0 - add task, 1 - delete task, 2 - edit task, 3 - sort the list, "
                + "4 - complete a task, 5 - undo complete a task)");
        int totalTaskNumStarts0 = displayEachTasks(targetedList);
        int whichOperation = Integer.parseInt(keyboard.nextLine());

        isValid(0, 5, whichOperation); // NOW IT HAS THREE OPERATIONS, If change the switch below,
                                                    // need to change this and String literal above

        switch (whichOperation) {
            case 0: // add task
                addTaskOperation(targetedList);
                break;
            case 1: // delete task
                removeTaskOperation(targetedList, totalTaskNumStarts0);
                break;
            case 2: // edit the specific task
                editTaskOperation(targetedList, totalTaskNumStarts0);
                break;
            case 3: // sort list
                sortListOperation(targetedList);
                break;
            case 4: // complete task
                finishTask(targetedList);
                break;
            case 5: // undo complete a task
                undoFinishTask(targetedList);
                break;
            default:
                System.out.println("ERROR! check operationInDefaultList");
        }

    }

    public void addTaskOperation(BasicList targetedList) throws InvalidDateException, ListFullException {
        System.out.println("What's the new task title?");
        String taskTitle = keyboard.nextLine();
        targetedList.addTask(new Task(taskTitle));
    }

    public void removeTaskOperation(BasicList targetedList, int totalTaskNumStarts0) throws InvalidIndexException {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to delete?");
            int taskIndexToDelete = Integer.parseInt(keyboard.nextLine());
            isValid(0, totalTaskNumStarts0, taskIndexToDelete);

            // TODO check this if statement
            if (taskIndexToDelete > targetedList.getTaskList().size()) {
                targetedList.removeTask(1, taskIndexToDelete - targetedList.getTaskList().size());
            } else {
                targetedList.removeTask(0, taskIndexToDelete);
            }
        }
    }

    public void finishTask(BasicList targetedList) throws InvalidIndexException {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to complete?");
            int taskIndexToComplete = Integer.parseInt(keyboard.nextLine());
            isValid(0, targetedList.getTaskList().size() - 1, taskIndexToComplete);

            targetedList.finishTask(taskIndexToComplete);
        }
    }

    public void undoFinishTask(BasicList targetedList) throws InvalidIndexException {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to undo finish?");
            int taskIndexToIncomplete = Integer.parseInt(keyboard.nextLine());
            isValid(0, targetedList.getCompletedTaskList().size() - 1, taskIndexToIncomplete);

            targetedList.undoFinishTask(taskIndexToIncomplete);
        }
    }



    public void sortListOperation(BasicList targetedList) throws InvalidIndexException {
        System.out.println("How do you want your list sorted? (0 - sortListAlphabeticallyAscending, "
                + "1 - sortListAlphabeticallyDescending, 2 - sortListDueDateAscending, 3 - sortListDueDateDescending"
                + "4 - sortListImportance)");
        int sortOption = Integer.parseInt(keyboard.nextLine());
        isValid(0, 4, sortOption);
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

    public void editTaskOperation(BasicList targetedList, int totalTaskNumStarts0) throws InvalidIndexException,
            InvalidDateException {
        if (targetedList.getTaskList().size() == 0 && targetedList.getCompletedTaskList().size() == 0) {
            System.out.println("Sorry, that list is empty");
        } else {
            System.out.println("Which task do you want to edit?");
            int taskIndexToEdit = Integer.parseInt(keyboard.nextLine());
            isValid(0, totalTaskNumStarts0, taskIndexToEdit);

            Task targetedTask;
            // TODO check this if statement
            if (taskIndexToEdit > targetedList.getTaskList().size()) {
                // completed Task List
                int completedListIndex = taskIndexToEdit - targetedList.getTaskList().size();
                targetedTask = targetedList.getCompletedTaskList().get(completedListIndex);
            } else {
                targetedTask = targetedList.getTaskList().get(taskIndexToEdit);
            }
            editTaskOptions(targetedTask);
        }

    }

    public void editTaskOptions(Task targetedTask) throws InvalidIndexException, InvalidDateException {
        System.out.println("0 - change task title, 1 - add step, 2 - delete step, 3 - complete a step, "
                + "4 - change due date, 5 - change importance, 6 - change note");
        int whichEditTaskOption = Integer.parseInt(keyboard.nextLine());
        isValid(0, 6, whichEditTaskOption);
        switch (whichEditTaskOption) {
            case 0:
                System.out.println("What's the new title?");
                targetedTask.setTitle(keyboard.nextLine());
                break;
            case 1:
                System.out.println("What's the step");
                targetedTask.addStep(keyboard.nextLine());
                break;
            case 2:             // TODO this needs improvement, situation where step is empty
                displayAllSteps(targetedTask);
                System.out.println("Which step to delete? (index start with 0)");
                int stepIndex = Integer.parseInt(keyboard.nextLine());
                isValid(0, targetedTask.getStep().size() - 1, stepIndex);
                targetedTask.deleteStep(stepIndex);
                break;
            case 3:
                displayAllSteps(targetedTask);
                System.out.println("Which step to complete? (index start with 0)");
                int stepIndexComplete = Integer.parseInt(keyboard.nextLine());
                isValid(0, targetedTask.getStep().size() - 1, stepIndexComplete);
                targetedTask.completeStep(stepIndexComplete);
                break;
            case 4:
                System.out.println("What's the new date?(in the format of \"year-mn-dy\" please, total is 10 digits");
                targetedTask.setDueDay(keyboard.nextLine());
                break;
            case 5:
                System.out.println("Please enter true or false");
                targetedTask.setImportant(Boolean.parseBoolean(keyboard.nextLine()));
                break;
            case 6:
                System.out.println("What's the new note?");
                targetedTask.setNote(keyboard.nextLine());
                break;
            default:
                System.out.println("ERROR in editTaskOptions ");
        }

    }

    public void displayAllSteps(Task task) {
        for (int i = 0; i < task.getStep().size(); i++) {
            System.out.println(i + ". " + task.getStep().get(i) + " -- " + task.getIsStepComplete().get(i));
        }
    }

}
