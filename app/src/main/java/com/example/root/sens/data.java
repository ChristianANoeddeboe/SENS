package com.example.root.sens;

import com.example.root.sens.Controlles.GoalController;
import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.DTO.GoalType;
import com.example.root.sens.DTO.Response;
import com.example.root.sens.DTO.User;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeCalendar;
import com.example.root.sens.view.fragments.interfaces.TypeData;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class data {

    public static User user = new User(1,"Hans hansen", Calendar.getInstance().getTime());
    private static GoalType goalRest = new GoalType(1,"Afslapning");
    private static GoalType goalStanding = new GoalType(2,"Stå");
    private static GoalType goalWalking = new GoalType(3,"Gang");
    private static GoalType goalCycling = new GoalType(4,"Cykling");
    private static GoalType goalExercise = new GoalType(5,"Motion");

    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static Goal[] goalArray;
    static {
        try {
            goalArray = new Goal[]{
                    new Goal(1,df.parse("11/18/2018"),1,goalRest,60*8),
                    new Goal(2,df.parse("11/18/2018"),1,goalStanding,50),
                    new Goal(3,df.parse("11/18/2018"),1,goalWalking,300),

                };
            user.setGoals(new ArrayList<Goal>(Arrays.asList(goalArray)));
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
                          sensDf.parse("2018-11-18T23:00:00"),
                          sensDf.parse("2018-11-19T23:00:00"),
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


    public static Call<Response> getDataFromSens(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("ttps://beta.sens.dk/exapi/1.0/patients/data/external/").addConverterFactory(GsonConverterFactory.create()).build();
        SENSAPI service = retrofit.create(SENSAPI.class);
        Call<Response> temp = service.getData();
        return  temp;

    }

}
