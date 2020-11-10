package ui;

import model.BasicList;
import model.Task;
import model.ToDoListProgram;
import ui.tools.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class TaskListEditor extends JFrame {
    public static final String JSON_STORE = "./data/toDoListProgram.json";
    private ToDoListProgram toDoListProgram;
    private BasicList basicList;

    private List<Tool> tools;


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

        JPanel upperCenter = new JPanel();
        JPanel lowerCenter = new JPanel();

        initializeUnCTasks(upperCenter);
        initializeCTasks(lowerCenter);
        add(BorderLayout.CENTER, centerArea);

    }

    public void initializeUnCTasks(JPanel upperCenter) {
        for (Task task : basicList.getTaskList()) {
            JPanel innerTaskPane = new JPanel();
            IsTaskCompleteCheckBox completeCheckBox = new IsTaskCompleteCheckBox(task, this, innerTaskPane);
            DisplayTaskTool displayTaskTool = new DisplayTaskTool(this, innerTaskPane, task);
            upperCenter.add(innerTaskPane);
        }
    }

    public void initializeCTasks(JPanel lowerCenter) {
        for (Task task : basicList.getTaskList()) {
            JPanel innerTaskPane = new JPanel();
            IsTaskCompleteCheckBox completeCheckBox = new IsTaskCompleteCheckBox(task, this, innerTaskPane);
            DisplayTaskTool displayTaskTool = new DisplayTaskTool(this, innerTaskPane, task);
            lowerCenter.add(innerTaskPane);
        }
    }



//    private void initializeTable(JPanel panel) {
//
//        TableModel uncompletedTasksModel = new TaskListTableModel(basicList.getTaskList());
//        JTable uncompletedTaskTable = new JTable(uncompletedTasksModel);
//        JScrollPane scrollPane1 = new JScrollPane(uncompletedTaskTable);
//        panel.add(scrollPane1);
//
//    }

//    private void initializeTable(JPanel panel) {
//        String[] colNames = {"Title", "Due Date", "Created Date", "Notes", "Important?", "Complete?",
//                "OverDue?"};
//        DefaultTableModel tableModelUncompletedTasks = new DefaultTableModel(colNames, 0);
//        DefaultTableModel tableModelCompletedTasks = new DefaultTableModel(colNames, 0);
//        JTable uncompletedTasksTable = new JTable(tableModelUncompletedTasks);
//        JTable completedTasksTable = new JTable(tableModelCompletedTasks);
//
//        addToTable(basicList.getTaskList(), tableModelUncompletedTasks);
//        addToTable(basicList.getCompletedTaskList(), tableModelCompletedTasks);
//
//        uncompletedTasksTable.setPreferredScrollableViewportSize(new Dimension(100, 25));
//        completedTasksTable.setPreferredScrollableViewportSize(new Dimension(100, 25));
//        uncompletedTasksTable.setFillsViewportHeight(true);
//        completedTasksTable.setFillsViewportHeight(true);
//
//        //JScrollPane scrollPaneUncompleted = new JScrollPane(uncompletedTasksTable);
//        JScrollPane scrollPaneUncompleted = new JScrollPane(new JTextArea(5,40));
////        JScrollPane scrollPaneCompleted = new JScrollPane(completedTasksTable);
//        panel.add(BorderLayout.NORTH, scrollPaneUncompleted);
//        //panel.add(BorderLayout.SOUTH, scrollPaneCompleted);
////        panel.repaint();
//
//    }
//
//    private void addToTable(List<Task> tasks, DefaultTableModel tableModel) {
//        for (Task task : tasks) {
//            String title = task.getTitle();
//            String dueDate = task.getDueDay();
//            String createdDate = task.getCreatedDate();
//            String notes = task.getNote();
//            boolean isImportant = task.isImportant();
//            boolean isComplete = task.isComplete();
//            boolean isOverDue = task.isOverDue();
//
//
//            Object[] data = {title, dueDate, createdDate, notes, isImportant, isComplete, isOverDue};
//
//            tableModel.addRow(data);
//
//        }
//    }


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
