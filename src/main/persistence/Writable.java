package persistence;

import org.json.JSONObject;

// reference:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    //EFFECTS: return as a JSON object
    JSONObject toJson();
}
