package com.example.root.sens.recyclers.adapters.itemmodels;

public class ConfirmGoalItemModel {
    private String description;
    private String value;

    public ConfirmGoalItemModel(String description, String value){
        this.description = description;
        this.value = value;
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
}
