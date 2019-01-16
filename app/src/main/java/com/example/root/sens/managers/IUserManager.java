package com.example.root.sens.managers;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.dao.interfaces.IUserDao;
import com.example.root.sens.dao.interfaces.UserObserver;
import com.example.root.sens.dto.User;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IUserManager {
    boolean isUser(String sensorID); // IS this ID already known, aka there is a user in Realm
    void updateGoal(ActivityCategories activityCategory, int newValue);
    Map<ActivityCategories, Integer> getGoals(Date date);
    Map<ActivityCategories, Float> getDayData(Date date);
    void getGoal(ActivityCategories activityCategory);
    boolean fulfilledAllGoals(Date date);
    boolean fulfilledGoal(ActivityCategories activityCategory, Date date);

    void createUser(User user, String sensorID, UserObserver userObserver);
    void createGoals(List<SetGoalItemModel> goals);
    void saveUser();
}
