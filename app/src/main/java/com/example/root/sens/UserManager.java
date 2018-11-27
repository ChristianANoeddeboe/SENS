package com.example.root.sens;

import com.example.root.sens.dao.IUserDao;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Sensor;
import com.example.root.sens.dto.SetGoalItemModel;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class UserManager {
    IUserDao userDao;
    private User user = null;

    public UserManager(IUserDao userDao){
        this.userDao = userDao;
    }

    public void createUser(User user, String sensorID, UserObserver userObserver){
        this.user = user;
        user.setSensors(new RealmList<>(new Sensor(sensorID)));
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
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String MM = Integer.toString(Calendar.getInstance().get(Calendar.MONTH));
        String dd = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String yyyy = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        try {
            goalHistory.setDate(df.parse(MM+"/"+dd+"/"+yyyy));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RealmList<GoalHistory> goalHistories = new RealmList<>();

        goalHistories.add(goalHistory);

        user.setGoals(goalHistories);
        user.notifyObservers(User.GOALDATA);
    }

    public void saveUser(){
        userDao.saveUser(user);
        userDao.setUserLoggedIn(user);
    }

    public User getUser(String sensorID){
        return userDao.getUser(sensorID);
    }
}
