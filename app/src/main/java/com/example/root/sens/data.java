package com.example.root.sens;


import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.GoalType;
import com.example.root.sens.dto.User;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class data {

    public static User user = new User(1,"Hans hansen", Calendar.getInstance().getTime());
    private static GoalType goalRest = new GoalType(1,"Afslapning");
    private static GoalType goalStanding = new GoalType(2,"Stå");
    private static GoalType goalWalking = new GoalType(3,"Gang");
    private static GoalType goalCycling = new GoalType(4,"Cykling");
    private static GoalType goalExercise = new GoalType(5,"Motion");

    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static GoalHistory[] goalHistoryArray;
    static {
        try {
            goalHistoryArray = new GoalHistory[]{
              new GoalHistory(1,df.parse("11/18/2018"), new Goal[]{
                      new Goal(goalRest,60*8),
                      new Goal(goalStanding,50),
                      new Goal(goalWalking,300),
                      new Goal(goalExercise,350),
                      new Goal(goalCycling, 120)

              }),
              new GoalHistory(2,df.parse("11/10/2018"), new Goal[]{
                      new Goal(goalRest,120*8),
                      new Goal(goalStanding,300),
                      new Goal(goalWalking,400),
                      new Goal(goalExercise,550),
                      new Goal(goalCycling, 360)
              })
            };

            user.setGoals(new ArrayList<>(Arrays.asList(goalHistoryArray)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    //"2018-11-07T23:00:00"
    private static DateFormat sensDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static DayData[] dayDataArray;

    static {
        try {
            dayDataArray = new DayData[]{
                    new DayData(
                          sensDf.parse("2018-11-19T23:00:00"),
                          sensDf.parse("2018-11-20T23:00:00"),
                          60*8,20,300,0,100),
                    new DayData(
                            sensDf.parse("2018-11-17T23:00:00"),
                            sensDf.parse("2018-11-18T23:00:00"),
                            60*8,20,300,0,100),
                    new DayData(
                            sensDf.parse("2018-11-16T23:00:00"),
                            sensDf.parse("2018-11-17T23:00:00"),
                            60*8,20,300,0,100),
                };
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static{
        user.setDayData(new ArrayList<DayData>(Arrays.asList(dayDataArray)));
    }



}
