package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.auxiliary.ResourceManagement;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Record;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class GoalInfoFragment extends Fragment {
    TextView title;
    CardView goalbox, header;
    ActivityCategories goalType;
    BarChart chart;
    Button oneWeek, oneMonth, threeMonths;

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

        oneWeek.setOnClickListener((View v) ->{
            updateChart(generateData(generateData.oneWeekData));
        });

        oneMonth.setOnClickListener((View v) ->{
            updateChart(generateData(generateData.oneMonthData));
        });

        threeMonths.setOnClickListener((View v) ->{
            updateChart(generateData(generateData.threeMonthsData));
        });

        // TODO: This is also deprecated
        header.setBackgroundTintList(getActivity().getResources().getColorStateList(new ResourceManagement().getGoalColor(goalType)));

        setupChart();
        updateChart(generateData(generateData.oneWeekData));
        UserDAO.getInstance().getDataSpecificDate(new Date());
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
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false); // disable grid lines for the XAxis
        chart.getAxisLeft().setDrawGridLines(false); // disable grid lines for the left YAxis
        chart.getAxisRight().setDrawGridLines(false); // disable grid lines for the right YAxis
        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chart.animateY(2000);
        chart.setDrawValueAboveBar(false);


        chart.setTouchEnabled(false);

        chart.invalidate(); // refresh
    }

    enum generateData {
        oneWeekData, oneMonthData, threeMonthsData
    }
    private List<BarEntry> generateData(generateData len){
        List<BarEntry> entries = new ArrayList<>();
        ArrayList<DayData> tempDayData = UserDAO.getInstance().getSortedDayData();
        Collections.reverse(tempDayData);
        int counter = 1;
        for(DayData dayData : tempDayData){
            RealmList<Record> tempRecords = dayData.getRecords();
            for(Record record : tempRecords){
                if(record.getType().equals(goalType) && record.getValue() > 1){
                    switch (len){
                        case oneWeekData:
                            if(counter < 7){
                                entries.add(new BarEntry(counter, record.getValue()));
                                counter++;
                            }else{
                                return entries;
                            }
                            break;
                        case oneMonthData:
                            if(counter < 30){
                                entries.add(new BarEntry(counter, record.getValue()));
                                counter++;
                            }else{
                                return entries;
                            }
                            break;
                        case threeMonthsData:
                            if(counter < 90){
                                entries.add(new BarEntry(counter, record.getValue()));
                                counter++;
                            }else{
                                return entries;
                            }
                            break;
                    }
                    break;
                }
            }
        }
        return entries;
    }
}
