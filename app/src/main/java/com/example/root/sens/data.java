package com.example.root.sens;

import com.example.root.sens.DTO.Goal;
import com.example.root.sens.DTO.GoalType;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeCalendar;
import com.example.root.sens.view.fragments.interfaces.TypeData;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class data {
    private static GoalType[] goalTypes = {
            new GoalType(1,"Løb"),
            new GoalType(2,"Søvn"),
            new GoalType(3,"Gang"),
            new GoalType(4,"Cykling"),
    };
    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static Goal[] goalArray;
    static {
        try {
            goalArray = new Goal[]{
                    new Goal(1,df.parse("11/18/2018"),1,goalTypes[0],50),
                    new Goal(2,df.parse("11/17/2018"),1,goalTypes[1],100),
                    new Goal(3,df.parse("11/18/2018"),1,goalTypes[2],200),
                };
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static ArrayList<Goal> goals = new ArrayList<>(Arrays.asList(goalArray));


    public static ArrayList<ListItem> generateData(){
        ArrayList<ListItem> data = new ArrayList<>();
        data.add(new TypeCalendar());
        for(Goal g : goalArray){
            data.add(new TypeData(g));
        }
        return data;
    }

}
