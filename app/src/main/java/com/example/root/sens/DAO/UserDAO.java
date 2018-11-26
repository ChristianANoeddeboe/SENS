package com.example.root.sens.DAO;

import com.example.root.sens.Singleton;
import com.example.root.sens.dto.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserDAO {
    public void createUser(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User tempUser = realm.where(User.class).sort("id").findFirst();
        user.setId(tempUser.getId()+1);
        realm.copyToRealm(user);
        realm.commitTransaction();
    }
    public void setUserLoggedIn(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
    }
    public void removeUserLoggedIn(User user){

    }
}
