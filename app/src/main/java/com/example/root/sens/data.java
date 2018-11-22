package com.example.root.sens;


import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.DTO.GoalHistory;
import com.example.root.sens.DTO.GoalType;
import com.example.root.sens.DTO.User;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import io.realm.RealmList;

public class data {

    public static User user = new User(1,"Hans hansen", Calendar.getInstance().getTime());
    private static GoalType goalRest = new GoalType(1,"Afslapning");
    private static GoalType goalStanding = new GoalType(2,"Stå");
    private static GoalType goalWalking = new GoalType(3,"Gang");
    private static GoalType goalCycling = new GoalType(4,"Cykling");
    private static GoalType goalExercise = new GoalType(5,"Motion");

    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static RealmList<GoalHistory> goalHistories = new RealmList<>();
    static {
        try {
           RealmList<Goal> goals = new RealmList<>();
           goals.add(new Goal(goalRest,60*8));
           goals.add(new Goal(goalStanding,50));
           goals.add(new Goal(goalWalking,300));
           goals.add(new Goal(goalCycling, 120));
            goals.add(new Goal(goalExercise,350));
           GoalHistory temp =  new GoalHistory(1,df.parse("11/18/2018"),goals);
           temp.setGoals(goals);
           goalHistories.add(temp);


           goals.clear();
           goals.add(new Goal(goalRest,120*8));
           goals.add(new Goal(goalStanding,300));
           goals.add(new Goal(goalWalking,400));
           goals.add(new Goal(goalCycling, 360));
            goals.add(new Goal(goalExercise,0));
           GoalHistory temp2 = new GoalHistory(2,df.parse("11/10/2018"),goals);
           goalHistories.add(temp2);
           user.setGoals(goalHistories);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    //"2018-11-07T23:00:00"
    private static DateFormat sensDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static RealmList<DayData> dayData = new RealmList<>();

    static {
        try {
            dayData.add(new DayData(
                    sensDf.parse("2018-11-21T23:00:00"),
                    sensDf.parse("2018-11-22T23:00:00"),
                    60*8,20,300,0,100));
            dayData.add(new DayData(
                            sensDf.parse("2018-11-17T23:00:00"),
                            sensDf.parse("2018-11-18T23:00:00"),
                            60*8,20,300,0,100));
            dayData.add(new DayData(
                    sensDf.parse("2018-11-16T23:00:00"),
                    sensDf.parse("2018-11-17T23:00:00"),
                    60*8,20,300,0,100));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static{
        user.setDayData(dayData);
    }



}
