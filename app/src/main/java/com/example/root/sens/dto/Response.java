package com.example.root.sens.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This is used for when downloading data from sens only
 */
public class Response {

    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("status_msg")
    @Expose
    public String statusMsg;
    @SerializedName("value")
    @Expose
    public Value value;

}

class Values {

    @SerializedName("activity/resting/time")
    @Expose
    public Float activityRestingTime;
    @SerializedName("activity/standing/time")
    @Expose
    public Float activityStandingTime;
    @SerializedName("activity/walking/time")
    @Expose
    public Float activityWalkingTime;
    @SerializedName("activity/cycling/time")
    @Expose
    public Float activityCyclingTime;
    @SerializedName("activity/exercise/time")
    @Expose
    public Float activityExerciseTime;
    @SerializedName("activity/other/time")
    @Expose
    public Float activityOtherTime;
    @SerializedName("general/nodata/time")
    @Expose
    public Float generalNodataTime;
    @SerializedName("activity/steps/count")
    @Expose
    public Float activityStepsCount;

}

class Value {

    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

}

class Datum {

    @SerializedName("start_time")
    @Expose
    public String startTime;
    @SerializedName("end_time")
    @Expose
    public String endTime;
    @SerializedName("values")
    @Expose
    public Values values;

}