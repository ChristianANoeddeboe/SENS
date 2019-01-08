package com.example.root.sens.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

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
    String channelId;
    Context ctx;
    NotificationManager notificationManager;


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

        currentUser.getFirstName();

        String[] text = getNotiText(currentUser);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hej " + currentUser.getFirstName() + "!")
                .setContentText("Status rapport, du har klaret " + text[0] + " af 5 mål")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text[1]))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(42, mBuilder.build());
    }

    private String[] getNotiText(User user){
        GoalHistory goals = user.getGoals().get(0);
        DayData dayData = user.getDayData().get(0);

        Map<String, Integer> goalMap = new HashMap();
        for(Goal goal : goals.getGoals()){
            goalMap.put(String.valueOf(goal.getType()), goal.getValue());
        }
        Map<String, Float> dataMap = new HashMap();
        for(Record record : dayData.getRecords()){
            dataMap.put(String.valueOf(record.getType()), record.getValue());
        }


        ArrayList<String> list = new ArrayList<>();
        EnumSet.allOf(ActivityCategories.class).forEach(day -> list.add(String.valueOf(day)));

        String string = "";
        int goalCompletedCounter = 0, goal = 0, day = 0;
        for(String str : list){
            goal = goalMap.get(str);
            day = dataMap.get(str).intValue();
            string += str + ": " + day + "/" + goal  + "\n";
            if(day >= goal){
                goalCompletedCounter++;
            }
        }

        return new String[]{String.valueOf(goalCompletedCounter), string};
    }

}
