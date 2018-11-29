package com.example.root.sens.dto.sensresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Value {

    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
}
