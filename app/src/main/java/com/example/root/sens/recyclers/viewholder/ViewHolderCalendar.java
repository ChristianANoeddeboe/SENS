package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.ApplicationSingleton;
import com.example.root.sens.R;
import com.example.root.sens.activities.MainActivity;
import com.example.root.sens.dao.SensDAO;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.observers.MainFullScreenObserver;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ViewHolderCalendar extends ViewHolder {
    private final CompactCalendarView calendar;
    private TextView calendarMonth;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.US);
    private Context ctx;
    private final String TAG = ViewHolderCalendar.class.getSimpleName();

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
        ctx = itemView.getContext();
        calendar.setCurrentDate(MonthSingleton.getInstance().getCurrentMonth());
        calendarMonth.setText(dateFormatForMonth.format(MonthSingleton.getInstance().getCurrentMonth()));

    }

    public void bindType(OverviewListItem item) {
        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                MonthSingleton.getInstance().setCurrentMonth(dateClicked);
                fullScreenOverlayFragment(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                MainFullScreenObserver observer = (MainFullScreenObserver) ctx;
                calendarMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                MonthSingleton.getInstance().setCurrentMonth(firstDayOfNewMonth);
                Calendar c1 = Calendar.getInstance();
                c1.setTime(firstDayOfNewMonth);
                c1.add(Calendar.DATE, 14);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(firstDayOfNewMonth);
                c2.add(Calendar.DATE, 30);
                SensDAO.getInstance().getDataMonth(new UserManager().getUserLoggedIn().getPatientKey(), 14, c1.getTime(), c2.getTime(), true);

                observer.showDataFetchSnack();


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
        boolean dayDdata = userManager.getDayData(dateClicked).isEmpty();
        boolean goalData = userManager.getGoals(dateClicked).isEmpty();

        MainFullScreenObserver observer = (MainFullScreenObserver) ctx;
        observer.showFragment(dayDdata, goalData, dateClicked);
    }
}

