package ui.tools;

import model.*;
import persistence.*;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SaveTool extends Tool {
    private JsonWriter jsonWriter;


    public SaveTool(TaskListEditor editor, JComponent parent) {
        super(editor, parent);
        jsonWriter = new JsonWriter(TaskListEditor.JSON_STORE);
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save");
        button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new SaveToolClickHandler());
    }

    // EFFECTS: save the file to the default path
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

    private class SaveToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the Save Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile();
        }
    }
}
