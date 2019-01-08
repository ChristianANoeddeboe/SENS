package com.example.root.sens.dao;

import com.example.root.sens.dao.interfaces.IUserDao;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Sensor;
import com.example.root.sens.dto.Settings;
import com.example.root.sens.dto.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

public class UserDAO implements IUserDao {
    private static UserDAO instance;
    private UserDAO(){}
    public static UserDAO getInstance(){
        if(instance==null){
            instance = new UserDAO();
        }
        return instance;
    }
    @Override
    public void setUserLoggedIn(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Settings settings = realm.where(Settings.class).findFirst();
        if(settings == null) {
            settings = new Settings();
        }
        settings.setLoggedInUser(user);
        realm.copyToRealmOrUpdate(settings);
        realm.commitTransaction();
    }
    @Override
    public void removeUserLoggedIn(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Settings settings = realm.where(Settings.class).findFirst();
        if(settings.getLoggedInUser().equals(user)){
            settings.setLoggedInUser(null);
        }
        realm.commitTransaction();
    }

    @Override
    public void saveUser(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    @Override
    public User getUserLoggedIn() {
        Realm realm = Realm.getDefaultInstance();
        Settings set = realm.where(Settings.class).findFirst();
        return set.getLoggedInUser();
    }

    @Override
    public User getUser(String sensorId){
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("sensors.id",sensorId).findFirst();
        return user;
    }

    @Override
    public GoalHistory getNewestGoal() {
        RealmList<GoalHistory> goals = getUserLoggedIn().getGoals();
        ArrayList<Date> dates = new ArrayList<>();

        for(GoalHistory curr : goals) {
            dates.add(curr.getDate());
        }
        Collections.sort(dates);

        for(GoalHistory curr : goals) {
            if(curr.getDate().equals(dates.get(0))) {
                return curr;
            }
        }
        //TODO: Cast en execption her.
        return goals.get(0);
    }


}
