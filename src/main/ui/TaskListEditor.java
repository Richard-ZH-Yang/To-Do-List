package ui;

import model.BasicList;
import model.Task;
import model.ToDoListProgram;
import ui.tools.AddTaskTool;
import ui.tools.LoadTool;
import ui.tools.SaveTool;
import ui.tools.Tool;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TaskListEditor extends JFrame {
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

        initializeNorth();
        initializeSouth();
        initializeCenter();

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
        JTextField addTaskField = new JTextField("new task", 10);
        JPanel southArea = new JPanel();
        southArea.setLayout(new GridLayout(1,0));
        southArea.setSize(new Dimension(0, 0));

        southArea.add(addTaskField);
        add(BorderLayout.SOUTH, southArea);

        AddTaskTool addTaskTool = new AddTaskTool(this, southArea, addTaskField);
        tools.add(addTaskTool);
    }


    public void initializeCenter() {
        JPanel centerArea = new JPanel();
        centerArea.setLayout(new GridLayout(1,0));
        centerArea.setSize(new Dimension(0, 0));

        initializeTable(centerArea);
        add(BorderLayout.CENTER, centerArea);



    }

    private void initializeTable(JPanel panel) {
        String[] colNames = {"Title", "Due Date", "Created Date", "Notes", "Important?", "Complete?",
                "OverDue?"};
        DefaultTableModel tableModelUncompletedTasks = new DefaultTableModel(colNames, 0);
        DefaultTableModel tableModelCompletedTasks = new DefaultTableModel(colNames, 0);
        JTable uncompletedTasksTable = new JTable(tableModelUncompletedTasks);
        JTable completedTasksTable = new JTable(tableModelCompletedTasks);

        addToTable(basicList.getTaskList(), tableModelUncompletedTasks);
        addToTable(basicList.getCompletedTaskList(), tableModelCompletedTasks);

        uncompletedTasksTable.setPreferredScrollableViewportSize(new Dimension(100, 25));
        completedTasksTable.setPreferredScrollableViewportSize(new Dimension(100, 25));
        uncompletedTasksTable.setFillsViewportHeight(true);
        completedTasksTable.setFillsViewportHeight(true);

        JScrollPane scrollPaneUncompleted = new JScrollPane(uncompletedTasksTable);
        JScrollPane scrollPaneCompleted = new JScrollPane(completedTasksTable);
        panel.add(BorderLayout.NORTH, scrollPaneUncompleted);
        panel.add(BorderLayout.SOUTH, scrollPaneCompleted);


    }
    
    private void addToTable(List<Task> tasks, DefaultTableModel tableModel) {
        for (int i = 0; i < tasks.size(); i++) {
            String title = tasks.get(i).getTitle();
            String dueDate = tasks.get(i).getDueDay();
            String createdDate = tasks.get(i).getCreatedDate();
            String notes = tasks.get(i).getNote();
            boolean isImportant = tasks.get(i).isImportant();
            boolean isComplete = tasks.get(i).isComplete();
            boolean isOverDue = tasks.get(i).isOverDue();

            Object[] data = {title, dueDate, createdDate, notes, isImportant, isComplete, isOverDue};

            tableModel.addRow(data);

        }
    }


    public ToDoListProgram getToDoListProgram() {
        return toDoListProgram;
    }

    public void setToDoListProgram(ToDoListProgram toDoListProgram) {
        this.toDoListProgram = toDoListProgram;
        basicList = toDoListProgram.getCustomizedList().get(0);
    }

    public static void main(String[] args) {
        new TaskListEditor();
    }
}
