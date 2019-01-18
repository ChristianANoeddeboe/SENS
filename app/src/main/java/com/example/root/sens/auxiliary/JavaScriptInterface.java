package com.example.root.sens.auxiliary;

import android.webkit.JavascriptInterface;

import com.google.gson.JsonArray;

public class JavaScriptInterface {
    JsonArray jsonArray;

    public JavaScriptInterface(JsonArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @JavascriptInterface
    public String getData() {
        return jsonArray.toString();
    }
}
