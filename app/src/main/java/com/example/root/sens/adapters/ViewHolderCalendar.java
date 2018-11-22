package com.example.root.sens.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.root.sens.DTO.DayData;
import com.example.root.sens.DTO.Goal;
import com.example.root.sens.DTO.GoalHistory;
import com.example.root.sens.R;
import com.example.root.sens.data;
import com.example.root.sens.utilities.EpochConverter;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeCalendar;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

public class ViewHolderCalendar extends ViewHolder {
    private final CompactCalendarView calendar;

    public ViewHolderCalendar(View itemView) {
        super(itemView);
        calendar = itemView.findViewById(R.id.compactcalendar_view);
    }

    public void bindType(ListItem item) {
        for(DayData d : data.user.getDayData()){
            long timeDelta = -1;
            GoalHistory temp = null;
            for(GoalHistory g : data.user.getGoals()){
                long temp2 = d.getStart_time().getTime() - g.getDate().getTime();
                if((temp2 < timeDelta && temp2 >= 0)||timeDelta == -1) {
                    timeDelta = temp2;
                    temp = g;
                }
            }
            if(temp != null){
                boolean completed = false;
                for(Goal g : temp.getGoals()){
                    completed = false;
                    switch(g.getType().getTypeId()){ //TODO: Probaly should do so that we can map daydata categories and goaltypes
                        case 1:
                            if(d.getResting() >= g.getValue()){
                                completed = true;
                            }
                            break;
                        case 2:
                            if(d.getStanding() >= g.getValue()){
                                completed = true;
                            }
                            break;
                        case 3:
                            if(d.getWalking() >= g.getValue()){
                                completed = true;
                            }
                            break;
                        case 4:
                            if(d.getCycling() >= g.getValue()){
                                completed = true;
                            }
                            break;
                        case 5:
                            if(d.getExercise() >= g.getValue()){
                                completed = true;
                            }
                            break;
                        default:
                            break;
                    }
                    if(!completed){
                        calendar.addEvent(new Event(Color.RED, d.getEnd_time().getTime(), "test"));
                        break;
                    }
                }
                if(completed){
                    calendar.addEvent(new Event(Color.GREEN, d.getEnd_time().getTime(), "test1234"));
                }
            }

        }
       // calendar.addEvent(new Event(Color.GREEN,1542530497*1000L,"Tesaasdt")); // This should be done dynamically

    }
}
