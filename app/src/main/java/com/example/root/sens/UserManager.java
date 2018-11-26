package com.example.root.sens;

import com.example.root.sens.dao.IUserDao;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.SetGoalItemModel;
import com.example.root.sens.dto.User;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class UserManager {
    IUserDao userDao;
    private User user = null;

    public UserManager(IUserDao userDao){
        this.userDao = userDao;
    }

    public void createUser(User user, UserObserver userObserver){
        this.user = user;
        user.addObserver(userObserver);
        user.notifyObservers(User.USERDATA);
    }

    public void createGoals(List<SetGoalItemModel> goals){
        RealmList<Goal> list = new RealmList<>();
        for(SetGoalItemModel goal : goals){
            list.add(new Goal(goal.getPrimaryTxt(), goal.getValue()));
        }

        GoalHistory goalHistory = new GoalHistory();
        goalHistory.setGoals(list);

        RealmList<GoalHistory> goalHistories = new RealmList<>();

        goalHistories.add(goalHistory);

        user.setGoals(goalHistories);
        user.notifyObservers(User.GOALDATA);
    }

    public void saveUser(){
        userDao.saveUser(user);
    }
}
