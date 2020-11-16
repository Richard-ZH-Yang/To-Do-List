package ui.tools;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.Task;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompleteTaskTool extends Tool {
    private int row;

    public CompleteTaskTool(TaskListEditor editor, JComponent parent, int row) {
        super(editor, parent);
        this.row = row;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Complete Task");
        button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new CompleteTaskTool.CompleteTaskToolClickHandler());
    }

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
