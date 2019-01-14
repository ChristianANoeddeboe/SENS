package com.example.root.sens.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Record;
import com.example.root.sens.recyclers.viewholder.ViewHolderProgressBar;
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

public class GoalInfoFragment extends Fragment implements View.OnClickListener {
    TextView title;
    ImageButton updateButton, backButton;
    CardView goalbox, header;
    ActivityCategories goalType;
    ImageView icon;
    BarChart chart;
    Button oneweek, onemonth, threemonths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boolean debug = true;
        View rootView = inflater.inflate(R.layout.fragment_graph_card, container, false);
        goalType = ActivityCategories.valueOf(getArguments().getString("goalType"));
        goalbox = rootView.findViewById(R.id.goalchart_cardview);
        title = rootView.findViewById(R.id.goalInfoBox_TextView_title);
        chart = rootView.findViewById(R.id.goalInfoChart);
        title.setText(goalType.toString());
        updateButton = rootView.findViewById(R.id.goalInfo_Button_editgoal);
        oneweek = rootView.findViewById(R.id.goalInfo_Button_1week);
        onemonth = rootView.findViewById(R.id.goalInfo_Button_1month);
        threemonths = rootView.findViewById(R.id.goalInfo_Button_3month);
        backButton = rootView.findViewById(R.id.typeGoalInfo_ImageButton_showless);
        header = rootView.findViewById(R.id.cardview_goalchart_header);

        goalbox.setOnClickListener((View v) ->{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction()
                    .remove(this)
                    .commit();
        });

        updateButton.setOnClickListener(this);

        oneweek.setOnClickListener(this);

        onemonth.setOnClickListener(this);

        threemonths.setOnClickListener(this);

        header.setBackgroundTintList(getActivity().getResources().getColorStateList(getGoalColor(goalType)));

        setupChart();
        updateChart(generateData(gendata.oneweekdata));
        Date d = new Date();
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

    @Override
    public void onClick(View v) {
        if(v.equals(backButton)){
            getFragmentManager().popBackStack();
        }else if( v.equals(oneweek)){
            updateChart(generateData(gendata.oneweekdata));
        }else if(v.equals(onemonth)){
            updateChart(generateData(gendata.onemonthdata));
        }else if(v.equals(threemonths)){
            updateChart(generateData(gendata.threemonthsdata));

        }
    }
    enum gendata{
        oneweekdata,onemonthdata,threemonthsdata
    }
    private List<BarEntry> generateData(gendata len){
        List<BarEntry> entries = new ArrayList<>();
        ArrayList<DayData> tempDayData = UserDAO.getInstance().getSortedDayData();
        Collections.reverse(tempDayData);
        int counter = 1;
        for(DayData dayData : tempDayData){
            RealmList<Record> tempRecords = dayData.getRecords();
            for(Record record : tempRecords){
                if(record.getType().equals(goalType) && record.getValue() > 1){
                    switch (len){
                        case oneweekdata:
                            if(counter < 7){
                                entries.add(new BarEntry(counter, record.getValue()));
                                counter++;
                            }else{
                                return entries;
                            }
                            break;
                        case onemonthdata:
                            if(counter < 30){
                                entries.add(new BarEntry(counter, record.getValue()));
                                counter++;
                            }else{
                                return entries;
                            }
                            break;
                        case threemonthsdata:
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

    private int getGoalColor(ActivityCategories curr) {
        switch (curr) {
            case Søvn:
                //return Color.argb(255, 0, 150, 136);
                return R.color.restingColor;
            case Stå:
                //return Color.argb(255, 63, 81, 181);
                return R.color.standingColor;
            case Gang:
                //return Color.argb(255, 76, 175, 80);
                return R.color.walkingColor;
            case Cykling:
                //return Color.argb(255, 255, 152, 0);
                return R.color.cyclingColor;
            case Træning:
                //return Color.argb(244, 244, 67, 54);
                return R.color.exerciseColor;
            default:
                return R.color.white;
        }
    }
}
