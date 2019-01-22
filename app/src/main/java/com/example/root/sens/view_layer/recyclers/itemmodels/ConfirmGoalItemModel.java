package com.example.root.sens.view_layer.recyclers.itemmodels;

public class ConfirmGoalItemModel {
    private String description;
    private String value;
    private int type;

    public ConfirmGoalItemModel(String description, String value, int type){
        this.description = description;
        this.value = value;
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
