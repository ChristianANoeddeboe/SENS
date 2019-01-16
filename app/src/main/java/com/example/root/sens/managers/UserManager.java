package com.example.root.sens.managers;

import android.util.Log;

import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dao.interfaces.UserObserver;
import com.example.root.sens.dao.interfaces.IUserDao;
import com.example.root.sens.data;
import com.example.root.sens.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Sensor;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.RealmList;

public class UserManager implements IUserManager{
    private IUserDao userDao;
    private User user = null;
    private static final String TAG = UserManager.class.getSimpleName();
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
            switch(goal.getPrimaryTxt()){
                case "Cykling":
                    list.add(new Goal(ActivityCategories.Cykling.toString(),goal.getValue()));
                    break;
                case "Gang":
                    list.add(new Goal(ActivityCategories.Gang.toString(),goal.getValue()));
                    break;
                case "Træning":
                    list.add(new Goal(ActivityCategories.Træning.toString(),goal.getValue()));
                    break;
                case "Stå":
                    list.add(new Goal(ActivityCategories.Stå.toString(),goal.getValue()));
                    break;
                case "Søvn":
                    list.add(new Goal(ActivityCategories.Søvn.toString(),goal.getValue()));
                    break;
                default:
                    //TODO: Throw error
                    break;
            }
        }

        GoalHistory goalHistory = new GoalHistory();
        goalHistory.setGoals(list);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", new Locale("da"));
        String MM = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        String dd = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String yyyy = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        Log.d(TAG,MM+"/"+dd+"/"+yyyy);
        try {
            goalHistory.setDate(df.parse(MM+"/"+dd+"/"+yyyy));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RealmList<GoalHistory> goalHistories = new RealmList<>();

        goalHistories.add(goalHistory);

        user.setGoals(goalHistories);
        user.notifyObservers(User.GOALDATA);
        RealmList<DayData> daydata = new RealmList<>();
        user.setDayData(daydata);
    }

    public void saveUser(){
        userDao.saveUser(user);
        userDao.setUserLoggedIn(userDao.getUser(user.getSensors().get(0).getId()));
        data.initializeData();
    }

    public User getUser(String sensorID){
        return userDao.getUser(sensorID);
    }

    @Override
    public boolean isUser(String sensorID) {
        UserDAO dao = UserDAO.getInstance();
        if(dao.getUser(sensorID) == null){
            return false;
        }
        return true;
    }

    @Override
    public void updateGoal(ActivityCategories activityCategory, int newValue) {

    }

    @Override
    public Map<ActivityCategories, Integer> getGoals(Date date) {
        return null;
    }

    @Override
    public Map<ActivityCategories, Float> getDayData(Date date) {
        return null;
    }

    @Override
    public void getGoal(ActivityCategories activityCategory) {

    }

    @Override
    public boolean fulfilledAllGoals(Date date) {
        return false;
    }

    @Override
    public boolean fulfilledGoal(ActivityCategories activityCategory, Date date) {
        return false;
    }

    @Override
    public User getUserLoggedIn() {
        return null;
    }
}
