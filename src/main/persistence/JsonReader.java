package persistence;

import exceptions.InvalidDateException;
import exceptions.ListFullException;
import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads ToDoListProgram from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ToDoListProgram from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoListProgram read() throws IOException, ListFullException, InvalidDateException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoListProgram(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ToDoListProgram from JSON object and returns it
    private ToDoListProgram parseToDoListProgram(JSONObject jsonObject) throws InvalidDateException, ListFullException {
        ToDoListProgram toDoListProgram = new ToDoListProgram();
        toDoListProgram.getDefaultList().clear();

        toDoListProgram.setEndProgram(false);
        JSONArray defaultListArray = jsonObject.getJSONArray("defaultList");
        JSONArray customizedListArray = jsonObject.getJSONArray("customizedList");

        for (Object inDefaultList : defaultListArray) {
            JSONObject nextBasicList = (JSONObject) inDefaultList;
            addBasicList(toDoListProgram.getDefaultList(), nextBasicList);
        }


        for (Object inCustomizedList : customizedListArray) {
            JSONObject nextBasicList = (JSONObject) inCustomizedList;
            addBasicList(toDoListProgram.getCustomizedList(), nextBasicList);
        }

        return toDoListProgram;
    }

    // MODIFIES: toDoListProgram
    // EFFECTS: parses BasicList from JSON object and adds them to List<BasicList>
    private void addBasicList(List<BasicList> basicListList, JSONObject basicListJson) throws ListFullException,
            InvalidDateException {
        BasicList basicList = new BasicList();
        basicList.setListTitle(basicListJson.getString("listTitle"));
        basicList.setVisible(basicListJson.getBoolean("isVisible"));
        JSONArray taskListArray = basicListJson.getJSONArray("taskList");
        JSONArray completedTaskList = basicListJson.getJSONArray("completedTaskList");

        for (Object inTaskList : taskListArray) {
            JSONObject nextTask = (JSONObject) inTaskList;
            addTask(basicList, nextTask);
        }
        for (Object inCompletedTaskList : completedTaskList) {
            JSONObject nextTask = (JSONObject) inCompletedTaskList;
            addTask(basicList, nextTask);
        }
        basicListList.add(basicList);

    }

    // MODIFIES: toDoListProgram
    // EFFECTS: parses Task from JSON object and adds it to BasicList
    private void addTask(BasicList basicList, JSONObject taskJson) throws InvalidDateException, ListFullException {
        Task task = new Task();
        task.setTitle(taskJson.getString("title"));
        task.setDueDay(taskJson.getString("dueDay"));
        task.setCreatedDate(taskJson.getString("createdDate"));
        task.setNote(taskJson.getString("note"));
        task.setImportant(taskJson.getBoolean("isImportant"));
        task.setComplete(taskJson.getBoolean("isComplete"));
        task.setVisible(taskJson.getBoolean("isVisible"));
        task.setOverDue(taskJson.getBoolean("isOverDue"));


        JSONObject stepJson = taskJson.getJSONObject("step");
        JSONObject isStepCompleteJson = taskJson.getJSONObject("isStepComplete");

        for (int i = 0; i < stepJson.length(); i++) {
            addStep(task, stepJson, i);
            addIsStepComplete(task, isStepCompleteJson, i);
        }


        basicList.addTask(task);
    }

    // MODIFIES: toDoListProgram
    // EFFECTS: parses Step from JSON object and adds it to Task
    private void addStep(Task task, JSONObject stepJson, Integer index) {
        task.addStep(stepJson.getString(index.toString()));
    }

    // MODIFIES: toDoListProgram
    // EFFECTS: parses IsStepComplete from JSON object and adds it to Task
    private void addIsStepComplete(Task task, JSONObject isStepCompleteJson, Integer index) {
        boolean hasDone = isStepCompleteJson.getBoolean(index.toString());
        if (hasDone) {
            task.completeStep(index);
        } else {
            task.inCompleteStep(index);
        }
    }

}
