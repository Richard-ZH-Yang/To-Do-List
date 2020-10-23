//package model;
//
//import exceptions.InvalidDateException;
//import exceptions.InvalidIndexException;
//import exceptions.ListFullException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class ToDoListProgram {
//    List<BasicList> defaultList;    // contains a list of default list
//    List<BasicList> customizedList; // contains a list of customized list
//    boolean endProgram = false;     // when equal to true, the program will end
//    Scanner keyboard = new Scanner(System.in);  // for user input
//
//
//
//
//
//    // MODIFIES: this
//    // EFFECTS: initialize defaultList and customizedList field in this class. And add four default task list to the
//    //          default task list module.
//    public void initialize() {
//        defaultList = new ArrayList<>();
//        customizedList = new ArrayList<>();
//
//        BasicList important = new DefaultList("Important");
//        BasicList tasks = new DefaultList("Tasks");
//        BasicList myDay = new DefaultList("My Day");
//        BasicList planned = new DefaultList("Planned");
//        defaultList.add(myDay);
//        defaultList.add(important);
//        defaultList.add(planned);
//        defaultList.add(tasks);
//    }
//
//    // EFFECTS: if the third parameter value is outside of the integral [lower, upper], throw InvalidIndexException
//    public void isValid(int lower, int upper, int value) throws InvalidIndexException {
//        boolean isWorking = (value >= lower) && (value <= upper);
//        if (!isWorking) {
//            throw  new InvalidIndexException("Number out of bound");
//        }
//    }
//
//    // EFFECTS: display and let user choose the unique operations that can only preform in customized module
//    public void operationInCustomizedListModule() throws InvalidIndexException,
//            ListFullException, InvalidDateException {
//        System.out.println("0 - Add a customized list, 1 - delete a customized list, 2 - rename a customized list"
//                + ", 3 - edit the list");
//        int whichCustomizedListOperation = Integer.parseInt(keyboard.nextLine());
//        isValid(0, 3, whichCustomizedListOperation);
//        if (whichCustomizedListOperation == 0) {
//            addCustomizedList();
//        } else if (whichCustomizedListOperation == 1) {
//            deleteCustomizedList();
//        } else if (whichCustomizedListOperation == 2) {
//            renameCustomizedList();
//        } else if (whichCustomizedListOperation == 3) {
//            System.out.println("Which list do you want to operate:   ");
//            displayAllCustomizedListIndexNTitle();
//            int whichList = Integer.parseInt(keyboard.nextLine());
//            isValid(0, customizedList.size() - 1, whichList);
//            BasicList targetedList = customizedList.get(whichList);
//            operationInList(targetedList);
//        }
//    }
//}
