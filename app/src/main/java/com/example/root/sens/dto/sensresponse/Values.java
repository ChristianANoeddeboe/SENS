package com.example.root.sens.dto.sensresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Values {
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