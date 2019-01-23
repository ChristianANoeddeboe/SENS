package com.example.root.sens.dto;

import com.example.root.sens.ActivityCategories;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Goal extends RealmObject {
    @Required
    private String type;
    private int value;

    public Goal(){}

    public Goal(String type, int value) {
        this.type = type;
        this.value = value;
    }


    public ActivityCategories getType() throws NullPointerException {
        if(type == null){
            throw new NullPointerException("GoalType was null!");
        }
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
