package com.example.root.sens.dto;

import com.example.root.sens.dao.interfaces.UserObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @Ignore
    public static final String USERDATA = "userdata";
    @Ignore
    public static final String GOALDATA = "goaldata";
    @Ignore
    private List<UserObserver> observers = new ArrayList<>();

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private RealmList<Sensor> sensors;
    private Date birthday;
    private RealmList<GoalHistory> goals;
    private RealmList<DayData> dayData;
    private String firstName;
    private String lastName;


    public User(){}

    public User(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public RealmList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(RealmList<Sensor> sensors) {
        this.sensors = sensors;
    }

    public static String getUSERDATA() {
        return USERDATA;
    }

    public static String getGOALDATA() {
        return GOALDATA;
    }

    public List<UserObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<UserObserver> observers) {
        this.observers = observers;
    }
}
