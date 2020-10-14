package model;

public class CustomizedList extends BasicList {

    public CustomizedList(String listTitle) {
        super();
        super.setListTitle(listTitle);
    }

    public CustomizedList() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: change the list title
    public void setListTitle(String listTitle) {
        super.listTitle = listTitle;
    }
}
