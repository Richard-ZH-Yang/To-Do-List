package model;

import exceptions.InvalidDateException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private List<String> step;
    private String dueDay;
    private String createdDate;
    private String note;
    private boolean isImportant;
    private String todayDate;


    public Task(String title, String dueDay, String note, boolean isImportant)
            throws InvalidDateException {
        setTodayDate();
        setDueDay(dueDay);
        setCreatedDate();
        setTitle(title);
        step = new ArrayList<>();
        setNote(note);
        setImportant(isImportant);
    }

    public Task() throws InvalidDateException {
        this("", "", "", false);
    }

    public int decodeDate(String date) {
        // 8 digits, in the order of year month day, separate with -
        String[] dates = date.split("-");
        return Integer.parseInt(dates[0] + dates[1] + dates[2]);
    }

    public void setTodayDate() {
        ZoneId zonedId = ZoneId.of("America/Vancouver");
        LocalDate today = LocalDate.now(zonedId);
        todayDate = today.toString().substring(0, 10);
    }

    public void setDueDay(String dueDay) throws InvalidDateException {
        if (decodeDate(dueDay) < decodeDate(todayDate)) {
            throw new InvalidDateException();
        }
        this.dueDay = dueDay;
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

    public void setCreatedDate() {
        createdDate = todayDate;
    }






    public boolean isImportant() {
        return isImportant;
    }

    public List<String> getStep() {
        return step;
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
