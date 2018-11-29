package com.example.root.sens.recyclers.itemmodels;

public class SetGoalItemModel {
    private String primaryTxt;
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public SetGoalItemModel(String primaryTxt){
        this.primaryTxt = primaryTxt;
    }

    public String getPrimaryTxt() {
        return primaryTxt;
    }
}
