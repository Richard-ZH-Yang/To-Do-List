package ui.tools;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.Task;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// this tool can complete a task from uncompleted task list with row number represents the index of uncompleted task in
// taskList from BasicList
public class CompleteTaskTool extends Tool {
    private int row;    // the index of the task to complete

    // constructor
    // EFFECTS: construct a new CompleteTaskTool, add this tool to the corresponding JComponent
    public CompleteTaskTool(TaskListEditor editor, JComponent parent, int row) {
        super(editor, parent);
        this.row = row;
    }

    // EFFECTS: create a new button named "Complete Task", and add this button to the parent JComponent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Complete Task");
        button = customizeButton(button);
        addToParent(parent);
    }

    // EFFECTS: add an action listener for this button
    @Override
    protected void addListener() {
        button.addActionListener(new CompleteTaskTool.CompleteTaskToolClickHandler());
    }

    // MODIFIES: editor
    // EFFECTS: if the row is -1, show a message. Otherwise,
    //          play the sound effects, complete the task, and set the row in taskListEditor to -1
    private void completeTask() {
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "please select a task!");
        } else {
            editor.playSound("completeTask.wav");
            editor.getToDoListProgram().getCustomizedList().get(0).finishTask(row);
            editor.setRow(-1);
            editor.initializeNorth();
            editor.initializeCenter();
        }

    }

    // this class represents the action listener for this button
    private class CompleteTaskToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the complete Task Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Complete Task")) {
                completeTask();
            }
        }
    }
}
