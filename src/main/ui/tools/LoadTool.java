package ui.tools;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.*;
import persistence.*;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadTool extends Tool {
    private JsonReader jsonReader;


    public LoadTool(TaskListEditor editor, JComponent parent) {
        super(editor, parent);
        jsonReader = new JsonReader(TaskListEditor.JSON_STORE);
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load");
        button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new LoadToolClickHandler());
    }

    // EFFECTS: load the file to the default path
    private void loadFile() {
        try {
            ToDoListProgram toDoListProgram = jsonReader.read();
            editor.setToDoListProgram(toDoListProgram);
            JOptionPane.showMessageDialog(null, "Load successfully from " + TaskListEditor.JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + TaskListEditor.JSON_STORE);
        } catch (ListFullException listFullException) {
            JOptionPane.showMessageDialog(null, "Failed! List is full");
        } catch (InvalidDateException invalidDateException) {
            JOptionPane.showMessageDialog(null, "Failed! Date in the file is invalid");
        }
        editor.initializeCenter();
    }

    private class LoadToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the Load Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            loadFile();
        }
    }
}
