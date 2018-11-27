package com.example.root.sens.adapters;

import android.graphics.Color;
import android.view.View;

import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.R;
import com.example.root.sens.data;
import com.example.root.sens.dto.User;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

public class ViewHolderCalendar extends ViewHolder {
    private final CompactCalendarView calendar;

    public ViewHolderCalendar(View itemView) {
        super(itemView);
        calendar = itemView.findViewById(R.id.compactcalendar_view);
        calendar.setCurrentDayBackgroundColor(Color.rgb(240,240,240));
        calendar.setCurrentSelectedDayBackgroundColor(Color.rgb(218,218,218));
        calendar.setCurrentSelectedDayIndicatorStyle(0);
        String[] temp = new String[]{
                "M","T","O","T","F","L","S"
        };
        calendar.setDayColumnNames(temp);

    }

    public void bindType(ListItem item) {
        User activeUser = UserDAO.getInstance().getUserLoggedIn();
        for(DayData d : activeUser.getDayData()){
            long timeDelta = -1;
            GoalHistory temp = null;
            /**
             * Find the smallest difference which is positive
             */
            for(GoalHistory g : activeUser.getGoals()){
                long temp2 = d.getStart_time().getTime() - g.getDate().getTime();
                if((temp2 < timeDelta && temp2 >= 0)||timeDelta == -1) {
                    timeDelta = temp2;
                    temp = g;
                }
            }
            //If temp is null then we did not find a valid match
            if(temp != null){
                boolean completed = false;
                for(Goal g : temp.getGoals()){
                    completed = false;
                    for(Record r : d.getRecords()){ // Check if the user completed all its goals
                        if(r.getType().equals(g.getType())){
                            if(r.getValue() >= g.getValue()){
                                completed = true;
                                break;
                            }
                        }
                    }
                    if(!completed){
                        calendar.addEvent(new Event(Color.rgb(244,57,54), d.getEnd_time().getTime(), "test"));
                        break;
                    }
                }
                if(completed){
                    calendar.addEvent(new Event(Color.rgb(76,175,80), d.getEnd_time().getTime(), "test1234"));
                }
            }

        }
       // calendar.addEvent(new Event(Color.GREEN,1542530497*1000L,"Tesaasdt")); // This should be done dynamically

    }
}
