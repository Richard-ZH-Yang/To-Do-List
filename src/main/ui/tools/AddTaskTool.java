package ui.tools;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.Task;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTaskTool extends Tool {
    private JTextField field;

    public AddTaskTool(TaskListEditor editor, JComponent parent, JTextField field) {
        super(editor, parent);
        this.field = field;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add");
        button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new AddTaskToolClickHandler());
    }

    private void addTask() throws InvalidDateException, ListFullException {
        editor.getToDoListProgram().getCustomizedList().get(0).addTask(new Task(field.getText()));
    }

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
