package ui;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.ToDoListProgram;
import persistence.JsonReader;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import ui.table.TaskTableModel;
import ui.tools.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// GUI for the ToDoListProgram. Able to add task, edit task from a table, save load and play sound
public class TaskListEditor extends JFrame {
    public static final String JSON_STORE = "./data/toDoListProgram.json";      // directory for save and load
    private ToDoListProgram toDoListProgram;
    private JPanel centerArea;              // contains a JTable to display information
    private int row;                        // current row in the JTable
    private JPanel upperArea;               // contains four buttons

    // constructor
    // EFFECTS: constructs a new TaskListEditor object. Initialize everything related to JFrame and JTable and buttons
    public TaskListEditor() {
        super("To Do List Program");        // the name for the main frame
        toDoListProgram = new ToDoListProgram();
        toDoListProgram.addBasicList();

        loadFirst();

        centerArea = new JPanel(new BorderLayout());
        upperArea = new JPanel(new GridLayout(2,2));
        row = 0;


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(14, 14, 14, 14));
        setLayout(new BorderLayout());  // has north, south and center

        initializeNorth();
        initializeSouth();
        initializeCenter();

        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: clears and add four buttons into the northern region of the JFrame
    public void initializeNorth() {
        upperArea.removeAll();
        add(upperArea, BorderLayout.NORTH);

        SaveTool saveTool = new SaveTool(this, upperArea);

        LoadTool loadTool = new LoadTool(this, upperArea);

        CompleteTaskTool completeTaskTool = new CompleteTaskTool(this, upperArea, row);
        ToggleImportanceTool toggleImportanceTool = new ToggleImportanceTool(this, upperArea, row);

        centerArea.revalidate();
        centerArea.repaint();

    }

    // MODIFIES: this, toDoListProgram
    // EFFECTS: ask user if they want to load first. If yes, then load the data from directory
    public void loadFirst() {
        int result = JOptionPane.showConfirmDialog(this, "Do you want to load first?",
                                                    "load file from " + JSON_STORE,
                                                            JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            try {
                JsonReader jsonReader = new JsonReader(JSON_STORE);
                ToDoListProgram toDoListProgram = jsonReader.read();
                setToDoListProgram(toDoListProgram);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to read from file: " + TaskListEditor.JSON_STORE);
            } catch (ListFullException listFullException) {
                JOptionPane.showMessageDialog(null, "Failed! List is full");
            } catch (InvalidDateException invalidDateException) {
                JOptionPane.showMessageDialog(null, "Failed! Date in the file is invalid");
            }
        }

    }


    // MODIFIES: this
    // EFFECTS: add a JTextField and a button in the southern region of the JFrame.
    public void initializeSouth() {
        JTextField addTaskField = new JTextField("new task", 10);
        JPanel southArea = new JPanel();
        southArea.setLayout(new GridLayout(1,0));
        southArea.setSize(new Dimension(0, 0));

        southArea.add(addTaskField);
        add(BorderLayout.SOUTH, southArea);

        AddTaskTool addTaskTool = new AddTaskTool(this, southArea, addTaskField);
    }


    // MODIFIES: this
    // EFFECTS: clear the center region and then initialize the JTable that represents the tasks
    public void initializeCenter() {
        centerArea.removeAll();
        centerArea.setLayout(new GridLayout(0,1));
        centerArea.setSize(new Dimension(0, 0));

        initializeTable(centerArea);

        centerArea.revalidate();
        centerArea.repaint();
        add(BorderLayout.CENTER, centerArea);
    }

    // this class is derived from the following source
    // https://blog.csdn.net/xietansheng/article/details/78079806
    // MODIFIES: this
    // EFFECTS: initialize the JTable that represents tasks, and also initialize all features for the table.
    //          add this table into the center region
    private void initializeTable(JPanel panel) {
        TableModel taskTableModel = new TaskTableModel(toDoListProgram.getSpecificBasicList(4).getTaskList());
        addTableListener(taskTableModel);

        JTable taskTable = new JTable(taskTableModel);
        taskTable.getTableHeader().setReorderingAllowed(false);

        initializeTableCellEditor(taskTableModel, taskTable);

        initializeTableSelection(taskTable);

        initializeTableRenderer(taskTableModel, taskTable);

        initializeRowSorter(taskTableModel, taskTable);

        JScrollPane scrollPane1 = new JScrollPane(taskTable);
        panel.add(taskTable.getTableHeader(), BorderLayout.NORTH);
        panel.add(BorderLayout.CENTER, scrollPane1);

    }

