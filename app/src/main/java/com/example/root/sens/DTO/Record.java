package com.example.root.sens.DTO;

import io.realm.RealmObject;

public class Record extends RealmObject {
    private int value;
    private ActivityCategories type;
    public Record(){}
    public Record(int value, ActivityCategories type) {
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
        return type;
    }

    public void setType(ActivityCategories type) {
        this.type = type;
    }
}
