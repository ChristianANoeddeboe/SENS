package com.example.root.sens.dto;

public class Goal {
    private GoalType type;
    private int value;


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
