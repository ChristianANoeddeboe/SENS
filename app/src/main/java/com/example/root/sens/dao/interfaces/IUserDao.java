package com.example.root.sens.dao.interfaces;

import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface IUserDao {
    void setUserLoggedIn(User user);

    void removeUserLoggedIn(User user);
    void saveUser(User user);
    User getUserLoggedIn();
    User getUser(String sensorId);
    GoalHistory getNewestGoal();
    ArrayList<DayData> getSortedDayData();
    HashMap<Date,Boolean> userFulfilledGoals();
    DayData getDataSpecificDate(Date d);
    GoalHistory getGoalSpecificDate(Date d);
    void updateOrMergeGoals(HashMap<String,Integer> temp);
}
