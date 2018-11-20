package com.example.root.sens.adapters;

import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.R;
import com.example.root.sens.data;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewHolderData extends ViewHolder {
    private final DecoView progressCircle;
    public ViewHolderData(View itemView) {
        super(itemView);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
    }

    public void bindType(ListItem item) {
        for(DayData d : data.user.getDayData()){
           if(isToday(d)){
               int size = data.user.getGoals().size();
               Goal[] goals = data.user.getGoals().get(size - 1).getGoals();
               int numofgoals = goals.length;

               int[] colors = {
                       Color.argb(255, 0, 0, 255),
                       Color.argb(255, 255, 0, 255),
                       Color.argb(255, 255, 0, 0),
                       Color.argb(255, 255, 255, 0),
                       Color.argb(255, 0, 255, 0),
                       Color.argb(255, 0, 255, 255)
               };

               float progresswidth = 24f;
               float totalprogresswidth = numofgoals*progresswidth; //120

               //https://github.com/bmarrdev/android-DecoView-charting
               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                       .setRange(0, 100, 100)
                       .setLineWidth(totalprogresswidth)
                       .build());

               float inset = -((progresswidth * (numofgoals - 1)) / 2);

               for (int i = 0; i < numofgoals; i++) {
                   progressCircle.addSeries(new SeriesItem.Builder(colors[i])
                           .setRange(0, 100, 33)
                           .setLineWidth(progresswidth)
                           .setInset(new PointF(inset, inset))
                           .build());

                   inset += progresswidth;
               }
               break;
           }
        }
    }

    private boolean isToday(DayData d) {
        Calendar todayCalendar = Calendar.getInstance();
        Calendar dCalendar = Calendar.getInstance();
        dCalendar.setTime(d.getStart_time());
        if(dCalendar.get(Calendar.DAY_OF_YEAR) == todayCalendar.get(Calendar.DAY_OF_YEAR) && dCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)){
            return true;
        }else{
            return false;
        }
    }
}
