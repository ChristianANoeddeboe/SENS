package com.example.root.sens.dto.sensresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
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
