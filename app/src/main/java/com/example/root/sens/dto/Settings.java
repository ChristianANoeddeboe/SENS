package com.example.root.sens.dto;

import io.realm.RealmObject;

public class Settings extends RealmObject {
    private User loggedInUser;

    public Settings() {
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
