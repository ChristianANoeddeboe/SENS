package com.example.root.sens.view_layer.recyclers.itemmodels;

import com.example.root.sens.ActivityCategories;

public class SetGoalItemModel {
    private ActivityCategories type;
    private String primaryTxt;
    private int value;

    public SetGoalItemModel(ActivityCategories type, String primaryTxt, int value){
        this.type = type;
        this.primaryTxt = primaryTxt;
        this.value = value;
    }

    public ActivityCategories getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPrimaryTxt() {
        return primaryTxt;
    }

}
