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
           goals.add(new Goal(ActivityCategories.Resting.toString(),60*8));
           goals.add(new Goal(ActivityCategories.Standing.toString(),50));
           goals.add(new Goal(ActivityCategories.Walking.toString(),300));
           goals.add(new Goal(ActivityCategories.Exercise.toString(),350));
           goals.add(new Goal(ActivityCategories.Cycling.toString(), 120));
           GoalHistory temp =  new GoalHistory(1,df.parse("11/18/2018"),goals);
           temp.setGoals(goals);
           goalHistories.add(temp);


           goals.clear();
           goals.add(new Goal(ActivityCategories.Resting.toString(),60*8));
           goals.add(new Goal(ActivityCategories.Standing.toString(),300));
           goals.add(new Goal(ActivityCategories.Walking.toString(),400));
           goals.add(new Goal(ActivityCategories.Exercise.toString(),550));
           goals.add(new Goal(ActivityCategories.Cycling.toString(), 360));
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
            temp.add( new Record(50,ActivityCategories.Resting.toString()));
            temp.add( new Record(75, ActivityCategories.Standing.toString()));
            temp.add( new Record(100,ActivityCategories.Walking.toString()));
            temp.add( new Record(125, ActivityCategories.Exercise.toString()));
            temp.add( new Record(150,ActivityCategories.Cycling.toString()));
            dayData.add(new DayData( sensDf.parse("2018-11-25T23:00:00"), sensDf.parse("2018-11-26T23:00:00"),temp));
            temp.clear();
            temp.add( new Record(60*8,ActivityCategories.Resting.toString()));
            temp.add( new Record(300, ActivityCategories.Standing.toString()));
            temp.add( new Record(400,ActivityCategories.Walking.toString()));
            temp.add( new Record(550, ActivityCategories.Exercise.toString()));
            temp.add( new Record(550,ActivityCategories.Cycling.toString()));
            dayData.add(new DayData(sensDf.parse("2018-11-17T23:00:00"),sensDf.parse("2018-11-18T23:00:00"),temp));
            temp.clear();
            temp.add( new Record(60*8,ActivityCategories.Resting.toString()));
            temp.add( new Record(20, ActivityCategories.Standing.toString()));
            temp.add( new Record(300,ActivityCategories.Walking.toString()));
            temp.add( new Record(0, ActivityCategories.Exercise.toString()));
            temp.add( new Record(100,ActivityCategories.Cycling.toString()));
            dayData.add(new DayData(sensDf.parse("2018-11-16T23:00:00"),sensDf.parse("2018-11-17T23:00:00"),temp));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static{
        user.setDayData(dayData);
    }



}
