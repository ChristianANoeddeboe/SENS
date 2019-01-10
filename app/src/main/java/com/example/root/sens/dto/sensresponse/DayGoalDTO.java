package com.example.root.sens.dto.sensresponse;

public class DayGoalDTO {
    public int dayCatData;
    public int dayGoaData;
    public String dataType;

    public DayGoalDTO(int dayCatData, int dayGoaData, String dataType) {

        this.dayCatData = dayCatData;
        this.dayGoaData = dayGoaData;
        this.dataType = dataType;
    }

    public int getDayCatData() {
        return dayCatData;
    }

    public void setDayCatData(int dayCatData) {
        this.dayCatData = dayCatData;
    }

    public int getDayGoaData() {
        return dayGoaData;
    }

    public void setDayGoaData(int dayGoaData) {
        this.dayGoaData = dayGoaData;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
