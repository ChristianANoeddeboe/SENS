package com.example.root.sens.controllers;

import com.example.root.sens.UserManager;
import com.example.root.sens.UserObserver;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.SetGoalItemModel;
import com.example.root.sens.dto.User;

import java.util.Date;
import java.util.List;

public class LoginController implements ILoginController {
    UserManager userManager = new UserManager(UserDAO.getInstance());

    @Override
    public boolean isUser(String sensorID) {
        if(userManager.getUser(sensorID) != null){
            return true;
        }
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
