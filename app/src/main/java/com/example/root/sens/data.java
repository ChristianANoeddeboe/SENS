package com.example.root.sens;

import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeCalendar;
import com.example.root.sens.view.fragments.interfaces.TypeData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class data {
    private static mål[] goalsArray = {new mål("Gang",50), new mål("Cykling",20), new mål("Løb", 80)};
    public static ArrayList<mål> goalsData = new ArrayList<>(Arrays.asList(goalsArray));
    private static ListItem[] dataarray = {new TypeCalendar(), new TypeData()};
    public static ArrayList<ListItem> data = new ArrayList<>(Arrays.asList(dataarray));
}
