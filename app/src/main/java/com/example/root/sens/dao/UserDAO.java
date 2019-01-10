package com.example.root.sens.dao;

import com.example.root.sens.dao.interfaces.IUserDao;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.Settings;
import com.example.root.sens.dto.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

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

    @Override
    public ArrayList<DayData> getSortedDayData() {
        RealmList<DayData> dayData = getUserLoggedIn().getDayData();
        ArrayList<DayData> result = new ArrayList<>();
        for (DayData d : dayData){
            result.add(d);
        }
        Collections.sort(result);
        return result;

    }


    public HashMap<Date,Boolean> userFulfilledGoals() {
        HashMap<Date,Boolean> result = new HashMap<>();
        User activeUser = UserDAO.getInstance().getUserLoggedIn();
        for(DayData d : activeUser.getDayData()){
            long timeDelta = -1;
            GoalHistory temp = null;
            /**
             * Find the smallest difference which is positive
             */
            for(GoalHistory g : activeUser.getGoals()){
                long temp2 = d.getStart_time().getTime() - g.getDate().getTime();
                if((temp2 < timeDelta && temp2 >= 0)||timeDelta == -1) {
                    timeDelta = temp2;
                    temp = g;
                }
            }
            //If temp is null then we did not find a valid match
            if(temp != null){
                boolean completed = false;
                for(Goal g : temp.getGoals()){
                    completed = false;
                    for(Record r : d.getRecords()){ // Check if the user completed all its goals
                        if(r.getType().equals(g.getType())){
                            if(r.getValue() >= g.getValue()){
                                completed = true;
                                break;
                            }
                        }
                    }
                    if(!completed){
                        result.put(d.getEnd_time(),false);
                        //calendar.addEvent(new Event(Color.rgb(244,57,54), d.getEnd_time().getTime(), "test"));
                        break;
                    }
                }
                if(completed){
                    result.put(d.getEnd_time(),true);
                    //calendar.addEvent(new Event(Color.rgb(76,175,80), d.getEnd_time().getTime(), "test1234"));
                }
            }

        }
        return result;
    }

    @Override
    public DayData getDataSpecificDate(Date d) {
        RealmList<DayData> dayData = UserDAO.getInstance().getUserLoggedIn().getDayData();
        for(DayData day : dayData){
            if(Math.abs(day.getEnd_time().getTime() - d.getTime()) < 86400000 ){
                return day;
            }
        }
        return null;
    }

    @Override
    public GoalHistory getGoalSpecificDate(Date d) {
        RealmList<GoalHistory> goalHistories = UserDAO.getInstance().getUserLoggedIn().getGoals();
        GoalHistory mostRecent = null;
        for(GoalHistory g : goalHistories){
            if(mostRecent == null){
                mostRecent = g;
            }else{
                if((Math.abs(mostRecent.getDate().getTime()-d.getTime())) > (Math.abs(g.getDate().getTime()-d.getTime()))){
                    mostRecent = g;
                }
            }
        }
        return mostRecent;
    }


}
