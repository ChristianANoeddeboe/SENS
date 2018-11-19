package com.example.root.sens.DTO;

import java.util.Date;

public class DayData {
    private Date start_time, end_time;
    private double resting,standing,walking,cycling,exercise,other,nodata,steps;

    public DayData(Date start_time, Date end_time, double resting, double standing, double walking, double cycling, double exercise) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.resting = resting;
        this.standing = standing;
        this.walking = walking;
        this.cycling = cycling;
        this.exercise = exercise;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public double getResting() {
        return resting;
    }

    public void setResting(double resting) {
        this.resting = resting;
    }

    public double getStanding() {
        return standing;
    }

    public void setStanding(double standing) {
        this.standing = standing;
    }

    public double getWalking() {
        return walking;
    }

    public void setWalking(double walking) {
        this.walking = walking;
    }

    public double getCycling() {
        return cycling;
    }

    public void setCycling(double cycling) {
        this.cycling = cycling;
    }

    public double getExercise() {
        return exercise;
    }

    public void setExercise(double exercise) {
        this.exercise = exercise;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public double getNodata() {
        return nodata;
    }

    public void setNodata(double nodata) {
        this.nodata = nodata;
    }

    public double getSteps() {
        return steps;
    }

    public void setSteps(double steps) {
        this.steps = steps;
    }
}
