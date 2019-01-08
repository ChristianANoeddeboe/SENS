package com.example.root.sens.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeReceiver extends BroadcastReceiver {

    /*
    Creating a receiver to handle time notification.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent intent1 = new Intent(context, TimeService.class);
//        context.startService(intent1);
        Toast.makeText(context, "Den virker", Toast.LENGTH_LONG).show();
    }
}
