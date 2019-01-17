package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
    TextView title;
    CardView goalbox;
    LinearLayout header;
    ActivityCategories goalType;
    GraphView graphView;
    Button oneWeek, oneMonth, threeMonths;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_card, container, false);

        goalType = ActivityCategories.valueOf(getArguments().getString("goalType"));
        goalbox = rootView.findViewById(R.id.goalchart_cardview);
        title = rootView.findViewById(R.id.goalInfoBox_TextView_title);
        graphView = rootView.findViewById(R.id.goalInfoChart);
        oneWeek = rootView.findViewById(R.id.goalInfo_Button_1week);
        oneMonth = rootView.findViewById(R.id.goalInfo_Button_2weeks);
        threeMonths = rootView.findViewById(R.id.goalInfo_Button_1month);
        header = rootView.findViewById(R.id.typeGoalInfo_LinearLayout_header);

        title.setText(goalType.toString());

        goalbox.setOnClickListener((View v) -> getActivity().onBackPressed());

        oneWeek.setOnClickListener((View v) ->  initGraph(graphView, 7));

        oneMonth.setOnClickListener((View v) ->  initGraph(graphView, 14));

        threeMonths.setOnClickListener((View v) ->  initGraph(graphView,30));

        header.setBackgroundResource(new ResourceManagement().getGoalColor(goalType));

        initGraph(graphView, 7);

        return rootView;
    }

    public void initGraph(GraphView graph, int numberOfDays) {

        DataPoint[] dataPoints = generateData(numberOfDays);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
        graph.addSeries(series);

        // Setting the X-label formatter to handle dates.
        graph.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(graph.getContext(),
                        new SimpleDateFormat("dd'/'MM",
                                new Locale("da"))));

        graph.getGridLabelRenderer().setNumHorizontalLabels(7); // More than seven labels i mashed together

        // Modifieing graph view
        Viewport graphViewport = graph.getViewport();
        graphViewport.setMinX(dataPoints[0].getX());
        graphViewport.setMaxX(dataPoints[numberOfDays-1].getX());
        graphViewport.setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not nessecary
        graph.getGridLabelRenderer().setHumanRounding(false);

        series.setSpacing(25); // 25% spacing between bars
    }

    public void updateGraph(GraphView graph, int numberOfDays){

    }

    private DataPoint[] generateData(int numberOfDays) {
        // Using Java Calender to manage time, since
        // it's easier manipulated than Date.
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -numberOfDays);

        DataPoint[] dataPoints = new DataPoint[numberOfDays];

        IUserManager userManager = new UserManager();

        for (int i = 0; i < numberOfDays; i++) {
            Date date = cal.getTime();

            Map<ActivityCategories, Float> tempDayData = userManager.getDayData(date);

            dataPoints[i] = new DataPoint(date, tempDayData.get(goalType) == null ? 0 : tempDayData.get(goalType));

            // subtracting one day from the calender.
            cal.add(Calendar.DATE, 1);
        }

        return dataPoints;
    }
}
