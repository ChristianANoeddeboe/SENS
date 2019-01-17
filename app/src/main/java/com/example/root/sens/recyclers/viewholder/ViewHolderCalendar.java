package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.observers.MainFullScreenObserver;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ViewHolderCalendar extends ViewHolder {
    private final CompactCalendarView calendar;
    private TextView calendarMonth;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.US);
    private Context ctx;

    public ViewHolderCalendar(View itemView) {
        super(itemView);
        calendar = itemView.findViewById(R.id.compactcalendar_view);
        calendar.setCurrentDayBackgroundColor(Color.rgb(240, 240, 240));
        calendar.setCurrentSelectedDayBackgroundColor(Color.rgb(218, 218, 218));
        calendar.setCurrentSelectedDayIndicatorStyle(1);
        final String[] temp = new String[]{
                "M", "T", "O", "T", "F", "L", "S"
        };
        calendar.setDayColumnNames(temp);
        calendarMonth = itemView.findViewById(R.id.calendarMonth);
        calendarMonth.setText(dateFormatForMonth.format(calendar.getFirstDayOfCurrentMonth()));

        ctx = itemView.getContext();
    }

    public void bindType(OverviewListItem item) {
        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                fullScreenOverlayFragment(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendarMonth.setText(dateFormatForMonth.format(calendar.getFirstDayOfCurrentMonth()));
            }
        });
        Map<Date, Boolean> result = new UserManager().generateFulfilleGoalsMap();
        for (Date date : result.keySet()) {
            boolean tempRes = result.get(date).booleanValue();
            if (tempRes) {
                calendar.addEvent(new Event(Color.rgb(76, 175, 80), date.getTime(), R.string.NoData));
            } else {
                calendar.addEvent(new Event(Color.rgb(244, 57, 54), date.getTime(), R.string.NoData));
            }
        }
    }

    private void fullScreenOverlayFragment(Date dateClicked) {
        IUserManager userManager = new UserManager();
        MainFullScreenObserver observer = (MainFullScreenObserver) ctx;
        Map<ActivityCategories, Float> data = userManager.getDayData(dateClicked);
        if (data.isEmpty()) {
            observer.showFragment(null);
        }else {
            observer.showFragment(dateClicked);
        }
    }
}

