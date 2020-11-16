package ui;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.BasicList;
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

public class TaskListEditor extends JFrame {
    public static final String JSON_STORE = "./data/toDoListProgram.json";
    private ToDoListProgram toDoListProgram;
    private BasicList basicList;
    private JPanel centerArea;
    private int row;
    private int col;
    private JPanel upperArea;


    public TaskListEditor() {
        super("To Do List Program");
        toDoListProgram = new ToDoListProgram();
        toDoListProgram.getCustomizedList().add(new BasicList());
        basicList = toDoListProgram.getCustomizedList().get(0);

        loadFirst();

        centerArea = new JPanel(new BorderLayout());
        upperArea = new JPanel(new GridLayout(2,2));
        row = 0;
        col = 0;


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
        upperArea.removeAll();
        add(upperArea, BorderLayout.NORTH);

        SaveTool saveTool = new SaveTool(this, upperArea);

        LoadTool loadTool = new LoadTool(this, upperArea);

        CompleteTaskTool completeTaskTool = new CompleteTaskTool(this, upperArea, row);
        ToggleImportanceTool toggleImportanceTool = new ToggleImportanceTool(this, upperArea, row);

        centerArea.revalidate();
        centerArea.repaint();

    }

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



    public void initializeSouth() {
        JTextField addTaskField = new JTextField("new task", 10);
        JPanel southArea = new JPanel();
        southArea.setLayout(new GridLayout(1,0));
        southArea.setSize(new Dimension(0, 0));

        southArea.add(addTaskField);
        add(BorderLayout.SOUTH, southArea);

        AddTaskTool addTaskTool = new AddTaskTool(this, southArea, addTaskField);
    }


    public void initializeCenter() {
        centerArea.removeAll();
        basicList = toDoListProgram.getCustomizedList().get(0);
        centerArea.setLayout(new GridLayout(0,1));
        centerArea.setSize(new Dimension(0, 0));

        initializeTable(centerArea);

        centerArea.revalidate();
        centerArea.repaint();
        //setContentPane(centerArea);
        add(BorderLayout.CENTER, centerArea);
//        pack();
    }

    // this class is derived from the following source
    // https://blog.csdn.net/xietansheng/article/details/78079806
    private void initializeTable(JPanel panel) {
        TableModel taskTableModel = new TaskTableModel(basicList.getTaskList());
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
                col = taskTable.getSelectedColumn();
                initializeNorth();
            }
        });
    }

    private void initializeRowSorter(TableModel taskTableModel, JTable taskTable) {
        // row sorter
        RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(taskTableModel);
        taskTable.setRowSorter(rowSorter);
    }

    private void initializeTableCellEditor(TableModel taskTableModel, JTable taskTable) {
        // table cell editor
        TaskTableCellEditor cellEditor = new TaskTableCellEditor(new JTextField());
        for (int i = 1; i < taskTableModel.getColumnCount(); i++) {
            TableColumn tableColumn = taskTable.getColumn(taskTableModel.getColumnName(i));
            tableColumn.setCellEditor(cellEditor);
        }
    }

    private void initializeTableRenderer(TableModel taskTableModel, JTable taskTable) {
        // table renderer
        TaskTableCellRenderer renderer = new TaskTableCellRenderer();
        for (int i = 0; i < taskTableModel.getColumnCount(); i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = taskTable.getColumn(taskTableModel.getColumnName(i));;
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
    }



    private void makeChangesInTable(int row, TableModel uncompletedTasksModel) {
        Object title = uncompletedTasksModel.getValueAt(row, 0);
        Object dueDate = uncompletedTasksModel.getValueAt(row, 1);
        Object notes = uncompletedTasksModel.getValueAt(row, 3);

        try {
            basicList.getTaskList().get(row).setNote(notes.toString());
            basicList.getTaskList().get(row).setTitle(title.toString());
            basicList.getTaskList().get(row).setDueDay(dueDate.toString());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Failed Operation! Please enter the correct format.");
        }

    }


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


    public ToDoListProgram getToDoListProgram() {
        return toDoListProgram;
    }

    public void setToDoListProgram(ToDoListProgram toDoListProgram) {
        this.toDoListProgram = toDoListProgram;
        basicList = toDoListProgram.getCustomizedList().get(0);
    }

    // reference: https://blog.csdn.net/xietansheng/article/details/78079806
    public static class TaskTableCellEditor extends DefaultCellEditor {
        public TaskTableCellEditor(JTextField textField) {
            super(textField);
        }

        public TaskTableCellEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public TaskTableCellEditor(JComboBox comboBox) {
            super(comboBox);
        }

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
    public static class TaskTableCellRenderer extends DefaultTableCellRenderer {
        /**
         * 返回默认的表单元格渲染器，此方法在父类中已实现，直接调用父类方法返回，在返回前做相关参数的设置即可
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            // 偶数行背景设置为白色，奇数行背景设置为灰色
            if (row % 2 == 0) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }

            setHorizontalAlignment(SwingConstants.CENTER);


            // 设置提示文本，当鼠标移动到当前(row, column)所在单元格时显示的提示文本
            setToolTipText(table.getColumnName(column) + ": " + table.getValueAt(row, column));

            // PS: 多个单元格使用同一渲染器时，需要自定义的属性，必须每次都设置，否则将自动沿用上一次的设置。

            /*
             * 单元格渲染器为表格单元格提供具体的显示，实现了单元格渲染器的 DefaultTableCellRenderer 继承自
             * 一个标准的组件类 JLabel，因此 JLabel 中相应的 API 在该渲染器实现类中都可以使用。
             *
             * super.getTableCellRendererComponent(...) 返回的实际上是当前对象（this），即 JLabel 实例，
             * 也就是以 JLabel 的形式显示单元格。
             *
             * 如果需要自定义单元格的显示形式（比如显示成按钮、复选框、内嵌表格等），可以在此自己创建一个标准组件
             * 实例返回。
             */

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public void setRow(int row) {
        this.row = row;
    }

    public static void main(String[] args) {
        new TaskListEditor();
    }

}
