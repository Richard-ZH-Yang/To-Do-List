package ui.tools;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.Task;
import model.ToDoListProgram;
import persistence.JsonReader;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DisplayTaskTool extends Tool {
    private Task task;

    public DisplayTaskTool(TaskListEditor editor, JComponent parent, Task task) {
        super(editor, parent);
        this.task = task;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton(task.getTitle());
        button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new DisplayInfoClickHandler());
    }

    private void displayTaskInfo() {
        JOptionPane.showMessageDialog(null, "I am displaying task info\n"
                + "title: " + task.getTitle());


    }


    private class DisplayInfoClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the Display Task Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            displayTaskInfo();

        }
    }
}
