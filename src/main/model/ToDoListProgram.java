package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// create an instance of this class to start the to do list program
public class ToDoListProgram implements Writable {
    private List<BasicList> defaultList;        // user should not change defaultList size
    private List<BasicList> customizedList;
    private boolean endProgram;     // when equal to true, the program will end

    // constructor
    // EFFECTS: construct a new ToDoListProgram, set endProgram to false,
    //          initialize defaultList and customizedList field in this class. And add four default task lists to the
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

    // EFFECTS: convert toDoListProgram to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("defaultList", defaultListToJson());
        json.put("customizedList", customizedListToJson());
        json.put("endProgram", endProgram);
        return json;
    }

    // EFFECTS: return defaultList as a JSONArray
    private JSONArray defaultListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BasicList basicList : defaultList) {
            jsonArray.put(basicList.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: return customizedList as a JSONArray
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

    // MODIFIES: this
    // EFFECTS: set the customizedList to parameter's BasicListList
    public void setCustomizedList(List<BasicList> customizedList) {
        this.customizedList = customizedList;
    }

    // MODIFIES: this
    // EFFECTS: set the defaultList to parameter's BasicListList
    public void setDefaultList(List<BasicList> defaultList) {
        this.defaultList = defaultList;
    }

    public void setEndProgram(boolean endProgram) {
        this.endProgram = endProgram;
    }

    // normal getters method

    public boolean isEndProgram() {
        return endProgram;
    }

    public List<BasicList> getCustomizedList() {
        return customizedList;
    }

    public List<BasicList> getDefaultList() {
        return defaultList;
    }
}
