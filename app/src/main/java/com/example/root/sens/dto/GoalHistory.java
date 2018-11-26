package com.example.root.sens.dto;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class GoalHistory extends RealmObject {
    private int goalHistoryId;
    private Date date;
    private RealmList<Goal> goals;
    public GoalHistory(){}
    public GoalHistory(int goalHistoryId, Date date, RealmList<Goal> goals) {
        this.goalHistoryId = goalHistoryId;
        this.date = date;
        this.goals = goals;
    }

    public int getGoalHistoryId() {
        return goalHistoryId;
    }

    public void setGoalHistoryId(int goalHistoryId) {
        this.goalHistoryId = goalHistoryId;
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
}
