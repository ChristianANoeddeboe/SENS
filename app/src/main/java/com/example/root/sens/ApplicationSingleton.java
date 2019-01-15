package com.example.root.sens;

import android.app.Application;
import android.os.Build;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import io.realm.Realm;

public class ApplicationSingleton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
        if (!EMULATOR) {
            //Fabric.with(this, new Crashlytics());
        }
        Realm.init(this);
    }
}

