package com.example.root.sens.dao;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.dao.interfaces.DatabaseObserver;
import com.example.root.sens.dao.interfaces.DatabaseSubject;
import com.example.root.sens.dao.interfaces.IUserDao;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Settings;
import com.example.root.sens.dto.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class UserDAO implements IUserDao, DatabaseSubject {
    private static final String TAG = UserDAO.class.getSimpleName();
    private static UserDAO instance;
    private ArrayList<DatabaseObserver> mObservers;
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

    /**
     * Sets an user as the current active user
     * @param user
     */
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

    /**
     * Removes the user as being the current logged in user
     * @param user
     */
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

    /**
     * Save the current instance of the user object
     * @param user
     */
    @Override
    public void saveUser(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
        notifyObservers();
    }

    /**
     * Get the currently logged in and active user
     * @return
     */
    @Override
    public User getUserLoggedIn() {
        Realm realm = Realm.getDefaultInstance();
        Settings set = realm.where(Settings.class).findFirst();
        return set.getLoggedInUser();
    }

    /**
     * Get user with a specific patientKey
     * @param patientKey
     * @return
     */
    @Override
    public User getUser(String patientKey){
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("patientKey",patientKey).findFirst();
        return user;
    }

    /**
     * Get the users newest goals and thus current goals
     * @return a GoalHistory object
     */
    @Override
    public GoalHistory getNewestGoal() {
        Realm realm = Realm.getDefaultInstance();
        RealmList<GoalHistory> goals = getUserLoggedIn().getGoals();
        ArrayList<GoalHistory> tempGoalHis = new ArrayList<>();

        if(goals.isEmpty()){
            realm.beginTransaction();
            goals.add(new GoalHistory(new Date(), new RealmList<Goal>()));
            realm.commitTransaction();
        }

        if(goals.size()==1 && goals.get(0).getGoals().isEmpty()){
            realm.beginTransaction();
            for(ActivityCategories activityCategory : ActivityCategories.values()){
                goals.get(0).getGoals().add(new Goal(activityCategory.toString(),0));
            }
            realm.commitTransaction();
            return goals.get(0);
        }
        for(GoalHistory curr : goals) {
            tempGoalHis.add(curr);
        }

        Collections.sort(tempGoalHis);

        return tempGoalHis.get(0);
    }

    /**
     * Get the users daydata in sorted order
     * @return
     */
    @Override
    public List<DayData> getSortedDayData() {
        RealmList<DayData> dayData = getUserLoggedIn().getDayData();
        ArrayList<DayData> result = new ArrayList<>();
        for (DayData d : dayData){
            result.add(d);
        }
        Collections.sort(result);
        return result;

    }

    /**
     * Get daydata for a specific date
     * @param d
     * @return
     */
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

    /**
     * Get the goals the user had at day d
     * @param d
     * @return
     */
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

    /**
     * This function updates and merges the goals it receives.
     * @param newgoals A hashmap AcivityCategory,Integer where the integer is the goal value
     */
    @Override
    public void updateOrMergeGoals(Map<ActivityCategories, Integer> newgoals) {
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
                            if(newgoals.containsKey(goal.getType())){
                                int i = newgoals.get(goal.getType());
                                goal.setValue(i);

                            }
                        }
                        found = true;
                        break;
                    }
                }
                if(!found){
                    RealmList<Goal> tempGoals = new RealmList<>();

                    for(ActivityCategories activityCategory : newgoals.keySet()){
                        tempGoals.add(new Goal(String.valueOf(activityCategory),newgoals.get(activityCategory)));
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

    /**
     * Used when the user requests for its data to be deletd
     */
    @Override
    public void deleteData() {
        User u = UserDAO.getInstance().getUserLoggedIn();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        while(u.getDayData().iterator().hasNext()){
            u.getDayData().iterator().next().deleteFromRealm();
        }
        while(u.getGoals().iterator().hasNext()){
            u.getGoals().iterator().next().deleteFromRealm();
        }
        realm.commitTransaction();
    }

    //Below methods are for observer pattern
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
