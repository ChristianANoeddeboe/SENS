package com.example.root.sens.dao.interfaces;

import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.User;

import java.util.Date;
import java.util.HashMap;

public interface IUserDao {
    void setUserLoggedIn(User user);

    void removeUserLoggedIn(User user);

    void saveUser(User user);
    User getUserLoggedIn();
    User getUser(String sensorId);
    GoalHistory getNewestGoal();
    HashMap<Date,Boolean> userFulfilledGoals();
}
