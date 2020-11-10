package ui;

import model.Task;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;

// this class is derived from a stack overflow code
// https://stackoverflow.com/questions/20526917/load-arraylist-data-into-jtable
public class TaskListTableModel extends AbstractTableModel {
    private String[] colNames = {"Title", "Due Date", "Created Date", "Notes", "Important?", "Complete?",
            "OverDue?"};
    private List<Task> taskList;

    public TaskListTableModel(List<Task> taskList) {
        super();
        this.taskList = taskList;
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
        Task task = taskList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return task.getTitle();
            case 1:
                return task.getDueDay();
            case 2:
                return task.getCreatedDate();
            case 3:
                return task.getNote();
            case 4:
                return task.isImportant();
            case 5:
                return task.isComplete();
            case 6:
                return task.isOverDue();
        }
        return null;
    }
}
