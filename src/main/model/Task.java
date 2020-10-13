package model;

import exceptions.InvalidDateException;
import exceptions.InvalidIndexException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private List<String> step;
    private List<Boolean> isStepComplete;
    private String todayDate;
    private String dueDay;
    private String createdDate;
    private String note;
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
        setDueDay(todayDate);
        setCreatedDate();
        setTitle("");
        step = new ArrayList<>();
        isStepComplete = new ArrayList<>();
        setNote("");
        setImportant(false);
        isComplete = false;
        isVisible = true;
    }

    // MODIFIES: this
    // EFFECTS: add a step to the step list
    public void addStep(String stepTitle) {
        step.add(stepTitle);
        isStepComplete.add(false);
        assert (step.size() == isStepComplete.size());
    }

    // MODIFIES: this
    // EFFECTS: If index is invalid, throw InvalidIndexException. Otherwise remove the step
    //          and isStepComplete from the two lists based on the index. Index should start with 0
    public void deleteStep(int index) throws InvalidIndexException {
        if (index < 0 || step.size() < index - 1) {
            throw new InvalidIndexException("Index out of bound, there are " + step.size() + " elements. And"
                    + " index should start with 0");
        } else {
            step.remove(index);
            isStepComplete.remove(index);
            assert (step.size() == isStepComplete.size());
        }
    }

    // MODIFIES: this
    // EFFECTS: If index is invalid, throw InvalidIndexException. Otherwise, set the boolean in isStepComplete to true
    public void completeStep(int index) throws InvalidIndexException {
        assert (step.size() == isStepComplete.size());
        if (index < 0 || step.size() < index - 1) {
            throw new InvalidIndexException("Index out of bound, there are " + step.size() + " elements. And"
                                             + " index should start with 0");
        } else {
            isStepComplete.set(index, Boolean.TRUE);
        }
    }

    // MODIFIES: the parameter has to have the first 10 substrings in the format of "year-month-day", year month day
    //          numbers, and year has 4 digits, month and day have 2 digits
    // EFFECTS: return the date in String format to 8 digits integer
    public int decodeDate(String date) {
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

    // MODIFIES: this
    // EFFECTS: if the due date is 10 year away from today's date, it is an invalid date it will throw
    // InvalidDateException. if the due date is in the past, set isOverDue to true. Finally, set the due date.
    public void setDueDay(String dueDay) throws InvalidDateException {
        setTodayDate();
        int today = decodeDate(todayDate);
        int due = decodeDate(dueDay);
        int difference = today - due;
        if (difference > 100000 || difference < -100000) {
            throw new InvalidDateException("Invalid date: can only add due date within 10 years");
        } else {    // due date within 10 years:
            if (difference > 0) {
                isOverDue = true;
            }
            this.dueDay = dueDay;
        }
    }

    // MODIFIES: this
    // EFFECTS: set created date to today's date
    public void setCreatedDate() {
        createdDate = todayDate;
    }

    // normal setters methods

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

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

    public void setOverDue(boolean overDue) {
        isOverDue = overDue;
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
        return todayDate;
    }

}
