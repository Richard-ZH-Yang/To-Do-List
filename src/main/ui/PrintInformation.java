package ui;

import model.ToDoListProgram;

import java.util.Observable;
import java.util.Observer;

public class PrintInformation implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (ToDoListProgram.EVENT_ADD_LIST.equals(arg)) {
            System.out.println("A new list has been added to To Do List Program");
        } else if (ToDoListProgram.EVENT_DELETE_LIST.equals(arg)) {
            System.out.println("A list has been removed from To Do List Program");
        } else if (ToDoListProgram.EVENT_RENAME_LIST.equals(arg)) {
            System.out.println("A list has been renamed from To Do List Program");
        }
    }
}
