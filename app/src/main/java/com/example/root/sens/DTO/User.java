package com.example.root.sens.DTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private Date birthday;
    private RealmList<GoalHistory> goals;
    private RealmList<DayData> dayData;
    public User(){}
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

    public RealmList<GoalHistory> getGoals() {
        return goals;
    }

    public void setGoals(RealmList<GoalHistory> goals) {
        this.goals = goals;
    }

    public RealmList<DayData> getDayData() {
        return dayData;
    }

    public void setDayData(RealmList<DayData> dayData) {
        this.dayData = dayData;
    }
}
