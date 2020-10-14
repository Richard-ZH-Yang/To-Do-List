package model;

public class CustomizedList extends BasicList {

    // MODIFIES: this
    // EFFECTS: change the list title
    public void renameList(String listTitle) {
        super.listTitle = listTitle;
    }
}
