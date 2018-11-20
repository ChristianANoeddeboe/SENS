package com.example.root.sens.DTO;

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
    public Integer activityRestingTime;
    @SerializedName("activity/standing/time")
    @Expose
    public Integer activityStandingTime;
    @SerializedName("activity/walking/time")
    @Expose
    public Integer activityWalkingTime;
    @SerializedName("activity/cycling/time")
    @Expose
    public Integer activityCyclingTime;
    @SerializedName("activity/exercise/time")
    @Expose
    public Integer activityExerciseTime;
    @SerializedName("activity/other/time")
    @Expose
    public Integer activityOtherTime;
    @SerializedName("general/nodata/time")
    @Expose
    public Integer generalNodataTime;
    @SerializedName("activity/steps/count")
    @Expose
    public Integer activityStepsCount;

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