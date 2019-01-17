package com.example.root.sens.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.auxiliary.DayDataGoalMapper;
import com.example.root.sens.dto.User;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;

import java.util.ArrayList;
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
        IUserManager userManager = new UserManager();
        User currentUser = userManager.getUserLoggedIn();

        DayDataGoalMapper dayDataGoalMapper = new DayDataGoalMapper(userManager);
        Map<ActivityCategories, Integer> goalMap = dayDataGoalMapper.getGoalMap();
        Map<ActivityCategories, Float> dataMap = dayDataGoalMapper.getDataMap();
        ArrayList<Notification> notificationsList = new ArrayList<>();
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
        for(ActivityCategories s : goalMap.keySet()){
            int progress_max = goalMap.get(s);
            int progress_current = dataMap.get(s).intValue();
            if(progress_max != 0){
                notificationsList.add(new NotificationCompat.Builder(ctx, channelId)
                        .setSmallIcon(R.mipmap.ic_notification_round)
                        .setContentTitle(String.valueOf(s))
                        .setContentText(""+progress_current+"/"+progress_max)
                        .setGroup(GROUP_KEY_PROGRESS)
                        .setProgress(progress_max, progress_current, false)
                        .build());
            }
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
        for(int i = 0; i < notificationsList.size(); i++){
            notificationManager.notify(i,notificationsList.get(i));
        }
        notificationManager.notify(notificationsList.size(),summaryNotification);
    }
}
