package com.example.root.sens.controllers;

import com.example.root.sens.UserManager;
import com.example.root.sens.UserObserver;
import com.example.root.sens.dao.UserDao;
import com.example.root.sens.dto.SetGoalItemModel;
import com.example.root.sens.dto.User;

import java.util.Date;
import java.util.List;

public class LoginController implements ILoginController {
    UserManager userManager = new UserManager(new UserDao());

    @Override
    public boolean isUser(String sensorID) {
        userManager.getUser(sensorID);
        return false;
    }

    @Override
    public void save1(String firstName, String lastName, Date birthday, String sensorID, UserObserver userObserver) {
        userManager.createUser(new User(firstName, lastName, birthday), sensorID, userObserver);
    }

    @Override
    public void save2(List<SetGoalItemModel> Goal) {
        userManager.createGoals(Goal);
    }

    @Override
    public void confirm() {
        userManager.saveUser();
    }

}
