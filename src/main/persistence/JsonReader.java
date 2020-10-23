//package persistence;
//
//import model.*;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.stream.Stream;
//
//import org.json.*;
//
//// reference:https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//// Represents a reader that reads BasicList from JSON data stored in file
//public class JsonReader {
//    private String source;
//
//    // EFFECTS: constructs reader to read from source file
//    public JsonReader(String source) {
//        this.source = source;
//    }
//
//    // EFFECTS: reads workroom from file and returns it;
//    // throws IOException if an error occurs reading data from file
//    public BasicList read() throws IOException {
//        String jsonData = readFile(source);
//        JSONObject jsonObject = new JSONObject(jsonData);
//        return parseBasicList(jsonObject);
//    }
//
//    // EFFECTS: reads source file as string and returns it
//    private String readFile(String source) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//
//        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
//            stream.forEach(s -> contentBuilder.append(s));
//        }
//
//        return contentBuilder.toString();
//    }
//
//    // EFFECTS: parses BasicList from JSON object and returns it
//    private BasicList parseBasicList(JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        BasicList basicList = new BasicList();
//        basicList.setListTitle(name);
//        addThingies(basicList, jsonObject);
//        return basicList;
//    }
//
//    // MODIFIES: wr
//    // EFFECTS: parses thingies from JSON object and adds them to workroom
//    private void addThingies(BasicList basicList, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("thingies");
//        for (Object json : jsonArray) {
//            JSONObject nextThingy = (JSONObject) json;
//            addTask(wr, nextThingy);
//        }
//    }
//
//    // MODIFIES: wr
//    // EFFECTS: parses thingy from JSON object and adds it to workroom
//    private void addTask(BasicList basicList, JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        Category category = Category.valueOf(jsonObject.getString("category"));
//        Thingy thingy = new Thingy(name, category);
//        wr.addThingy(thingy);
//    }
//}
