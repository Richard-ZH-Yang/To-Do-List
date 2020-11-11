//package ui;
//
//import model.Task;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//
//public class IsTaskCompleteCheckBox {
//    JCheckBox isComplete;
//    Task task;
//    TaskListEditor editor;
//
//    public IsTaskCompleteCheckBox(Task task, TaskListEditor editor, JComponent parent) {
//        isComplete = new JCheckBox("complete");
//        this.task = task;
//        this.editor = editor;
//        isComplete.addItemListener(new CompleteTaskCheckHandler());
//        parent.add(isComplete);
//    }
//
//
//    private class CompleteTaskCheckHandler implements ItemListener {
//
//        @Override
//        public void itemStateChanged(ItemEvent e) {
//            if (isComplete.isSelected()) {
//                task.setComplete(true);
//                editor.getToDoListProgram().getCustomizedList().get(0).finishTask(task);
//            } else {
//                task.setComplete(false);
//                editor.getToDoListProgram().getCustomizedList().get(0).undoFinishTask(task);
//            }
//        }
//    }
//
//
//}
