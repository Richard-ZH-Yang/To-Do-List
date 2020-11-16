package ui.tools;

import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// this tool can toggle the task importance in the selected task
public class ToggleImportanceTool extends Tool {
    private int row;    // the index to toggle

    // constructor
    // EFFECTS: construct a new ToggleImportanceTool, add this tool to the corresponding JComponent
    public ToggleImportanceTool(TaskListEditor editor, JComponent parent, int row) {
        super(editor, parent);
        this.row = row;
    }

    // EFFECTS: create a new button named "Toggle Task Importance", and add this button to the parent JComponent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Toggle Task Importance");
        button = customizeButton(button);
        addToParent(parent);
    }

    // EFFECTS: add an action listener for this button
    @Override
    protected void addListener() {
        button.addActionListener(new ToggleImportanceTool.ToggleImportanceToolClickHandler());
    }

    // MODIFIES: editor
    // EFFECTS: if row is not valid, display information. Otherwise,
    //          play the sound effects, and toggle the importance for the task with respect to row
    private void toggleImportance() {
        if (row > editor.getToDoListProgram().getCustomizedList().get(0).getTaskList().size() || row < 0) {
            JOptionPane.showMessageDialog(null, "please select a task to toggle importance");
        } else {
            editor.playSound("toggleImportance.wav");
            editor.getToDoListProgram().getCustomizedList().get(0).getTaskList().get(row).toggleImportance();
            editor.initializeCenter();
        }

    }

    // this class represents the action listener for this button
    private class ToggleImportanceToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the toggle Task Importance Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Toggle Task Importance")) {
                toggleImportance();
            }
        }
    }
}
