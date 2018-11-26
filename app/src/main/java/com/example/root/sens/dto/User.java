package com.example.root.sens.dto;

import com.example.root.sens.UserObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    public static final String USERDATA = "userdata";
    public static final String GOALDATA = "goaldata";
    private List<UserObserver> observers = new ArrayList<>();
    @PrimaryKey
    private int id;
    private String firstName;
    private String lastName;
    private RealmList<Sensor> sensors;
    private Date birthday;
    private RealmList<GoalHistory> goals;
    private RealmList<DayData> dayData;

    public User(){}

    public User(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    @Override
    public String toString() {
        return "User{" +
                "observers=" + observers +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sensors=" + sensors +
                ", birthday=" + birthday +
                ", goals=" + goals +
                ", dayData=" + dayData +
                '}';
    }

    public void notifyObservers(String tag){
        for(UserObserver observer : observers){
            observer.update(tag, this);
        }
    }

    public void addObserver(UserObserver userObserver){
        observers.add(userObserver);
    }

    public void removeObserver(UserObserver userObserver){
        observers.remove(userObserver);
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
