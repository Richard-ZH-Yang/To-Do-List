package ui;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.BasicList;
import model.Task;
import model.ToDoListProgram;
import persistence.JsonReader;
import ui.tools.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
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

        ///////////////////////////////////////////////////////load first///////////////////////////////
        try {
            JsonReader jsonReader = new JsonReader(TaskListEditor.JSON_STORE);
            toDoListProgram = jsonReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        basicList = toDoListProgram.getCustomizedList().get(0);
        ///////////////////////////////////////////////////////load first///////////////////////////////

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());

        initializeNorth();
        initializeSouth();
        initializeCenter();

        setLocationRelativeTo(null);
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


//    public void initializeCenter() {
//        JPanel centerArea = new JPanel();
//        centerArea.setLayout(new GridLayout(1,0));
//        centerArea.setSize(new Dimension(0, 0));
//
//        JPanel upperCenter = new JPanel();
//        JPanel lowerCenter = new JPanel();
//
//        initializeUnCTasks(upperCenter);
//        initializeCTasks(lowerCenter);
//        centerArea.add(upperCenter);
//        centerArea.add(lowerCenter);
//        add(BorderLayout.CENTER, centerArea);
//
//    }
//
//    public void initializeUnCTasks(JPanel upperCenter) {
//        for (Task task : basicList.getTaskList()) {
//            JPanel innerTaskPane = new JPanel();
//            IsTaskCompleteCheckBox completeCheckBox = new IsTaskCompleteCheckBox(task, this, innerTaskPane);
//            //DisplayTaskTool displayTaskTool = new DisplayTaskTool(this, innerTaskPane, task);
//            upperCenter.add(innerTaskPane);
//        }
//    }
//
//    public void initializeCTasks(JPanel lowerCenter) {
//        for (Task task : basicList.getTaskList()) {
//            JPanel innerTaskPane = new JPanel();
//            IsTaskCompleteCheckBox completeCheckBox = new IsTaskCompleteCheckBox(task, this, innerTaskPane);
//            //DisplayTaskTool displayTaskTool = new DisplayTaskTool(this, innerTaskPane, task);
//            lowerCenter.add(innerTaskPane);
//        }
//    }






    public void initializeCenter() {
        basicList = toDoListProgram.getCustomizedList().get(0);
        JPanel centerArea = new JPanel(new BorderLayout());
        centerArea.setLayout(new GridLayout(0,1));
        centerArea.setSize(new Dimension(0, 0));

        initializeTable(centerArea);

        centerArea.revalidate();
        centerArea.repaint();
        add(BorderLayout.CENTER, centerArea);

    }


    // reference: https://blog.csdn.net/xietansheng/article/details/78079806
    private void initializeTable(JPanel panel) {
        // TODO refactor: eliminate duplication

        TableModel uncompletedTasksModel = new TaskListTableModel(basicList.getTaskList());
        uncompletedTasksModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
                int firstRow = e.getFirstRow();
                int lastRow = e.getLastRow();
                int column = e.getColumn();

                // 事件的类型，可能的值有:
                //     TableModelEvent.INSERT   新行或新列的添加
                //     TableModelEvent.UPDATE   现有数据的更改
                //     TableModelEvent.DELETE   有行或列被移除
                int type = e.getType();
                if (type == TableModelEvent.UPDATE) {
                    for (int row = firstRow; row <= lastRow; row++) {
                        Object notes = uncompletedTasksModel.getValueAt(row, 3);

                        try {
                            basicList.getTaskList().get(row).setNote(notes.toString());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        });
        JTable uncompletedTaskTable = new JTable(uncompletedTasksModel);
        JScrollPane scrollPane1 = new JScrollPane(uncompletedTaskTable);
        panel.add(BorderLayout.NORTH, scrollPane1);

        // below are duplicate code
        TableModel completedTasksModel = new TaskListTableModel(basicList.getCompletedTaskList());
        JTable completedTaskTable = new JTable(completedTasksModel);
        JScrollPane scrollPane2 = new JScrollPane(completedTaskTable);
        panel.add(BorderLayout.SOUTH, scrollPane2);

    }


    private void addToTable(List<Task> tasks, DefaultTableModel tableModel) {
        for (Task task : tasks) {
            String title = task.getTitle();
            String dueDate = task.getDueDay();
            String createdDate = task.getCreatedDate();
            String notes = task.getNote();
            boolean isImportant = task.isImportant();
            boolean isComplete = task.isComplete();
            boolean isOverDue = task.isOverDue();


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
