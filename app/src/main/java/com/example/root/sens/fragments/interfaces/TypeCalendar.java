package com.example.root.sens.fragments.interfaces;

public class TypeCalendar implements OverviewListItem {
    public TypeCalendar(){}

    @Override
    public int getListItemType() {
        return OverviewListItem.TYPE_CALENDAR;
    }
}
