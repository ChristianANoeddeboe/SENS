package com.example.root.sens.controllers.interfaces;

import com.example.root.sens.dao.interfaces.UserObserver;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;

import java.util.Date;
import java.util.List;

public interface ILoginController {
    boolean login(String patientKey);
    void save1(String firstMame, String lastName, Date birthday, String patientKey, UserObserver userObserver);
    void save2(List<SetGoalItemModel> Goal);
    void confirm();
}
