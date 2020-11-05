package ui;

import model.BasicList;
import model.ToDoListProgram;
import ui.tools.LoadTool;
import ui.tools.SaveTool;
import ui.tools.Tool;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TaskListEditor extends JFrame implements ActionListener {
    public static final String JSON_STORE = "./data/toDoListProgram.json";
    private ToDoListProgram toDoListProgram;
    private BasicList basicList;

    private List<Tool> tools;

    private JLabel label;
    private JTextField field;

    public TaskListEditor() {
        super("To Do List Program");
        toDoListProgram = new ToDoListProgram();
        toDoListProgram.getCustomizedList().add(new BasicList());
        basicList = toDoListProgram.getCustomizedList().get(0);
        tools = new ArrayList<>();


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());
        JButton btn = new JButton("Change");

        initializeNorth();
//        initializeSouth();

        setVisible(true);

    }

    public void initializeNorth() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(1,0));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.NORTH);
        SaveTool saveTool = new SaveTool(this, toolArea);
        tools.add(saveTool);

        LoadTool loadTool = new LoadTool(this, toolArea);
        tools.add(loadTool);

    }



    public void initializeSouth() {
        field = new JTextField(5);
        add(BorderLayout.SOUTH, field);
    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            label.setText(field.getText());
        }
    }


    public ToDoListProgram getToDoListProgram() {
        return toDoListProgram;
    }

    public void setToDoListProgram(ToDoListProgram toDoListProgram) {
        this.toDoListProgram = toDoListProgram;
    }

    public static void main(String[] args) {
        new TaskListEditor();
    }
}
