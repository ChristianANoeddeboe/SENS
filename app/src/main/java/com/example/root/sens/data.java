package com.example.root.sens;


import com.example.root.sens.DTO.ActivityCategories;
import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.DTO.GoalHistory;
import com.example.root.sens.DTO.User;
import com.example.root.sens.DTO.Record;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.RealmList;

public class data {

    public static User user = new User(1,"Hans hansen", Calendar.getInstance().getTime());
    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static RealmList<GoalHistory> goalHistories = new RealmList<>();
    static {
        try {
           RealmList<Goal> goals = new RealmList<>();
           goals.add(new Goal(ActivityCategories.Resting,60*8));
           goals.add(new Goal(ActivityCategories.Standing,50));
           goals.add(new Goal(ActivityCategories.Walking,300));
           goals.add(new Goal(ActivityCategories.Exercise,350));
           goals.add(new Goal(ActivityCategories.Cycling, 120));
           GoalHistory temp =  new GoalHistory(1,df.parse("11/18/2018"),goals);
           temp.setGoals(goals);
           goalHistories.add(temp);


           goals.clear();
           goals.add(new Goal(ActivityCategories.Resting,60*8));
           goals.add(new Goal(ActivityCategories.Standing,300));
           goals.add(new Goal(ActivityCategories.Walking,400));
           goals.add(new Goal(ActivityCategories.Exercise,550));
           goals.add(new Goal(ActivityCategories.Cycling, 360));
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
            RealmList<Record> temp = new RealmList<Record>();
            temp.add( new Record(50,ActivityCategories.Resting));
            temp.add( new Record(75, ActivityCategories.Standing));
            temp.add( new Record(100,ActivityCategories.Walking));
            temp.add( new Record(125, ActivityCategories.Exercise));
            temp.add( new Record(150,ActivityCategories.Cycling));
            dayData.add(new DayData( sensDf.parse("2018-11-21T23:00:00"), sensDf.parse("2018-11-22T23:00:00"),temp));
            temp.clear();
            temp.add( new Record(60*8,ActivityCategories.Resting));
            temp.add( new Record(300, ActivityCategories.Standing));
            temp.add( new Record(400,ActivityCategories.Walking));
            temp.add( new Record(550, ActivityCategories.Exercise));
            temp.add( new Record(550,ActivityCategories.Cycling));
            dayData.add(new DayData(sensDf.parse("2018-11-17T23:00:00"),sensDf.parse("2018-11-18T23:00:00"),temp));
            temp.clear();
            temp.add( new Record(60*8,ActivityCategories.Resting));
            temp.add( new Record(20, ActivityCategories.Standing));
            temp.add( new Record(300,ActivityCategories.Walking));
            temp.add( new Record(0, ActivityCategories.Exercise));
            temp.add( new Record(100,ActivityCategories.Cycling));
            dayData.add(new DayData(sensDf.parse("2018-11-16T23:00:00"),sensDf.parse("2018-11-17T23:00:00"),temp));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static{
        user.setDayData(dayData);
    }



}
