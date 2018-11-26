package com.example.root.sens.controllers;

import com.example.root.sens.UserObserver;
import com.example.root.sens.dto.SetGoalItemModel;

import java.util.Date;
import java.util.List;

public interface ILoginController {
    void save1(String firstMame, String lastName, Date birthday, UserObserver userObserver);
    void save2(List<SetGoalItemModel> Goal);
    void confirm();
}
