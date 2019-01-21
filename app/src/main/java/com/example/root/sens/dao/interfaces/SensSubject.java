package com.example.root.sens.dao.interfaces;

public interface SensSubject {
    void registerObserver(SensObserver sensObserver);
    void removeObserver(SensObserver sensObserver);
    void notifyObservers(boolean b);
}
