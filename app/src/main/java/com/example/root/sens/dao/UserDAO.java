package com.example.root.sens.dao;

import com.example.root.sens.dto.Settings;
import com.example.root.sens.dto.User;
import io.realm.Realm;

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
        if(settings == null){
            settings = new Settings();
        }
        settings.setLoggedInUser(user);
        realm.copyToRealm(settings);
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
        setUserLoggedIn(user);
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
}
