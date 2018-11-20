package com.example.root.sens.adapters;

import android.graphics.Color;
import android.graphics.PointF;
import android.view.View;
import android.widget.TextView;

import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.R;
import com.example.root.sens.data;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeData;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.Calendar;

public class ViewHolderData extends ViewHolder {
    private final TextView mTextView;
    private final DecoView progressCircle;
    public ViewHolderData(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.header);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
    }

    public void bindType(ListItem item) {
        Goal g = ((TypeData) item).getG();
        for(DayData d : data.user.getDayData()){
           if(isToday(d)){
               Double temp;
               switch (g.getType().getTypeId()){
                   case 1:
                       temp = d.getResting();
                       break;
                   case 2:
                       temp = d.getStanding();
                       break;
                   case 3:
                       temp = d.getWalking();
                       break;
                   case 4:
                       temp = d.getCycling();
                       break;
                   case 5:
                       temp = d.getExercise();
                       break;
                   default:
                       temp = new Double(-1.0);
                       break;
               }
               mTextView.setText(g.getType().getName()+" : "+temp.intValue()+"/"+g.getValue()+" timer");
               //https://github.com/bmarrdev/android-DecoView-charting
               PointF point = new PointF(-20f, -20f);

               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                       .setRange(0, 100, 100)
                       .setLineWidth(64f)
                       .build());

               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                       .setRange(0, 100, 33)
                       .setLineWidth(24f)
                       .setInset(point)
                       .build());
               point = new PointF(point.x+20, point.y+20);

               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 0, 0, 255))
                       .setRange(0, 100, 50)
                       .setLineWidth(24f)
                       .setInset(point)
                       .build());

               point = new PointF(point.x+20, point.y+20);
               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 255, 0, 0))
                       .setRange(0, 100, 75)
                       .setLineWidth(24f)
                       .setInset(point)
                       .build());

               /*
               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                       .setRange(0, 100, 100)
                       .setInitialVisibility(true)
                       .setLineWidth(32f)
                       .build());
               progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                       .setRange(0, 1440, temp.intValue())
                       .setLineWidth(32f)
                       .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_OUTER, Color.parseColor("#22000000"), 0.4f))
                       .build());
               */




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
