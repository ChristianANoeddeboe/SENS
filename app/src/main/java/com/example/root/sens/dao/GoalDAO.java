package com.example.root.sens.dao;

import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Used to load/save goals from sqlite
 */
public class GoalDAO{
    public void saveGoalHistory(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user.getGoals());
        realm.commitTransaction();
    }

    public GoalHistory loadGoalHistory(User user){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<GoalHistory> goalHistory = realm.where(GoalHistory.class).findAll();
        GoalHistory g = goalHistory.first();
        return goalHistory.first();
    }

}