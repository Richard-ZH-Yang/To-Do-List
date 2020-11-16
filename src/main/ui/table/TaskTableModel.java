package ui.table;

import model.Task;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;

// this class is derived from the following two sources
// https://blog.csdn.net/xietansheng/article/details/78079806
// https://stackoverflow.com/questions/20526917/load-arraylist-data-into-jtable
public class TaskTableModel extends AbstractTableModel {
    private String[] colNames = {"Title", "Due Date", "Created Date", "Notes", "Important?", "Complete?"};
    private List<Task> taskList;
    private Object[][] rowData;

    public TaskTableModel(List<Task> taskList) {
        super();
        this.taskList = taskList;
        initializeRowData(taskList);

    }

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

    @Override
    public int getRowCount() {
        return taskList.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 2 && columnIndex != 4 && columnIndex != 5;
    }

    @Override
    public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
        rowData[rowIndex][columnIndex] = newValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column].toString();
    }
}
