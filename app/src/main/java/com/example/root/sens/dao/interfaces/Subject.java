package com.example.root.sens.dao.interfaces;

import com.example.root.sens.dto.sensresponse.Response;

public interface Subject {
    void registerObserver(SensObserver sensObserver);
    void removeObserver(SensObserver sensObserver);
    void notifyObservers(Response r);
}