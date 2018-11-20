package com.example.root.sens.adapters;

import android.graphics.Color;
import android.graphics.PointF;
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
               ArrayList<Goal> goals = data.user.getGoals();
               int numofgoals = goals.size();
               PointF point = new PointF(0f, 0f);
               int[] colors = {
                       Color.argb(255, 0, 0, 255),
                       Color.argb(255, 255, 0, 255),
                       Color.argb(255, 255, 0, 0),
                       Color.argb(255, 255, 255, 0),
                       Color.argb(255, 0, 255, 0),
                       Color.argb(255, 0, 255, 255)
               };

               Float progresswidth = 24f;
               Float totalprogresswidth = numofgoals*progresswidth; //120

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

               /*
               Float test = numofgoals*(progresswidth/numofgoals);

               for(int i = 0 ; i < numofgoals ; i++) {
                   progressCircle.addSeries(new SeriesItem.Builder(colors[i])
                           .setRange(0, 100, 33)
                           .setLineWidth(progresswidth)
                           .setInset(new PointF(point.x + i*progresswidth - test, point.y + i*progresswidth - test))
                           .build());
               }*/


               break;
           }
        }
    }

    private boolean isToday(DayData d) {
        Calendar todayCalendar = Calendar.getInstance();
        Calendar dCalendar = Calendar.getInstance();
        dCalendar.setTime(d.getStart_time());
        if(dCalendar.DAY_OF_YEAR == todayCalendar.DAY_OF_YEAR && dCalendar.YEAR == todayCalendar.YEAR){
            return true;
        }else{
            return false;
        }
    }
}
