package com.example.root.sens.dao.interfaces;


public interface DatabaseSubject {
    void registerObserver(DatabaseObserver databaseObserver);
    void removeObserver(DatabaseObserver databaseObserver);
    void notifyObservers();
}
