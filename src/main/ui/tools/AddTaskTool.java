package ui.tools;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.Task;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// this tool can add a new task to uncompleted task list with the task name from JTextField
public class AddTaskTool extends Tool {
    private JTextField field; // the name for the new task

    // constructor
    // EFFECTS: construct a new AddTaskTool, add this tool to the corresponding JComponent
    public AddTaskTool(TaskListEditor editor, JComponent parent, JTextField field) {
        super(editor, parent);
        this.field = field;
    }

    // EFFECTS: create a new button named "Add", and add this button to the parent JComponent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add");
        button = customizeButton(button);
        addToParent(parent);
    }

    // EFFECTS: add an action listener for this button
    @Override
    protected void addListener() {
        button.addActionListener(new AddTaskToolClickHandler());
    }

    // MODIFIES: editor
    // EFFECTS: play the sound effects, and add the task to toDoListProgram
    private void addTask() throws InvalidDateException, ListFullException {
        editor.playSound("addTask.wav");
        editor.getToDoListProgram().getCustomizedList().get(0).addTask(new Task(field.getText()));
        editor.initializeCenter();

    }

    // this class represents the action listener for this button
    private class AddTaskToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the Add Task Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Add")) {
                try {
                    addTask();
                } catch (InvalidDateException invalidDateException) {
                    JOptionPane.showMessageDialog(null, "Date is invalid");
                } catch (ListFullException listFullException) {
                    JOptionPane.showMessageDialog(null, "List is full");
                }
            }
        }
    }
}
