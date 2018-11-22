package com.example.root.sens.dto;

import java.util.Date;

public class GoalHistory {
    private int goalHistoryId;
    private Date date;
    private Goal[] goals;

    public GoalHistory(int goalHistoryId, Date date, Goal[] goals) {
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

    public Goal[] getGoals() {
        return goals;
    }

    public void setGoals(Goal[] goals) {
        this.goals = goals;
    }
}
