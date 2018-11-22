package com.example.root.sens.adapters;

import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.DTO.User;
import com.example.root.sens.R;
import com.example.root.sens.data;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmList;

public class ViewHolderData extends ViewHolder {
    private final DecoView progressCircle;
    private TextView exerciseText, standingText, walkingText, restText, cyclingText;
    private ImageView exerciseLogo, standingLogo, walkingLogo, restLogo, cyclingLogo;
    public ViewHolderData(View itemView) {
        super(itemView);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        exerciseText = itemView.findViewById(R.id.exerciselogotxt);
        standingText = itemView.findViewById(R.id.standinglogotxt);
        walkingText = itemView.findViewById(R.id.walkinglogotxt);
        restText = itemView.findViewById(R.id.restlogotxt);
        cyclingText = itemView.findViewById(R.id.cyclinglogotxt);

        exerciseLogo = itemView.findViewById(R.id.exerciselogo);
        standingLogo = itemView.findViewById(R.id.standinglogo);
        walkingLogo = itemView.findViewById(R.id.walkinglogo);
        restLogo = itemView.findViewById(R.id.restlogo);
        cyclingLogo = itemView.findViewById(R.id.cyclinglogo);
    }

    public void bindType(ListItem item) {
        for(DayData d : data.user.getDayData()){
           if(isToday(d)){
               int size = data.user.getGoals().size();
               RealmList<Goal> goals = data.user.getGoals().get(size-1).getGoals();
               int numofgoals = goals.size();

               int[] colors = {
                       Color.argb(255, 0, 0, 255),
                       Color.argb(255, 255, 0, 255),
                       Color.argb(255, 255, 0, 0),
                       Color.argb(255, 255, 255, 0),
                       Color.argb(255, 0, 255, 0),
                       Color.argb(255, 0, 255, 255)
               };

               int emptyGoals = 0;

               for(Goal curr : goals) {
                   if(curr.getValue() == 0) {
                       emptyGoals++;
                   }
               }

               float progresswidth = 24f;
               float totalprogresswidth = (numofgoals-emptyGoals)*progresswidth; //120



               //https://github.com/bmarrdev/android-DecoView-charting
               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                       .setRange(0, 100, 100)
                       .setLineWidth(totalprogresswidth)
                       .build());

               float inset = -((progresswidth * ((numofgoals-emptyGoals) - 1)) / 2);
               int max, current;
               for (int i = 0; i < numofgoals; i++) {
                   max = goals.get(i).getValue();
                   current = (int) d.getGoalData()[i];
                   if(max > 0) {
                       if(current > max) {
                           current = max;
                       }
                       progressCircle.addSeries(new SeriesItem.Builder(colors[i])
                               .setRange(0, max, current)
                               .setLineWidth(progresswidth)
                               .setInset(new PointF(inset, inset))
                               .build());

                       inset += progresswidth;
                   }

               }
               chooseGoalIcons(goals, d);
           }
        }
    }

    private void chooseGoalIcons(RealmList<Goal> allGoals, DayData dayGoals) {
        for(Goal curr : allGoals) {
            if(curr.getValue() > 0) {
                double currentGoalValue = dayGoals.getGoalData()[curr.getType().getTypeId()-1];
                addGoalIcon(curr, currentGoalValue);
            } else {
                removeGoalIcon(curr);
            }
        }
    }

    private void removeGoalIcon(Goal curr) {
        switch (curr.getType().getTypeId()) {
            case 1:
                restLogo.setVisibility(View.GONE);
                restText.setVisibility(View.GONE);
                break;
            case 2:
                standingLogo.setVisibility(View.GONE);
                standingText.setVisibility(View.GONE);
                break;
            case 3:
                walkingLogo.setVisibility(View.GONE);
                walkingText.setVisibility(View.GONE);
                break;
            case 4:
                cyclingLogo.setVisibility(View.GONE);
                cyclingText.setVisibility(View.GONE);
                break;
            case 5:
                exerciseLogo.setVisibility(View.GONE);
                exerciseText.setVisibility(View.GONE);
                break;
        }
    }

    private void addGoalIcon(Goal curr, double currentGoalValue) {
        switch (curr.getType().getTypeId()) {
            case 1:
                restLogo.setVisibility(View.VISIBLE);
                restText.setVisibility(View.VISIBLE);
                restText.setText(createIconText(curr, currentGoalValue));
                break;
            case 2:
                standingLogo.setVisibility(View.VISIBLE);
                standingText.setVisibility(View.VISIBLE);
                standingText.setText(createIconText(curr, currentGoalValue));
                break;
            case 3:
                walkingLogo.setVisibility(View.VISIBLE);
                walkingText.setVisibility(View.VISIBLE);
                walkingText.setText(createIconText(curr, currentGoalValue));
                break;
            case 4:
                cyclingLogo.setVisibility(View.VISIBLE);
                cyclingText.setVisibility(View.VISIBLE);
                cyclingText.setText(createIconText(curr, currentGoalValue));
                break;
            case 5:
                exerciseLogo.setVisibility(View.VISIBLE);
                exerciseText.setVisibility(View.VISIBLE);
                exerciseText.setText(createIconText(curr, currentGoalValue));
                break;
        }
    }

    private String createIconText(Goal curr, double currentGoalValue) {
        String current = ""+(int)currentGoalValue;
        String total = ""+curr.getValue();
        return current+"/"+total;
    }

    private boolean isToday(DayData d) {
        Calendar todayCalendar = Calendar.getInstance();
        Calendar dCalendar = Calendar.getInstance();
        dCalendar.setTime(d.getEnd_time());
        if(dCalendar.get(Calendar.DAY_OF_YEAR) == todayCalendar.get(Calendar.DAY_OF_YEAR) && dCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)){
            return true;
        }else{
            return false;
        }
    }
}
