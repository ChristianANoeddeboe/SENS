package com.example.root.sens.dto;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Settings extends RealmObject {
    private User loggedInUser;
    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    public Settings() {
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
