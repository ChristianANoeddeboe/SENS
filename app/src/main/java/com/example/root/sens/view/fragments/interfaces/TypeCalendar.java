package com.example.root.sens.view.fragments.interfaces;

public class TypeCalendar implements ListItem {
    private String text;

    public TypeCalendar() { this.text = text; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    @Override
    public int getListItemType() {
        return ListItem.TYPE_A;
    }
}
