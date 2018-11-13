package com.example.root.sens.DTO;

import java.util.Date;
import java.util.HashMap;

public class User {
    private int id;
    private String name;
    private Date fødselsdag;
    private HashMap<String, Integer> goals;

    public User(int id, String name, Date fødselsdag, HashMap<String, Integer> goals) {
        this.id = id;
        this.name = name;
        this.fødselsdag = fødselsdag;
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

    public Date getFødselsdag() {
        return fødselsdag;
    }

    public void setFødselsdag(Date fødselsdag) {
        this.fødselsdag = fødselsdag;
    }

    public HashMap<String, Integer> getGoals() {
        return goals;
    }

    public void setGoals(HashMap<String, Integer> goals) {
        this.goals = goals;
    }
}
