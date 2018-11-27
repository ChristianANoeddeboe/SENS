package com.example.root.sens.controllers;

import com.example.root.sens.UserObserver;
import com.example.root.sens.recyclers.adapters.itemmodels.SetGoalItemModel;

import java.util.Date;
import java.util.List;

public interface ILoginController {
    boolean isUser(String sensorID);
    void save1(String firstMame, String lastName, Date birthday, String sensorID, UserObserver userObserver);
    void save2(List<SetGoalItemModel> Goal);
    void confirm();
}