package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// create an instance of this class to start the to do list program
public class ToDoListProgram implements Writable {
    private List<BasicList> defaultList;
    private List<BasicList> customizedList;
    private boolean endProgram;     // when equal to true, the program will end

    // constructor
    // MODIFIES: this
    // EFFECTS: initialize defaultList and customizedList field in this class. And add four default task list to the
    //          default task list module.
    public ToDoListProgram() {
        endProgram = false;
        defaultList = new ArrayList<>();
        customizedList = new ArrayList<>();

        BasicList important = new BasicList("Important");
        BasicList tasks = new BasicList("Tasks");
        BasicList myDay = new BasicList("My Day");
        BasicList planned = new BasicList("Planned");
        defaultList.add(myDay);
        defaultList.add(important);
        defaultList.add(planned);
        defaultList.add(tasks);

    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("defaultList", defaultListToJson());
        json.put("customizedList", customizedListToJson());
        json.put("endProgram", endProgram);
        return json;
    }

    private JSONArray defaultListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BasicList basicList : defaultList) {
            jsonArray.put(basicList.toJson());
        }
        return jsonArray;
    }

    private JSONArray customizedListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BasicList basicList : customizedList) {
            jsonArray.put(basicList.toJson());
        }
        return jsonArray;
    }


    // EFFECTS: if the third parameter value is outside of the integral [lower, upper], throw IndexOutOfBoundsException
    public void isValid(int lower, int upper, int value) {
        boolean isWorking = (value >= lower) && (value <= upper);
        if (!isWorking) {
            throw  new IndexOutOfBoundsException("Number out of bound");
        }
    }

    public void setCustomizedList(List<BasicList> customizedList) {
        this.customizedList = customizedList;
    }

    public void setDefaultList(List<BasicList> defaultList) {
        this.defaultList = defaultList;
    }

    // EFFECTS: returns true if program ends
    public boolean isEndProgram() {
        return endProgram;
    }

    // MODIFIES: this
    public void setEndProgram(boolean endProgram) {
        this.endProgram = endProgram;
    }

    public List<BasicList> getCustomizedList() {
        return customizedList;
    }

    public List<BasicList> getDefaultList() {
        return defaultList;
    }
}
