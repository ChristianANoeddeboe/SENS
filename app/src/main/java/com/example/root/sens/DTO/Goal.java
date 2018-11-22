package com.example.root.sens.DTO;

import java.util.Date;

import io.realm.RealmObject;

public class Goal extends RealmObject {
    private ActivityCategories type;
    private int value;

    public Goal(){}
    public Goal(ActivityCategories type, int value) {
        this.type = type;
        this.value = value;
    }


    public ActivityCategories getType() {
        return type;
    }

    public void setType(ActivityCategories type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
