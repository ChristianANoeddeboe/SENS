package com.example.root.sens.dto;

import com.example.root.sens.dto.ActivityCategories;

import io.realm.RealmObject;

public class Record extends RealmObject {
    private int value;
    private String type;
    public Record(){}
    public Record(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ActivityCategories getType() {
        return ActivityCategories.valueOf(type);
    }

    public void setType(ActivityCategories type) {
        this.type = type.toString();
    }
}
