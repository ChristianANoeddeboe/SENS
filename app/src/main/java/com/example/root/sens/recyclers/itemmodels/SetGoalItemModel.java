package com.example.root.sens.recyclers.itemmodels;

public class SetGoalItemModel {
    private int type;
    private String primaryTxt;
    private int value;

    public SetGoalItemModel(int type, String primaryTxt, int value){
        this.type = type;
        this.primaryTxt = primaryTxt;
        this.value = value;
    }

    public int getType() {
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
