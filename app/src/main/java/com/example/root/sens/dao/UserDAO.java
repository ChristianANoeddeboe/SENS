package com.example.root.sens.dao;

import android.util.Log;

import com.example.root.sens.dao.interfaces.DatabaseObserver;
import com.example.root.sens.dao.interfaces.DatabaseSubject;
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
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class UserDAO implements IUserDao, DatabaseSubject {
    private static UserDAO instance;
    private ArrayList<DatabaseObserver> mObservers;
    private final String TAG = UserDAO.class.getSimpleName();
    private final int DAY_MILLISECONDS = (int) TimeUnit.DAYS.toMillis(1);
    private Realm realm;
    private RealmChangeListener realmListener;
    private UserDAO(){}
    public static UserDAO getInstance(){
        if(instance==null){
            instance = new UserDAO();
            instance.mObservers = new ArrayList<>();
            instance.realm = Realm.getDefaultInstance();
            instance.realmListener = o -> instance.notifyObservers();
            instance.realm.addChangeListener(instance.realmListener);
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
        ArrayList<GoalHistory> tempGoalHis = new ArrayList<>();
        for(GoalHistory curr : goals) {
            tempGoalHis.add(curr);
        }
        Collections.sort(tempGoalHis);

        return tempGoalHis.get(0);
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

    @Override
    public HashMap<Date,Boolean> userFulfilledGoals() {
        HashMap<Date,Boolean> result = new HashMap<>();
        User activeUser = UserDAO.getInstance().getUserLoggedIn();
        for(DayData d : activeUser.getDayData()){
            long timeDelta = -1;
            GoalHistory temp = null;
            /*
             * Find the smallest difference which is positive
             * We try to match the goal which is closest.
             */
            for(GoalHistory g : activeUser.getGoals()){
                long temp2 = d.getStart_time().getTime() - g.getDate().getTime();
                if(d.getStart_time().after(g.getDate())){
                    if(temp2 < timeDelta || timeDelta == -1){
                        timeDelta = temp2;
                        temp = g;
                    }
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
            long delta = day.getEnd_time().getTime()-d.getTime();
            if(delta > 0 && delta <DAY_MILLISECONDS){
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
            if(mostRecent == null && d.after(g.getDate())){
                mostRecent = g;
            }else{
                if(d.after(g.getDate())){
                    long bestTimeDelta = d.getTime()-mostRecent.getDate().getTime();
                    long currentTimeDelta = d.getTime()-g.getDate().getTime();
                    if(bestTimeDelta > currentTimeDelta){
                        mostRecent = g;
                    }
                }
            }
        }
        return mostRecent;
    }

    @Override
    public void updateOrMergeGoals(HashMap<String, Integer> newgoals) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = UserDAO.getInstance().getUserLoggedIn();
                RealmList<GoalHistory> temp = u.getGoals();
                boolean found = false;
                for(GoalHistory goalHistory : temp){
                    if(Math.abs(goalHistory.getDate().getTime()-new Date().getTime()) < DAY_MILLISECONDS){
                        RealmList<Goal> tempGoals = goalHistory.getGoals();
                        for(Goal goal : tempGoals){
                            if(newgoals.containsKey(goal.getType().toString())){
                                int i = newgoals.get(goal.getType().toString());
                                goal.setValue(i);

                            }
                        }
                        found = true;
                        break;
                    }
                }
                if(!found){
                    RealmList<Goal> tempGoals = new RealmList<>();
                    for(String s : newgoals.keySet()){
                        tempGoals.add(new Goal(s,newgoals.get(s)));
                    }
                    GoalHistory goalHistory = new GoalHistory();
                    goalHistory.setDate(new Date());
                    goalHistory.setGoals(tempGoals);
                    u.getGoals().add(goalHistory);
                }
                realm.copyToRealmOrUpdate(u);
            }
        });
    }


    @Override
    public void registerObserver(DatabaseObserver databaseObserver) {
        if(!mObservers.contains(databaseObserver)) {
            mObservers.add(databaseObserver);

        }
    }

    @Override
    public void removeObserver(DatabaseObserver databaseObserver) {
        if(mObservers.contains(databaseObserver)) {
            mObservers.remove(databaseObserver);
        }
    }

    @Override
    public void notifyObservers() {
        if(mObservers.size() != 0){
            for (DatabaseObserver observer: mObservers) {
                observer.onDataChanged();
            }
        }
    }
}
