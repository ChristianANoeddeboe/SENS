package com.example.root.sens.controllers;

import com.example.root.sens.controllers.interfaces.ILoginController;
import com.example.root.sens.dto.UserObserver;
import com.example.root.sens.dto.User;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.ui_layer.recyclers.itemmodels.SetGoalItemModel;

import java.util.Date;
import java.util.List;

public class LoginController implements ILoginController {
    private IUserManager userManager = new UserManager();

    @Override
    public boolean login(String patientKey) {
        return userManager.isUser(patientKey);
    }

    @Override
    public void save1(String firstName, String lastName, Date birthday, String patientKey, UserObserver userObserver) {
        userManager.createUser(new User(firstName, lastName, birthday), patientKey, userObserver);
    }

    @Override
    public void save2(List<SetGoalItemModel> goal) {
        userManager.createGoals(goal);
    }

    @Override
    public void confirm() {
        userManager.saveUser();
    }

}
