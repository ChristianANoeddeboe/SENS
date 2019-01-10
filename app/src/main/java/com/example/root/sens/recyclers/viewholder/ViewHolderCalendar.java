package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.sens.activities.MainActivity;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.R;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.User;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.example.root.sens.observers.MainFullScreenFragmentObserver;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ViewHolderCalendar extends ViewHolder {
    private final CompactCalendarView calendar;
    private TextView calendarMonth;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.US);
    private View currentView;
    private Context ctx;

    public ViewHolderCalendar(View itemView) {
        super(itemView);
        calendar = itemView.findViewById(R.id.compactcalendar_view);
        calendar.setCurrentDayBackgroundColor(Color.rgb(240, 240, 240));
        calendar.setCurrentSelectedDayBackgroundColor(Color.rgb(218, 218, 218));
        calendar.setCurrentSelectedDayIndicatorStyle(1);
        String[] temp = new String[]{
                "M", "T", "O", "T", "F", "L", "S"
        };
        calendar.setDayColumnNames(temp);
        calendarMonth = itemView.findViewById(R.id.calendarMonth);
        calendarMonth.setText(dateFormatForMonth.format(calendar.getFirstDayOfCurrentMonth()));

        ctx = itemView.getContext();
        currentView = itemView.findViewById(R.id.main_a_coordinator_layout);
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
        HashMap<Date, Boolean> result = UserDAO.getInstance().userFulfilledGoals();
        for (Date date : result.keySet()) {
            boolean tempRes = result.get(date).booleanValue();
            if (tempRes) {
                calendar.addEvent(new Event(Color.rgb(76, 175, 80), date.getTime(), "test1234"));
            } else {
                calendar.addEvent(new Event(Color.rgb(244, 57, 54), date.getTime(), "test"));
            }
        }
    }

    private void fullScreenOverlayFragment(Date dateClicked) {
        UserDAO userDAO = UserDAO.getInstance();
        DayData dayData = userDAO.getDataSpecificDate(dateClicked);

        MainFullScreenFragmentObserver observer = new MainActivity();

        if (dayData == null) {
            Toast.makeText(ctx, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            observer.showFragment(dateClicked);
        }
    }
}

