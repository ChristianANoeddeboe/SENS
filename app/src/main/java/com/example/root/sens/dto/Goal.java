package com.example.root.sens.dto;

import io.realm.RealmObject;

public class Goal extends RealmObject {
    private String type;
    private int value;

    public Goal(){}
    public Goal(String type, int value) {
        this.type = type;
        this.value = value;
    }


    public ActivityCategories getType() {
        return ActivityCategories.valueOf(type);
    }

    public void setType(ActivityCategories type) {
        this.type = type.toString();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
