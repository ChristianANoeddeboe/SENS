package com.example.root.sens.recyclers.itemmodels;

public class SetGoalItemModel {
    private String primaryTxt;
    private int value;

    public SetGoalItemModel(String primaryTxt, int value){
        this.primaryTxt = primaryTxt;
        this.value = value;
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
