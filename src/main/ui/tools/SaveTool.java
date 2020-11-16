package ui.tools;

import model.*;
import persistence.*;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// this tool can save the toDoListProgram with the address from editor
public class SaveTool extends Tool {
    private JsonWriter jsonWriter;

    // constructor
    // EFFECTS: construct a new SaveTool, add this tool to the corresponding JComponent
    public SaveTool(TaskListEditor editor, JComponent parent) {
        super(editor, parent);
        jsonWriter = new JsonWriter(TaskListEditor.JSON_STORE);
    }

    // EFFECTS: create a new button named "Save", and add this button to the parent JComponent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save");
        button = customizeButton(button);
        addToParent(parent);
    }

    // EFFECTS: add an action listener for this button
    @Override
    protected void addListener() {
        button.addActionListener(new SaveToolClickHandler());
    }

    // EFFECTS: play the sound effects, and save the toDoListProgram from editor
    private void saveFile() {
        editor.playSound("save.wav");
        try {
            jsonWriter.open();
            jsonWriter.write(editor.getToDoListProgram());
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved successfully to " + TaskListEditor.JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File does not exist");
        }
    }

    // this class represents the action listener for this button
    private class SaveToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the Save Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile();
        }
    }
}
