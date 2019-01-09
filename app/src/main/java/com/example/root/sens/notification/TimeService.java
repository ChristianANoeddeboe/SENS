package com.example.root.sens.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.root.sens.R;
import com.example.root.sens.activities.Login;

public class TimeService extends IntentService {
    private static final int NOTIFICATIONID = 42;

    public TimeService(){
        super("TimeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String title = "Title";
        String messageLong = "Lang";
        String messageBig = "Big";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "43")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(messageLong)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBig))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notifyIntent = new Intent(this, Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        mBuilder.setContentIntent(pendingIntent);
        Notification notificationCompat = mBuilder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATIONID, notificationCompat);
    }
}
