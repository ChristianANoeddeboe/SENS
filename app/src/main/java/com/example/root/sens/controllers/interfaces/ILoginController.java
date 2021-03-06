package com.example.root.sens.controllers.interfaces;

import com.example.root.sens.dto.UserObserver;
import com.example.root.sens.ui_layer.recyclers.itemmodels.SetGoalItemModel;

import java.util.Date;
import java.util.List;

public interface ILoginController {
    boolean login(String patientKey);
    void save1(String firstName, String lastName, Date birthday, String patientKey, UserObserver userObserver);
    void save2(List<SetGoalItemModel> goal);
    void confirm();
}