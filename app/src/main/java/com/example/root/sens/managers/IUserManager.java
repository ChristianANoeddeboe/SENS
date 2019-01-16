package com.example.root.sens.managers;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.dao.interfaces.IUserDao;

import java.util.Date;
import java.util.Map;

public interface IUserManager {
    boolean isUser(String sensorID); // IS this ID already known, aka there is a user in Realm
    void updateGoal(ActivityCategories activityCategory, int newValue);
    Map<ActivityCategories, Integer> getGoals(Date date);
    Map<ActivityCategories, Float> getDayData(Date date);
    void getGoal(ActivityCategories activityCategory);
    boolean fulfilledAllGoals(Date date);
    boolean fulfilledGoal(ActivityCategories activityCategory, Date date);

}
