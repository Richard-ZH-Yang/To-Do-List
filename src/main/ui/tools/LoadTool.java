package ui.tools;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.*;
import persistence.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import ui.TaskListEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

// this tool can load the toDoListProgram to the editor with the address from taskListEditor
public class LoadTool extends Tool {
    private JsonReader jsonReader;

    // constructor
    // EFFECTS: construct a new LoadTool, add this tool to the corresponding JComponent
    public LoadTool(TaskListEditor editor, JComponent parent) {
        super(editor, parent);
        jsonReader = new JsonReader(TaskListEditor.JSON_STORE);
    }


    // EFFECTS: create a new button named "Load", and add this button to the parent JComponent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load");
        button = customizeButton(button);
        addToParent(parent);
    }

    // EFFECTS: add an action listener for this button
    @Override
    protected void addListener() {
        button.addActionListener(new LoadToolClickHandler());
    }

    // MODIFIES: editor
    // EFFECTS: play the sound effects, load the file to the editor
    public void loadFile() {
        try {
            ToDoListProgram toDoListProgram = jsonReader.read();
            editor.setToDoListProgram(toDoListProgram);
            editor.playSound("load.wav");
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

    // this class represents the action listener for this button
    private class LoadToolClickHandler implements ActionListener {
        // EFFECTS: sets active tool to the Load Tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            loadFile();
        }
    }
}
