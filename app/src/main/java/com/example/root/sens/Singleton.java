package com.example.root.sens;

import android.app.Application;

import io.realm.Realm;

public class Singleton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

