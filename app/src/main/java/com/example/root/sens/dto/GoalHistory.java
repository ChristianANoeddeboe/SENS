package com.example.root.sens.dto;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class GoalHistory extends RealmObject implements Comparable {
    @Required
    private Date date;
    private RealmList<Goal> goals;

    public GoalHistory(){}

    public GoalHistory(Date date, RealmList<Goal> goals) {
        this.date = date;
        this.goals = goals;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<Goal> getGoals() {
        return goals;
    }

    public void setGoals(RealmList<Goal> goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        return "GoalHistory{" +
                ", date=" + date +
                ", goals=" + goals +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        GoalHistory curr = (GoalHistory) o;
        return curr.getDate().compareTo(date);
    }
}
