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
            for(GoalHistory g : data.user.getGoals()){
                if(g.getDate().)
            }

            calendar.addEvent(new Event(Color.GREEN,EpochConverter.getInstance().convertDate()));
        }
        calendar.addEvent(new Event(Color.GREEN,1542530497*1000L,"Tesaasdt")); // This should be done dynamically

    }
}
