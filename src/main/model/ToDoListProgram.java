package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;


public class ToDoListProgram extends Observable implements Writable, Iterable<BasicList> {
    private List<BasicList> basicLists;
    public static final String EVENT_ADD_LIST = "ADD_LIST";
    public static final String EVENT_DELETE_LIST = "DELETE_LIST";
    public static final String EVENT_RENAME_LIST = "RENAME_LIST";


    // constructor
    // EFFECTS: construct a new ToDoListProgram, set endProgram to false,
    //          initialize defaultList and customizedList field in this class. And add four default task lists to the
    //          default task list module.
    public ToDoListProgram() {
        basicLists = new ArrayList<>();
        BasicList important = new BasicList("Important");
        BasicList tasks = new BasicList("Tasks");
        BasicList myDay = new BasicList("My Day");
        BasicList planned = new BasicList("Planned");
        basicLists.add(myDay);
        basicLists.add(important);
        basicLists.add(planned);
        basicLists.add(tasks);

    }

    // EFFECTS: convert toDoListProgram to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("basicLists", basicListsToJson());
        return json;
    }

    // EFFECTS: return basicLists as a JSONArray
    private JSONArray basicListsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BasicList basicList : basicLists) {
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

    public void addBasicList(String name) {
        basicLists.add(new BasicList(name));
        notifyAboutAdding();
    }

    public void addBasicList() {
        basicLists.add(new BasicList());
        notifyAboutAdding();
    }

    public void addBasicList(BasicList basicList) {
        basicLists.add(basicList);
        notifyAboutAdding();
    }

    private void notifyAboutAdding() {
        setChanged();
        notifyObservers(EVENT_ADD_LIST);
    }

    private void notifyAboutDeleting() {
        setChanged();
        notifyObservers(EVENT_DELETE_LIST);
    }

    public void deleteBasicList(int index) {
        isValid(0, basicLists.size() - 1, index);
        basicLists.remove(index);
        notifyAboutDeleting();
    }

    public void deleteAllBasicList() {
        basicLists.clear();
        notifyAboutDeleting();
    }

    public void renameBasicList(int index, String name) {
        isValid(0, basicLists.size() - 1, index);
        basicLists.get(index).setListTitle(name);
    }

    public boolean isEmpty() {
        return basicLists.size() == 0;
    }

    public BasicList getSpecificBasicList(int index) {
        isValid(0, basicLists.size() - 1, index);
        return basicLists.get(index);
    }

    public int getBasicListsSize() {
        return basicLists.size();
    }

    @Override
    public Iterator<BasicList> iterator() {
        return basicLists.iterator();
    }

}
