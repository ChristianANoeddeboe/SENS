package com.example.root.sens.adapters;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.R;
import com.example.root.sens.data;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeData;

import java.util.Calendar;
import java.util.Date;

public class ViewHolderData extends ViewHolder {
    private final TextView mTextView;
    private final ProgressBar mProgressBar;
    public ViewHolderData(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.header);
        mProgressBar = itemView.findViewById(R.id.progessbar);
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
               mProgressBar.setMax(g.getValue());
               mProgressBar.setProgress(temp.intValue());

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
