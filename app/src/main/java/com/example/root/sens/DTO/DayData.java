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
}
