package com.example.root.sens.dto;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DayData extends RealmObject {
    private Date start_time, end_time;
    private RealmList<Record> records;
    public DayData(){}

    public DayData(Date start_time, Date end_time, RealmList<Record> records) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.records = records;
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

    public RealmList<Record> getRecords() {
        return records;
    }

    public void setRecords(RealmList<Record> records) {
        this.records = records;
    }
}
