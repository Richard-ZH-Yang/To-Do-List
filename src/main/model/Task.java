package model;

import exceptions.InvalidDateException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
// hi

// Represents task inside the basicList, Task class stores different properties for each task
public class Task implements Writable {
    public static String todayDate;   // using JAVA's library to obtain. Time zone is America/Vancouver
    private String title;
    private List<String> step;      // the step title list
    private List<Boolean> isStepComplete;   // list of status for each step, true means step is complete
    private String dueDay;      // can be set within 10 years of today's date
    private String createdDate; // equal to today's date when created, difference is that today's date can update
    private String note;        // note for each tasks
    private boolean isImportant;
    private boolean isComplete;
    private boolean isVisible;
    private boolean isOverDue;

    // constructor
    // EFFECTS: construct a new Task, set title to parameter String
    //          set todayDate to today's date, dueDay to "NA", createdDate to todayDate,
    //          initialize step and isStepComplete, set note to "NA", set isImportant to false
    //          set isComplete to false, set isVisible to true. Set isOverDue to true if dueDay is smaller than
    //          todayDate, the last operation is done inside setDueDay()
    public Task(String title) throws InvalidDateException {
        this();
        this.title = title;
    }

    // constructor
    // EFFECTS: construct a new Task, set todayDate to today's date, dueDay to "NA", createdDate to todayDate,
    //          title to "", initialize step and isStepComplete, set note to "NA", set isImportant to false
    //          set isComplete to false, set isVisible to true. Set isOverDue to true if dueDay is smaller than
    //          todayDate, this operation is done inside setDueDay()
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

    // EFFECTS: Convert Task to a JSONObject
    @Override
    public JSONObject toJson() {
        // not for todayDate
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("step", stepToJson());
        json.put("isStepComplete", isStepCompleteToJson());
        json.put("dueDay", dueDay);
        json.put("createdDate", createdDate);
        json.put("note", note);
        json.put("isImportant", isImportant);
        json.put("isComplete", isComplete);
        json.put("isVisible", isVisible);
        json.put("isOverDue", isOverDue);
        return json;
    }

    // EFFECTS: return step list as a JSONObject
    private JSONObject stepToJson() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        int i = 0;
        for (String steps: step) {
            jsonObject.put(Integer.toString(i), steps);
            //jsonArray.put(i, jsonObject);
            i++;
        }

        return jsonObject;
    }

    // EFFECTS: return isStepComplete list as a JSONObject
    private JSONObject isStepCompleteToJson() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        int i = 0;
        for (Boolean status: isStepComplete) {
            jsonObject.put(Integer.toString(i), status);
            //jsonArray.put(i, jsonObject);
            i++;
        }

        return jsonObject;
    }


    // MODIFIES: this
    // EFFECTS: add a step to the step list
    public void addStep(String stepTitle) {
        step.add(stepTitle);
        isStepComplete.add(false);
        //assert (step.size() == isStepComplete.size());
    }

    // MODIFIES: this
    // EFFECTS: If index is invalid, throw IndexOutOfBoundsException. Otherwise remove the step
    //          and isStepComplete from the two lists based on the index. Index should start with 0
    public void deleteStep(int index) {
        if (index < 0 || step.size() <= index) {
            throw new IndexOutOfBoundsException("Index out of bound, there are " + step.size()
                    + " elements in the list. "
                    + "And index should start with 0");
        } else {
            step.remove(index);
            isStepComplete.remove(index);
            //assert (step.size() == isStepComplete.size());
        }
    }

    // MODIFIES: this
    // EFFECTS: If index is invalid, throw IndexOutOfBoundsException. Otherwise, set the boolean in isStepComplete to
    //          true
    public void completeStep(int index) {
        //assert (step.size() == isStepComplete.size());
        if (index < 0 || step.size() <= index) {
            throw new IndexOutOfBoundsException("Index out of bound, there are " + step.size()
                    + " elements in the list. "
                    + "And index should start with 0");
        } else {
            isStepComplete.set(index, Boolean.TRUE);
        }
    }

    // MODIFIES: this
    // EFFECTS: If index is invalid, throw IndexOutOfBoundsException. Otherwise, set the boolean in isStepComplete to
    //          false
    public void inCompleteStep(int index) {
        //assert (step.size() == isStepComplete.size());
        if (index < 0 || step.size() <= index) {
            throw new IndexOutOfBoundsException("Index out of bound, there are " + step.size()
                    + " elements in the list. "
                    + "And index should start with 0");
        } else {
            isStepComplete.set(index, Boolean.FALSE);
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
    public static void setTodayDate() {
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
    // EFFECTS: toggle the isImportant boolean field
    public void toggleImportance() {
        isImportant = !isImportant;
    }

    // MODIFIES: this
    // EFFECTS: set created date to today's date
    public void setCreatedDate() {
        createdDate = todayDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    // MODIFIES: this
    // EFFECTS: set overDue to true if it todayDate greater than dueDay
    public void setOverDue() {
        isOverDue = (decodeDate(todayDate) - decodeDate(dueDay))  > 0;
    }

    // normal setters methods

    public void setOverDue(boolean overDue) {
        isOverDue = overDue;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

//    public void setImportant(String isImportant) {
//        if (!(isImportant.equals("true") || isImportant.equals("false"))) {
//            throw new IllegalArgumentException("must be true or false");
//        }
//        this.isImportant = Boolean.parseBoolean(isImportant);
//    }

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

//    public void setComplete(String isComplete) {
//        if (!(isComplete.equals("true") || isComplete.equals("false"))) {
//            throw new IllegalArgumentException("must be true or false");
//        }
//        this.isComplete = Boolean.parseBoolean(isComplete);
//    }

    // EFFECTS: initialize todayDate first, then return todayDate
    public String getTodayDate() {
        setTodayDate();
        return todayDate;
    }

    // normal getters methods

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
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        Task task = (Task) o;
//        return isImportant == task.isImportant
//                && isComplete == task.isComplete
//                && isVisible == task.isVisible
//                && isOverDue == task.isOverDue
//                && Objects.equals(title, task.title)
//                && Objects.equals(step, task.step)
//                && Objects.equals(isStepComplete, task.isStepComplete)
//                && Objects.equals(dueDay, task.dueDay)
//                && Objects.equals(createdDate, task.createdDate)
//                && Objects.equals(note, task.note);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(title, step, isStepComplete, dueDay, createdDate, note, isImportant, isComplete,
//                isVisible, isOverDue);
//    }
}
