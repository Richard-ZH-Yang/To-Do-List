package ui.table;

import model.Task;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;

// this class is derived from the following two sources
// https://blog.csdn.net/xietansheng/article/details/78079806
// https://stackoverflow.com/questions/20526917/load-arraylist-data-into-jtable
// this table model has 6 columns, and represents the table that taskEditor uses
public class TaskTableModel extends AbstractTableModel {
    private String[] colNames = {"Title", "Due Date", "Created Date", "Notes", "Important?", "Complete?"};  // 6 columns
    private List<Task> taskList;    // represents the row data that matches the colNames
    private Object[][] rowData;     // represents the data frame, does not include the header which is colNames

    // constructor
    // EFFECTS: construct a new TaskTableModel, and initialize the data frame
    public TaskTableModel(List<Task> taskList) {
        super();
        this.taskList = taskList;
        initializeRowData(taskList);

    }

    // MODIFIES: this
    // EFFECTS: initialize rowData, set every element
    public void initializeRowData(List<Task> taskList) {
        rowData = new Object[getRowCount()][getColumnCount()];
        for (int row = 0; row < getRowCount(); row++) {
            rowData[row][0] = taskList.get(row).getTitle();
            rowData[row][1] = taskList.get(row).getDueDay();
            rowData[row][2] = taskList.get(row).getCreatedDate();
            rowData[row][3] = taskList.get(row).getNote();
            rowData[row][4] = taskList.get(row).isImportant();
            rowData[row][5] = taskList.get(row).isComplete();
        }
    }

    // EFFECTS: get how many rows not including the header colNames
    @Override
    public int getRowCount() {
        return taskList.size();
    }

    // EFFECTS: get how many columns
    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    // EFFECTS: get the value at the row col with respect to rowData
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }

    // EFFECTS: make column 2,4,5 not editable
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 2 && columnIndex != 4 && columnIndex != 5;
    }

    // MODIFIES: this
    // EFFECTS: set the value for specific element
    @Override
    public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
        rowData[rowIndex][columnIndex] = newValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    // EFFECTS: get the colNames with respect to column index
    @Override
    public String getColumnName(int column) {
        return colNames[column].toString();
    }
}
