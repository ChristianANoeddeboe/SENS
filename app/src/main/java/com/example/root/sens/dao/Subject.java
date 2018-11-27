package com.example.root.sens.dao;

public interface Subject {
    void registerObserver(SensObserver sensObserver);
    void removeObserver(SensObserver sensObserver);
    void notifyObservers();
}
