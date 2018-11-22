package com.example.root.sens.DTO;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GoalType extends RealmObject {
    @PrimaryKey
    private int typeId;
    private String name;
    public GoalType(){}
    public GoalType(int typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
