package com.example.root.sens;

import com.example.root.sens.DTO.Goal;
import com.example.root.sens.DTO.GoalType;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeCalendar;
import com.example.root.sens.view.fragments.interfaces.TypeData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class data {
    private static ListItem[] dataarray = { new TypeCalendar(),new TypeData(), new TypeData(), new TypeData()};
    public static ArrayList<ListItem> data = new ArrayList<>(Arrays.asList(dataarray));
    public static GoalType[] goalTypes = {
            new GoalType(1,"Løb"),
            new GoalType(2,"Søvn"),
            new GoalType(3,"Gang"),
            new GoalType(4,"Cykling")
    };

    public static Goal[] goalArray = {
            new Goal()

    }
}
