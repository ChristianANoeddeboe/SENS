package com.example.root.sens.auxiliary;

import android.webkit.JavascriptInterface;

import com.google.gson.JsonArray;

public class JavaScriptInterface {
    private JsonArray jsonArray;
    private String yAxisDescription;
    private String color;

    public JavaScriptInterface(JsonArray jsonArray, String color) {
        this.jsonArray = jsonArray;
        this.color = color;
    }

    @JavascriptInterface
    public String getData() {
        return jsonArray.toString();
    }

    @JavascriptInterface
    public String getYAxisDesc(){
        return yAxisDescription;
    }

    @JavascriptInterface
    public String getColor(){
        return color;
    }

    public void setyAxisDescription(String yAxisDescription){
        this.yAxisDescription = yAxisDescription;
    }
}
