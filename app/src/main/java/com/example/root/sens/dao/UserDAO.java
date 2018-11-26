package com.example.root.sens.dao;

import com.example.root.sens.Singleton;
import com.example.root.sens.dto.Sensor;
import com.example.root.sens.dto.Settings;
import com.example.root.sens.dto.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserDAO implements IUserDao {
    @Override
    public void createUser(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User tempUser = realm.where(User.class).sort("id").findFirst();
        if(tempUser == null){
            user.setId(tempUser.getId()+1);
        }else{
            user.setId(1);
        }
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    @Override
    public void setUserLoggedIn(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Settings settings = realm.where(Settings.class).findFirst();
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
    public User getUser(String sensorId){
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("sensors.id",sensorId).findFirst();
        return user;
    }
}
