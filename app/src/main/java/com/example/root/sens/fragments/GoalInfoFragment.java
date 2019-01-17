package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.auxiliary.ResourceManagement;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GoalInfoFragment extends Fragment {
    TextView title;
    CardView goalbox, header;
    ActivityCategories goalType;
    BarChart chart;
    Button oneWeek, oneMonth, threeMonths;
    DateAxisFormatter dateAxisFormatter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_card, container, false);

        goalType = ActivityCategories.valueOf(getArguments().getString("goalType"));
        goalbox = rootView.findViewById(R.id.goalchart_cardview);
        title = rootView.findViewById(R.id.goalInfoBox_TextView_title);
        chart = rootView.findViewById(R.id.goalInfoChart);
        oneWeek = rootView.findViewById(R.id.goalInfo_Button_1week);
        oneMonth = rootView.findViewById(R.id.goalInfo_Button_1month);
        threeMonths = rootView.findViewById(R.id.goalInfo_Button_3month);
        header = rootView.findViewById(R.id.cardview_goalchart_header);

        title.setText(goalType.toString());

        goalbox.setOnClickListener((View v) -> getActivity().onBackPressed());

        oneWeek.setOnClickListener((View v) -> updateChart(generateData(generateData.oneWeekData)));

        oneMonth.setOnClickListener((View v) -> updateChart(generateData(generateData.oneMonthData)));

        threeMonths.setOnClickListener((View v) -> updateChart(generateData(generateData.threeMonthsData)));

        header.setBackgroundTintList(getActivity().getResources().getColorStateList(new ResourceManagement().getGoalColor(goalType)));

        setupChart();
        updateChart(generateData(generateData.oneWeekData));
        return rootView;
    }

    private void updateChart(List<BarEntry> data) {
        BarDataSet dataSet = new BarDataSet(data,"test"); // add entries to dataset
        BarData lineData = new BarData(dataSet);
        lineData.setDrawValues(false);
        chart.setData(lineData);
        chart.invalidate();
    }

    private void setupChart() {
        // Formatting X-axis to handle dates
        dateAxisFormatter = new DateAxisFormatter();
        chart.getXAxis().setValueFormatter(dateAxisFormatter);

        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false); // disable grid lines for the XAxis
        chart.getAxisLeft().setDrawGridLines(false); // disable grid lines for the left YAxis
        chart.getAxisRight().setDrawGridLines(false); // disable grid lines for the right YAxis
        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chart.animateY(500);
        chart.setDrawValueAboveBar(false);

        chart.setTouchEnabled(false);

        chart.invalidate(); // refresh
    }

    enum generateData{
        oneWeekData(7),
        oneMonthData(30),
        threeMonthsData(90);

        private int dates;

        public int getValue(){ return this.dates; }

        generateData(int dates){ this.dates = dates; }
    }

    private List<BarEntry> generateData(generateData len){
        // Using Java Calender to manage time, since
        // it's easier manipulated than Date.
        Calendar cal = Calendar.getInstance();
        cal.setTime ( new Date() );

        IUserManager userManager = new UserManager();

        List<BarEntry> entries = new ArrayList<>();

        for(int i = 0; i < len.getValue(); i++){
            Map<ActivityCategories, Float> tempDayData = userManager.getDayData(cal.getTime());
            dateAxisFormatter.setDateInMilis(cal.getTimeInMillis());
            entries.add(new BarEntry(i+30, tempDayData.get(goalType)));
//            entries.add(new BarEntry(cal.getTime().getDate(), tempDayData.get(goalType)));

            // subtracting one day from the calender.
            cal.add(Calendar.DATE, -1);
        }
        return entries;
    }
}
