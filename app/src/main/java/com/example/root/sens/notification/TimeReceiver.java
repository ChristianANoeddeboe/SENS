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
        NotificationsManager notificationsManager = new NotificationsManager("42", context);
        notificationsManager.displayNotification();
    }
}
