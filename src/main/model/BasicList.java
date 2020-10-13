package model;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicList {
    protected List<Task> taskList;

    public BasicList() {
        taskList = new ArrayList<>();
    }

    
}
