package model;

import exceptions.InvalidDateException;
import exceptions.InvalidIndexException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

// Task class collects different properties for each task
public class Task implements Writable {
    private String title;
    private List<String> step;      // the step title list
    private List<Boolean> isStepComplete;   // list of status for each step, true means step is complete
    private String todayDate;   // using JAVA's library to obtain. Time zone is America/Vancouver
    private String dueDay;      // can be set within 10 years of today's date
    private String createdDate; // equal to today's date when created, difference is that today's date can update
    private String note;        // note for each tasks
    private boolean isImportant;
    private boolean isComplete;
    private boolean isVisible;
    private boolean isOverDue;

    public Task(String title) throws InvalidDateException {
        this();
        this.title = title;
    }

    public Task() throws InvalidDateException {
        setTodayDate();
        setDueDay("NA");
        setCreatedDate();
        setTitle("");
        step = new ArrayList<>();
        isStepComplete = new ArrayList<>();
        setNote("NA");
        setImportant(false);
        isComplete = false;
        isVisible = true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("step", stepToJson());
        json.put("isStepComplete", isStepCompleteToJson());
        json.put("todayDate", todayDate);
        json.put("dueDay", dueDay);
        json.put("createdDate", createdDate);
        json.put("note", note);
        json.put("isImportant", isImportant);
        json.put("isComplete", isComplete);
        json.put("isVisible", isVisible);
        json.put("isOverDue", isOverDue);
        return json;
    }

    // EFFECTS: return step list as a JSON array
    private JSONArray stepToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String step: step) {
            jsonArray.put(step);
        }

        return jsonArray;
    }

    // EFFECTS: return isStepComplete list as a JSON array
    private JSONArray isStepCompleteToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Boolean status: isStepComplete) {
            jsonArray.put(status.toString());
        }

        return jsonArray;
    }

    // EFFECTS: compare every element in the task to other task objects
    public boolean equals(Task task2) {
        boolean isEqual = false;

    }

    // MODIFIES: this
    // EFFECTS: add a step to the step list
    public void addStep(String stepTitle) {
        step.add(stepTitle);
        isStepComplete.add(false);
        //assert (step.size() == isStepComplete.size());
    }

    // MODIFIES: this
    // EFFECTS: If index is invalid, throw InvalidIndexException. Otherwise remove the step
    //          and isStepComplete from the two lists based on the index. Index should start with 0
    public void deleteStep(int index) throws InvalidIndexException {
        if (index < 0 || step.size() <= index) {
            throw new InvalidIndexException("Index out of bound, there are " + step.size() + " elements in the list. "
                    + "And index should start with 0");
        } else {
            step.remove(index);
            isStepComplete.remove(index);
            //assert (step.size() == isStepComplete.size());
        }
    }

    // MODIFIES: this
    // EFFECTS: If index is invalid, throw InvalidIndexException. Otherwise, set the boolean in isStepComplete to true
    public void completeStep(int index) throws InvalidIndexException {
        //assert (step.size() == isStepComplete.size());
        if (index < 0 || step.size() <= index) {
            throw new InvalidIndexException("Index out of bound, there are " + step.size() + " elements in the list. "
                    + "And index should start with 0");
        } else {
            isStepComplete.set(index, Boolean.TRUE);
        }
    }

    // REQUIRES: parameter date has at least 8 digits, and it is positive
    // EFFECTS: return a String date in the format of "xxxx-xx-xx", year month day.
    public static String parseDate(int date) {
        String whole = String.valueOf(date);
        char[] eachChar = whole.toCharArray();
        String newWord = "";
        for (int i = 0; i < 8; i++) {
            newWord += eachChar[i];
            if (i == 3 || i == 5) {
                newWord += "-";
            }
        }
        return newWord;
    }

    // REQUIRES: parameter date is in the format of year-month-day. year 4 digits, month and day have 2 digits. And the
    //          total length for the string parameter is 10
    // MODIFIES: the parameter has to have the first 10 substrings in the format of "year-month-day", year month day
    //          numbers, and year has 4 digits, month and day have 2 digits
    // EFFECTS: return the date in String format to 8 digits integer
    public static int decodeDate(String date) {
        // 8 digits, in the order of year month day, separate with -
        String[] dates = date.split("-");
        return Integer.parseInt(dates[0] + dates[1] + dates[2]);
    }

    // MODIFIES: this
    // set today's date to the date in Vancouver timezone with String format like "2020-10-13"
    public void setTodayDate() {
        ZoneId zonedId = ZoneId.of("America/Vancouver");
        LocalDate today = LocalDate.now(zonedId);
        todayDate = today.toString().substring(0, 10);
    }

    // REQUIRES: parameter should be "NA" or a reasonable date. By reasonable,
    //          it means cannot add date such as 2020-02-31. As February does not have 31th day.
    // MODIFIES: this
    // EFFECTS: Set due date to "NA" only if dueDay parameter is exactly "NA".
    //          Otherwise, if the due date is 10 year away from today's date, it is an invalid date it will throw
    //          InvalidDateException. if the due date is in the past, set isOverDue to true. Finally, set the due date.
    public void setDueDay(String dueDay) throws InvalidDateException {
        if (dueDay.equals("NA")) {
            this.dueDay = "NA";
            isOverDue = false;
        } else {
            setTodayDate();
            int today = decodeDate(todayDate);
            int due = decodeDate(dueDay);
            int difference = today - due;
            if (difference > 100000 || difference < -100000) {
                throw new InvalidDateException("Invalid date: can only add due date within 10 years");
            } else {    // due date valid:
                isOverDue = difference > 0;
                this.dueDay = dueDay;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: set created date to today's date
    public void setCreatedDate() {
        createdDate = todayDate;
    }




    // normal setters methods

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }


    // getters methods

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isOverDue() {
        return isOverDue;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public List<String> getStep() {
        return step;
    }

    public List<Boolean> getIsStepComplete() {
        return isStepComplete;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getDueDay() {
        return dueDay;
    }

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public String getTodayDate() {
        setTodayDate();
        return todayDate;
    }
}
