package com.example.root.sens.dto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sensor extends RealmObject {
    @PrimaryKey
    private String id;
    public Sensor(){}
    public Sensor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
