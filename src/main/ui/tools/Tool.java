package ui.tools;


import ui.*;

import javax.swing.*;
import java.awt.event.MouseEvent;


// This class is derived from UBC SimpleDrawingPlayer sample program
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
// this is class is the superclass for all the tools, consists information about a button, taskListEditor
public abstract class Tool {
    protected JButton button;
    protected TaskListEditor editor;
    private boolean active;

    // constructor
    // EFFECTS: construct a new Tool, create a button and add this to JComponent. Add the action listener
    public Tool(TaskListEditor editor, JComponent parent) {
        this.editor = editor;
        createButton(parent);
        addToParent(parent);
        active = false;
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // getters
    public boolean isActive() {
        return active;
    }

    // EFFECTS: sets this Tool's active field to true
    public void activate() {
        active = true;
    }

    // EFFECTS: sets this Tool's active field to false
    public void deactivate() {
        active = false;
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton(JComponent parent);

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }


}
