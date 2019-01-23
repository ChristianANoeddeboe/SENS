package com.example.root.sens.dto.sensresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This is used for when downloading DemoData from sens only
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

    public List<Datum> getData() {
        return value.data;
    }
}

