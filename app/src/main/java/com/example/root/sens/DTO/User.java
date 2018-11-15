package com.example.root.sens.DTO;

import java.util.Date;
import java.util.HashMap;

public class User {
    private int id;
    private String name;
    private Date birthday;
    private HashMap<String, Integer> goals;

    public User(int id, String name, Date birthday, HashMap<String, Integer> goals) {
        this.id = id;
        this.name = name;
        this.birthday = birthday    ;
        this.goals = goals;
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

    public HashMap<String, Integer> getGoals() {
        return goals;
    }

    public void setGoals(HashMap<String, Integer> goals) {
        this.goals = goals;
    }
}
