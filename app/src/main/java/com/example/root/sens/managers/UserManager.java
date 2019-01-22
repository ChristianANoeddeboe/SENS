package com.example.root.sens.managers;

import android.util.Log;

import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.UserObserver;
import com.example.root.sens.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.view_layer.recyclers.itemmodels.SetGoalItemModel;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;

public class UserManager implements IUserManager{
    private static final String TAG = UserManager.class.getSimpleName();
    private final int DAY_MILLISECONDS = (int) TimeUnit.DAYS.toMillis(1);
    private final int COUNTER_LOWER_BOUND = 3; // This is for managing the achivements.
    private User user = null;

    public UserManager(){
    }

    @Override
    public void createUser(User user, String patientKey, UserObserver userObserver){
        this.user = user;
        user.setPatientKey(patientKey);
        user.addObserver(userObserver);
        user.notifyObservers(User.USERDATA);
    }

    @Override
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
                case "Skridt":
                    list.add(new Goal(ActivityCategories.Skridt.toString(),goal.getValue()));
                default:
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

    @Override
    public void saveUser(){
        UserDAO userDao = UserDAO.getInstance();
        userDao.saveUser(user);
        userDao.setUserLoggedIn(user);
        // DemoData.initializeData();
    }

    public User getUser(String patientKey){
        return UserDAO.getInstance().getUser(patientKey);
    }

    @Override
    public boolean isUser(String patientKey) {
        UserDAO dao = UserDAO.getInstance();
        if(dao.getUser(patientKey) == null){
            return false;
        }
        return true;
    }

    @Override
    public void updateGoal(ActivityCategories activityCategory, int newValue) {
        Map<ActivityCategories, Integer> result = new HashMap<>();
        UserDAO dao = UserDAO.getInstance();
        GoalHistory goalHistory = dao.getNewestGoal();

        result = generateGoalMap(goalHistory);

        result.replace(activityCategory, newValue);
        dao.updateOrMergeGoals(result);

    }

    @Override
    public Map<ActivityCategories, Integer> getGoals(Date date) {
        UserDAO dao = UserDAO.getInstance();
        if(dao.getGoalSpecificDate(date) == null){
            return new HashMap<ActivityCategories,Integer>();
        }
        return generateGoalMap(dao.getGoalSpecificDate(date));
    }

    @Override
    public Map<ActivityCategories, Float> getDayData(Date date) {
        Map<ActivityCategories, Float> result = new HashMap<>();
        UserDAO dao = UserDAO.getInstance();
        DayData data = dao.getDataSpecificDate(date);

        if(data != null){
            RealmList<Record> records = data.getRecords();

            for(Record rec : records){
                result.put(rec.getType(), rec.getValue());
            }
        }

        return result;
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

    @Override
    public List<String> getGoalStreak(){
        Map<Date, Boolean> userHistory = generateFulfilledGoalsMap();
        ArrayList<Date> tempDates = new ArrayList<>();
        List<String> result = new ArrayList<>();

        for(Date date : userHistory.keySet()){
            boolean res = userHistory.get(date).booleanValue();
            /*
             * Remove the ! to get the actually intended code, the ! is there for testing purposes.
             * */
            if(!res){
                tempDates.add(date);
            }
        }
        Collections.sort(tempDates);

        if(tempDates.size() > 1){
            int counter = 0;
            Date endDate = null;
            for(int i = 0; i < tempDates.size()-1; i++){
                if(tempDates.get(i+1).getTime()-tempDates.get(i).getTime() <= DAY_MILLISECONDS){
                    counter++;
                    endDate = tempDates.get(i+1);
                }else{
                    addResult(counter, endDate, result);
                    counter = 0;
                }
                if(i == tempDates.size()-2){
                    addResult(counter,endDate, result);
                }
            }
        }
        return result;
    }

    @Override
    public Map<Date, Boolean> generateFulfilledGoalsMap(){
        HashMap<Date,Boolean> result = new HashMap<>();
        User activeUser = UserDAO.getInstance().getUserLoggedIn();
        RealmList<DayData> dayData = activeUser.getDayData();

        for(DayData d : dayData){
            long timeDelta = -1;
            GoalHistory currentGoalHis = null;
            /*
             * Find the smallest difference which is positive
             * We try to match the goal which is closest.
             */
            for(GoalHistory g : activeUser.getGoals()){
                long temp2 = d.getStart_time().getTime() - g.getDate().getTime();
                if(d.getStart_time().after(g.getDate())){
                    if(temp2 < timeDelta || timeDelta == -1){
                        timeDelta = temp2;
                        currentGoalHis = g;
                    }
                }

            }
            //If currentGoalHis is null then we did not find a valid match, i.e we don't have a goal for that day
            if(currentGoalHis != null){
                boolean completed = false;
                for(Goal goal : currentGoalHis.getGoals()){ // we look at all the goals within the goal history
                    for(Record record : d.getRecords()){ // Check if the user completed all its goals
                        if(record.getType().equals(goal.getType())){ // If record type equals the goal type
                            if(record.getValue() >= goal.getValue()){ // If the user has fulfilled the goal
                                completed = true;
                            }else{
                                completed = false;
                                break; // If user has not completed one of its goal it cannot be approved
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
                    Log.d(TAG, "generateFulfilledGoalsMap: "+d.toString() + "\n"+currentGoalHis.getDate().toString());
                    for(Record r : d.getRecords()){
                        Log.d(TAG, "generateFulfilledGoalsMap: "+r.toString());
                    }
                    for(Goal g : currentGoalHis.getGoals()){
                        Log.d(TAG, "generateFulfilledGoalsMap: "+g.toString());
                    }
                    result.put(d.getEnd_time(),true);
                    //calendar.addEvent(new Event(Color.rgb(76,175,80), d.getEnd_time().getTime(), "test1234"));
                }
            }

        }
        return result;
    }

    @Override
    public void deleteData() {
        UserDAO.getInstance().deleteData();
    }

    private void addResult(int counter, Date endDate, List<String> result) {
        if(counter >= COUNTER_LOWER_BOUND){
            SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("d. MMM YYYY", Locale.US);
            result.add(dateFormatForMonth.format(endDate)+","+counter);
        }
    }
}
