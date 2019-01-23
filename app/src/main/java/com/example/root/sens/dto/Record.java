package com.example.root.sens.dto;

import com.example.root.sens.ActivityCategories;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Record extends RealmObject {
    private float value;
    @Required
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
