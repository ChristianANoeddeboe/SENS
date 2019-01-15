package com.example.root.sens.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.root.sens.R;
import com.example.root.sens.auxiliary.DayDataGoalMapper;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.User;

import java.util.HashMap;
import java.util.Map;


public class NotificationsManager {
    private final static String GROUP_KEY_PROGRESS = "sens.goalprogress";
    private String channelId;
    private Context ctx;


    public NotificationsManager(String channelId, Context ctx){
        this.channelId = channelId;
        this.ctx = ctx;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ctx.getString(R.string.channel_name);
            String description = ctx.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void displayNotification(){
        UserDAO userDAO = UserDAO.getInstance();
        User currentUser = userDAO.getUserLoggedIn();

        DayDataGoalMapper dayDataGoalMapper = new DayDataGoalMapper(userDAO);
        Map<String, Integer> goalMap = dayDataGoalMapper.getGoalMap();
        Map<String, Float> dataMap = dayDataGoalMapper.getDataMap();

        Notification summaryNotification =
                new NotificationCompat.Builder(ctx, channelId)
                        .setContentTitle("Hej " + currentUser.getFirstName() + "!")
                        //set content text to support devices running API level < 24
                        .setContentText("Status rapport.")
                        .setSmallIcon(R.mipmap.ic_notification_round)
                        //build summary info into InboxStyle template
                        //specify which group this notification belongs to
                        .setGroup(GROUP_KEY_PROGRESS)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .build();

        int process_max = goalMap.get(String.valueOf(ActivityCategories.Cykling));
        int progress_current = dataMap.get(String.valueOf(ActivityCategories.Cykling)).intValue();
        Notification notificationCycling = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentTitle("Cykling")
                .setContentText(""+progress_current+"/"+process_max)
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(process_max, progress_current, false)
                .build();

        process_max = goalMap.get(String.valueOf(ActivityCategories.Gang));
        progress_current = dataMap.get(String.valueOf(ActivityCategories.Gang)).intValue();
        Notification notificationWalking= new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentTitle("Gang")
                .setContentText(""+progress_current+"/"+process_max)
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(process_max, progress_current, false)
                .build();

        process_max = goalMap.get(String.valueOf(ActivityCategories.Træning));
        progress_current = dataMap.get(String.valueOf(ActivityCategories.Træning)).intValue();
        Notification notificationExercise = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentText(""+progress_current+"/"+process_max)
                .setContentTitle("Træning")
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(process_max, progress_current, false)
                .build();

        process_max = goalMap.get(String.valueOf(ActivityCategories.Stå));
        progress_current = dataMap.get(String.valueOf(ActivityCategories.Stå)).intValue();
        Notification notificationStanding = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentText(""+progress_current+"/"+process_max)
                .setContentTitle("Standing")
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(process_max, progress_current, false)
                .build();

        process_max = goalMap.get(String.valueOf(ActivityCategories.Søvn));
        progress_current = dataMap.get(String.valueOf(ActivityCategories.Søvn)).intValue();
        Notification notificationResting = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentText(""+progress_current+"/"+process_max)
                .setContentTitle("Søvn")
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(process_max, progress_current, false)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
        notificationManager.notify(0, notificationCycling);
        notificationManager.notify(1, notificationWalking);
        notificationManager.notify(2, notificationExercise);
        notificationManager.notify(3, notificationStanding);
        notificationManager.notify(4, notificationResting);
        notificationManager.notify(5, summaryNotification);
    }
}
