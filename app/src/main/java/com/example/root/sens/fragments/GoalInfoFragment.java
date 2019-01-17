package com.example.root.sens.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.auxiliary.ResourceManagement;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class GoalInfoFragment extends Fragment {
    TextView title, subTitle;
    CardView goalbox;
    LinearLayout header;
    ActivityCategories goalType;
    GraphView graphView;
    Button oneWeek, twoWeeks, oneMonth;
    Date startDate, endDate, pickedDate;
    int color;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_card, container, false);

        // Getting the selected data
        pickedDate = (Date) getArguments().getSerializable("date");
        if(pickedDate == null){
            pickedDate = new Date();
        }

        goalType = ActivityCategories.valueOf(getArguments().getString("goalType"));
        color = ContextCompat.getColor(getContext(), new ResourceManagement().getGoalColor(goalType));

        goalbox = rootView.findViewById(R.id.goalchart_cardview);
        title = rootView.findViewById(R.id.goalInfoBox_TextView_title);
        subTitle = rootView.findViewById(R.id.textViewGraphView);
        graphView = rootView.findViewById(R.id.goalInfoChart);
        oneWeek = rootView.findViewById(R.id.goalInfo_Button_1week);
        twoWeeks = rootView.findViewById(R.id.goalInfo_Button_2weeks);
        oneMonth = rootView.findViewById(R.id.goalInfo_Button_1month);
        header = rootView.findViewById(R.id.typeGoalInfo_LinearLayout_header);

        title.setText(goalType.toString());

        goalbox.setOnClickListener((View v) -> getActivity().onBackPressed());

        oneWeek.setOnClickListener((View v) -> showGraph(7));

        twoWeeks.setOnClickListener((View v) -> showGraph(14));

        oneMonth.setOnClickListener((View v) -> showGraph(30));

        header.setBackgroundColor(color);
        oneWeek.setTextColor(color);
        twoWeeks.setTextColor(color);
        oneMonth.setTextColor(color);

        initializeGraph();
        showGraph(7);

        return rootView;
    }

    private void initializeGraph(){
        graphView.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(graphView.getContext(),
                        new SimpleDateFormat("dd'/'MM",
                                new Locale("da"))));

        graphView.getGridLabelRenderer().setNumHorizontalLabels(7); // More than seven labels i mashed together

        if(goalType == ActivityCategories.Skridt){
            graphView.getGridLabelRenderer().setVerticalAxisTitle("Skridt");
        }else {
            graphView.getGridLabelRenderer().setVerticalAxisTitle("Tid i minutter");
        }

        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(7);
    }

    private void showGraph(int numberOfDays){
        graphView.removeAllSeries();

        DataPoint[] dataPoints = generateData(numberOfDays);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);

        series.setSpacing(12);
        series.setColor(color);

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("d'.' MMMM", new Locale("da"));
        subTitle.setText("Viser data for " + numberOfDays + " dage.\n Den "
                + simpleDateFormat.format(startDate) + " til den " + simpleDateFormat.format(endDate));

        Viewport graphViewport = graphView.getViewport();
        graphViewport.setMinX(dataPoints[0].getX() + 1);
        graphViewport.setMaxX(dataPoints[numberOfDays-1].getX());
        graphViewport.setXAxisBoundsManual(true);

        graphView.addSeries(series);
    }

    private DataPoint[] generateData(int numberOfDays) {
        // Using Java Calender to manage time, since
        // it's easier manipulated than Date.
        Calendar cal = Calendar.getInstance();
        cal.setTime(pickedDate);
        cal.add(Calendar.DATE, -numberOfDays);

        DataPoint[] dataPoints = new DataPoint[numberOfDays];

        IUserManager userManager = new UserManager();

        startDate = cal.getTime();
        for (int i = 0; i < numberOfDays; i++) {
            Date date = cal.getTime();

            Map<ActivityCategories, Float> tempDayData = userManager.getDayData(date);

            dataPoints[i] = new DataPoint(date, tempDayData.get(goalType) == null ? 0 : tempDayData.get(goalType));

            // subtracting one day from the calender.
            cal.add(Calendar.DATE, 1);
        }
        endDate = cal.getTime();

        return dataPoints;
    }
}
