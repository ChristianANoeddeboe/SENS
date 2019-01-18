package com.example.root.sens.dao.interfaces;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface IUserDao {
    void setUserLoggedIn(User user);
    void removeUserLoggedIn(User user);
    void saveUser(User user);
    User getUserLoggedIn();
    User getUser(String patientKey);
    GoalHistory getNewestGoal();
    ArrayList<DayData> getSortedDayData();
    DayData getDataSpecificDate(Date d);
    GoalHistory getGoalSpecificDate(Date d);
    void updateOrMergeGoals(Map<ActivityCategories,Integer> temp);
}
