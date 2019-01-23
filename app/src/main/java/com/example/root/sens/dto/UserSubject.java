package com.example.root.sens.dto;

public interface UserSubject {
    public void notifyObservers(String tag);

    public void addObserver(UserObserver userObserver);

    public void removeObserver(UserObserver userObserver);
}
