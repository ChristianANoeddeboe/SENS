package com.example.root.sens;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.Date;

import io.fabric.sdk.android.Fabric;

import io.realm.Realm;

public class ApplicationSingleton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator") || Build.ID.equals("OPR1.170623.032") || Build.ID.equals("O11019");
        // Build.ID.equals("OPR1.170623.032") this is for Thyges device
        if (!EMULATOR) {
            Fabric.with(this, new Crashlytics());
        }
        Realm.init(this);
    }
}