    // MODIFIES: this
    // EFFECTS: add the table listener for the taskTableModel, and will only handle UPDATE events
    private void addTableListener(TableModel taskTableModel) {
        taskTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // The first and last line that was changed. If only change one line, then they are the same
                int firstRow = e.getFirstRow();
                int lastRow = e.getLastRow();
                int column = e.getColumn();

                // possible events include:
                //     TableModelEvent.INSERT   New row or column insert
                //     TableModelEvent.UPDATE   change current data frame
                //     TableModelEvent.DELETE   remove row or column
                int type = e.getType();
                if (type == TableModelEvent.UPDATE) {

                    for (int row = firstRow; row <= lastRow; row++) {
                        makeChangesInTable(row, taskTableModel);
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initialize the table selection functionality. Will change the row and col field to corresponding row
    //          and column when selecting in the table
    private void initializeTableSelection(JTable taskTable) {
        // table selection
        taskTable.setCellSelectionEnabled(true);
        ListSelectionModel selectionModel = taskTable.getSelectionModel();
        // single selection
        selectionModel.setSelectionMode(0);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                row = taskTable.getSelectedRow();
//                col = taskTable.getSelectedColumn();
                initializeNorth();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initialize the table row sorter functionality. Will now sort the row when clicking the table header
    private void initializeRowSorter(TableModel taskTableModel, JTable taskTable) {
        // row sorter
        RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(taskTableModel);
        taskTable.setRowSorter(rowSorter);
    }

    // MODIFIES: this
    // EFFECTS: initialize the table cell editor functionality. With the help of TaskTableCellEditor static class,
    //          it can customized the effect when editing a cell
    private void initializeTableCellEditor(TableModel taskTableModel, JTable taskTable) {
        // table cell editor
        TaskTableCellEditor cellEditor = new TaskTableCellEditor(new JTextField());
        for (int i = 0; i < taskTableModel.getColumnCount(); i++) {
            TableColumn tableColumn = taskTable.getColumn(taskTableModel.getColumnName(i));
            tableColumn.setCellEditor(cellEditor);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize the table cell renderer functionality. With the help of TaskTableCellRenderer static class,
    //          it can customized the effect for the format of the table
    private void initializeTableRenderer(TableModel taskTableModel, JTable taskTable) {
        // table renderer
        TaskTableCellRenderer renderer = new TaskTableCellRenderer();
        for (int i = 0; i < taskTableModel.getColumnCount(); i++) {
            // get the column depends on the column name
            TableColumn tableColumn = taskTable.getColumn(taskTableModel.getColumnName(i));;
            // set the render for those columns
            tableColumn.setCellRenderer(renderer);
        }
    }


    // MODIFIES: this
    // EFFECTS: When a change is made in table, mutate the basicList to the corresponding changes
    private void makeChangesInTable(int row, TableModel uncompletedTasksModel) {
        Object title = uncompletedTasksModel.getValueAt(row, 0);
        Object dueDate = uncompletedTasksModel.getValueAt(row, 1);
        Object notes = uncompletedTasksModel.getValueAt(row, 3);

        try {
            toDoListProgram.getSpecificBasicList(4).getTaskList().get(row).setNote(notes.toString());
            toDoListProgram.getSpecificBasicList(4).getTaskList().get(row).setTitle(title.toString());
            toDoListProgram.getSpecificBasicList(4).getTaskList().get(row).setDueDay(dueDate.toString());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Failed Operation! Please enter the correct format.");
        }

    }


    // EFFECTS: play the sound effect in the data package. If the file cannot be found, display information
    public void playSound(String fileName) {
        InputStream inputStream;
        String address = "./data/sound/" + fileName;
        try {
            inputStream = new FileInputStream(address);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Unable to play sound from file: " + address);
        }

    }

    // EFFECTS: getter for toDoListProgram field
    public ToDoListProgram getToDoListProgram() {
        return toDoListProgram;
    }

    // MODIFIES: this
    // EFFECTS: set the toDoListProgram with respect to parameter
    public void setToDoListProgram(ToDoListProgram toDoListProgram) {
        this.toDoListProgram = toDoListProgram;
    }

    // reference: https://blog.csdn.net/xietansheng/article/details/78079806
    // this class represents the effects when editing a table cell
    public static class TaskTableCellEditor extends DefaultCellEditor {
        // constructor
        // EFFECTS: construct a new TaskTableCellEditor with respect to textField
        public TaskTableCellEditor(JTextField textField) {
            super(textField);
        }

        // constructor
        // EFFECTS: construct a new TaskTableCellEditor with respect to checkBox
        public TaskTableCellEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        // constructor
        // EFFECTS: construct a new TaskTableCellEditor with respect to comboBox
        public TaskTableCellEditor(JComboBox comboBox) {
            super(comboBox);
        }

        // EFFECTS: when user editing the cell, if the input is "null" or"", it will make the word in cell turn into red
        //          and make the cell not editable until user inputs other things, which it will turn word into green
        @Override
        public boolean stopCellEditing() {
            // get current editor component
            Component comp = getComponent();

            // get current editor value
            Object obj = getCellEditorValue();

            // return false if it's a null or nothing
            if (obj == null || obj.toString().matches("") || obj.toString().matches("null")) {
                // set the content to red if it's illegal
                comp.setForeground(Color.RED);
                return false;
            }

            // when it's legal, set color ot green
            comp.setForeground(Color.GREEN);

            return super.stopCellEditing();
        }
    }


    //reference: https://blog.csdn.net/xietansheng/article/details/78079806
    // this class represents the format for the table
    public static class TaskTableCellRenderer extends DefaultTableCellRenderer {

        // EFFECTS: set the row colour, make the data frame align center.Also when moving the mouse on top of the cell,
        //          display information about that cell. Finally return the DefaultTableCellRenderer
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            // make the row color to white - light grey - white format
            if (row % 2 == 0) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }

            // align center for the word inside the data frame, except header
            setHorizontalAlignment(SwingConstants.CENTER);

            // display detail the information about which column, and the vlaue that cell
            setToolTipText(table.getColumnName(column) + ": " + table.getValueAt(row, column));

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // MODIFIES: this
    // EFFECTS: set the row number. Used after delete a row from a table to initialize the row
    public void setRow(int row) {
        this.row = row;
    }

    // EFFECTS: run this method to run the GUI
    public static void main(String[] args) {
        new TaskListEditor();
    }

}
