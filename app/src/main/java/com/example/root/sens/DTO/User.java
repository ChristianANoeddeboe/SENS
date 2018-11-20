package com.example.root.sens.DTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class User {
    private int id;
    private String name;
    private Date birthday;
    private ArrayList<GoalHistory> goals;
    private ArrayList<DayData> dayData;

    public User(int id, String name, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public ArrayList<GoalHistory> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<GoalHistory> goals) {
        this.goals = goals;
    }

    public ArrayList<DayData> getDayData() {
        return dayData;
    }

    public void setDayData(ArrayList<DayData> dayData) {
        this.dayData = dayData;
    }
}
