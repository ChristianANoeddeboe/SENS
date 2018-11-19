package com.example.root.sens.DTO;

import java.util.Date;

public class Goal {
    private int goalId;
    private Date date;
    private int userId;
    private GoalType type;
    private int value;


    public Goal(int goalId, Date date, int userId, GoalType type, int value) {
        this.goalId = goalId;
        this.date = date;
        this.userId = userId;
        this.type = type;
        this.value = value;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
