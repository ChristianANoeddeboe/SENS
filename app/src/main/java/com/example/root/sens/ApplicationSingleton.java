package com.example.root.sens;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationSingleton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Realm.init(this);
    }
}
