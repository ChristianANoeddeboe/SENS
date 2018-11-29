package com.example.root.sens.dto;

import com.example.root.sens.dto.ActivityCategories;

import io.realm.RealmObject;

public class Record extends RealmObject {
    private float value;
    private String type;
    public Record(){}
    public Record(float value, String type) {
        this.value = value;
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public ActivityCategories getType() {
        return ActivityCategories.valueOf(type);
    }

    public void setType(ActivityCategories type) {
        this.type = type.toString();
    }
}
