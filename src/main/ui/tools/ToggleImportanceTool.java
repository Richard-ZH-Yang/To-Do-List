package ui.tools;

import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleImportanceTool extends Tool {
    private int row;

    public ToggleImportanceTool(TaskListEditor editor, JComponent parent, int row) {
        super(editor, parent);
        this.row = row;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Toggle Task Importance");
        button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ToggleImportanceTool.ToggleImportanceToolClickHandler());
    }

    private void toggleImportance() {
        if (row > editor.getToDoListProgram().getCustomizedList().get(0).getTaskList().size() || row < 0) {
            JOptionPane.showMessageDialog(null, "please select a task to toggle importance");
        } else {
            editor.playSound("toggleImportance.wav");
            editor.getToDoListProgram().getCustomizedList().get(0).getTaskList().get(row).toggleImportance();
            editor.initializeCenter();
        }

    }

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
