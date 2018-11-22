package com.example.root.sens.DTO;

import java.util.Date;

import io.realm.RealmObject;

public class Goal extends RealmObject {
    private GoalType type;
    private int value;

    public Goal(){}
    public Goal(GoalType type, int value) {
        this.type = type;
        this.value = value;
    }


    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
