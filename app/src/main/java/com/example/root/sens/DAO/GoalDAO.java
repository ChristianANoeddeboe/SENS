package com.example.root.sens.DAO;

import com.example.root.sens.DTO.GoalHistory;
import com.example.root.sens.DTO.User;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class GoalDAO{
    public void saveGoalHistory(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insert(user.getGoals());
        realm.commitTransaction();

    }

    public GoalHistory loadGoalHistory(User user){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<GoalHistory> goalHistory = realm.where(GoalHistory.class).findAll();
        return goalHistory.first();
    }

}
