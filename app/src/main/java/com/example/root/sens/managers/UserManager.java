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
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.Sensor;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.RealmList;

public class UserManager implements IUserManager{
    private static final String TAG = UserManager.class.getSimpleName();
    private User user = null;

    public UserManager(){
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
        UserDAO userDao = UserDAO.getInstance();
        userDao.saveUser(user);
        userDao.setUserLoggedIn(userDao.getUser(user.getSensors().get(0).getId()));
        data.initializeData();
    }

    public User getUser(String sensorID){
        return UserDAO.getInstance().getUser(sensorID);
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
        Map<ActivityCategories, Integer> result = new HashMap<>();
        UserDAO dao = UserDAO.getInstance();
        result = generateGoalMap(dao.getNewestGoal());

        result.replace(activityCategory, newValue);
        dao.updateOrMergeGoals(result);

    }

    @Override
    public Map<ActivityCategories, Integer> getGoals(Date date) {
        UserDAO dao = UserDAO.getInstance();
        return generateGoalMap(dao.getGoalSpecificDate(date));
    }

    @Override
    public Map<ActivityCategories, Float> getDayData(Date date) {
        Map<ActivityCategories, Float> result = new HashMap<>();
        UserDAO dao = UserDAO.getInstance();
        DayData data = dao.getDataSpecificDate(date);
        RealmList<Record> records = data.getRecords();

        for(Record rec : records){
            result.put(rec.getType(), rec.getValue());
        }

        return result;
    }

    @Override
    public void getGoal(ActivityCategories activityCategory) {
        //TODO implemented
    }

    @Override
    public boolean fulfilledAllGoals(Date date) {
        UserDAO dao = UserDAO.getInstance();
        GoalHistory goalHistory = dao.getGoalSpecificDate(date);
        RealmList<Goal> goals = goalHistory.getGoals();
        DayData dayData  = dao.getDataSpecificDate(date);
        RealmList<Record> records = dayData.getRecords();
        Record currentRecord = null;

        for(Goal goal : goals){
            for(Record rec : records) {
                if (goal.getType() == rec.getType()) {
                    currentRecord = rec;
                    break;
                }
            }
            if(currentRecord.getValue() < goal.getValue()) return false;
        }
        return true;
    }

    @Override
    public boolean fulfilledGoal(ActivityCategories activityCategory, Date date) {
        return false;
    }

    @Override
    public User getUserLoggedIn() {
        return UserDAO.getInstance().getUserLoggedIn();
    }

    private Map<ActivityCategories, Integer> generateGoalMap(GoalHistory goalHistory){
        Map<ActivityCategories, Integer> result = new HashMap<>();
        RealmList<Goal> goals = goalHistory.getGoals();

        for(Goal goal : goals){
            result.put(goal.getType(), goal.getValue());
        }

        return result;
    }
}
