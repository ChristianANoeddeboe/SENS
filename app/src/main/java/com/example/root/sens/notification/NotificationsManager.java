package com.example.root.sens.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.User;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class NotificationsManager {
    private final static String GROUP_KEY_PROGRESS = "sens.goalprogress";
    private String channelId;
    private Context ctx;
    private NotificationManager notificationManager;
    private Map<String, Integer> goalMap = new HashMap();
    private Map<String, Float> dataMap = new HashMap();


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
            notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void displayNotification(){
        UserDAO userDAO = UserDAO.getInstance();
        User currentUser = userDAO.getUserLoggedIn();
        loadData(currentUser);
        currentUser.getFirstName();

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

        int PROGRESS_MAX = goalMap.get(String.valueOf(ActivityCategories.Walking));
        int PROGRESS_CURRENT = dataMap.get(String.valueOf(ActivityCategories.Walking)).intValue();
        Notification notificationWalking = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentTitle("Walking")
                .setContentText(""+PROGRESS_CURRENT+"/"+PROGRESS_MAX)
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                .build();

        PROGRESS_MAX = goalMap.get(String.valueOf(ActivityCategories.Cycling));
        PROGRESS_CURRENT = dataMap.get(String.valueOf(ActivityCategories.Cycling)).intValue();
        Notification notificationCycling= new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentTitle("Cycling")
                .setContentText(""+PROGRESS_CURRENT+"/"+PROGRESS_MAX)
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                .build();

        PROGRESS_MAX = goalMap.get(String.valueOf(ActivityCategories.Exercise));
        PROGRESS_CURRENT = dataMap.get(String.valueOf(ActivityCategories.Exercise)).intValue();
        Notification notificationExercise = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentText(""+PROGRESS_CURRENT+"/"+PROGRESS_MAX)
                .setContentTitle("Exercise")
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                .build();

        PROGRESS_MAX = goalMap.get(String.valueOf(ActivityCategories.Standing));
        PROGRESS_CURRENT = dataMap.get(String.valueOf(ActivityCategories.Standing)).intValue();
        Notification notificationStanding = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentText(""+PROGRESS_CURRENT+"/"+PROGRESS_MAX)
                .setContentTitle("Standing")
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                .build();

        PROGRESS_MAX = goalMap.get(String.valueOf(ActivityCategories.Resting));
        PROGRESS_CURRENT = dataMap.get(String.valueOf(ActivityCategories.Resting)).intValue();
        Notification notificationResting = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentText(""+PROGRESS_CURRENT+"/"+PROGRESS_MAX)
                .setContentTitle("Resting")
                .setGroup(GROUP_KEY_PROGRESS)
                .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
        notificationManager.notify(0, notificationStanding);
        notificationManager.notify(1, notificationWalking);
        notificationManager.notify(2, notificationExercise);
        notificationManager.notify(3, notificationCycling);
        notificationManager.notify(4, notificationResting);
        notificationManager.notify(5, summaryNotification);
    }

    private void loadData(User user){
        GoalHistory goals = user.getGoals().get(0);
        DayData dayData = user.getDayData().get(0);


        for(Goal goal : goals.getGoals()){
            goalMap.put(String.valueOf(goal.getType()), goal.getValue());
        }

        for(Record record : dayData.getRecords()){
            dataMap.put(String.valueOf(record.getType()), record.getValue());
        }
    }

}
