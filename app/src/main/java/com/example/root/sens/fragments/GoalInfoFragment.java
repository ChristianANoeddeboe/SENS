package com.example.root.sens.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.User;
import com.example.root.sens.recyclers.viewholder.ViewHolderProgressBar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class GoalInfoFragment extends Fragment implements View.OnClickListener {
    TextView title, progressTextView, unit;
    ImageButton imageButton;
    LinearLayout header,goalbox;
    ActivityCategories goalType;
    ImageView icon;
    BarChart chart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boolean debug = true;
        View rootView = inflater.inflate(R.layout.fragment_goal_info, container, false);
        goalType = ActivityCategories.valueOf(getArguments().getString("goalType"));
        goalbox = rootView.findViewById(R.id.goalInfoBox_LinearLayout_container);
        title = rootView.findViewById(R.id.goalInfoBox_TextView_title);
        header = rootView.findViewById(R.id.typeGoalInfo_LinearLayout_header);
        progressTextView = rootView.findViewById(R.id.goalInfostatusTextView);
        unit = rootView.findViewById(R.id.goalInfoBox_Textview_unit);
        icon = rootView.findViewById(R.id.goalInfoIconImageView);
        chart = rootView.findViewById(R.id.goalInfoChart);
        imageButton = rootView.findViewById(R.id.typeGoalInfo_ImageButton_showmore);
        imageButton.setOnClickListener(this);
        icon.setImageResource(ViewHolderProgressBar.generateIcons(goalType));
        title.setText(goalType.toString());
        progressTextView.setText(getArguments().getString("progress"));
        unit.setText(getArguments().getString("unit"));
        setupColors(rootView);
        List<BarEntry> entries = new ArrayList<>();
        User temp = UserDAO.getInstance().getUserLoggedIn();
        RealmList<DayData> tempDayData = temp.getDayData();
        int counter = 0;
        for(DayData dayData : tempDayData){
            RealmList<Record> tempRecords = dayData.getRecords();
            for(Record record : tempRecords){
                if(record.getType().equals(goalType)){
                    if(record.getValue() > 1) {
                        entries.add(new BarEntry(counter, record.getValue()));
                        counter++;
                    }
                }
            }
        }

        BarDataSet dataSet = new BarDataSet(entries,"test"); // add entries to dataset
        BarData lineData = new BarData(dataSet);
        lineData.setDrawValues(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setData(lineData);
        chart.invalidate(); // refresh



        return rootView;
    }

    private void setupColors(View rootView) {
        int color = ViewHolderProgressBar.getGoalColor(goalType);
        color = ContextCompat.getColor(rootView.getContext(), color);
        goalbox.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        color = ViewHolderProgressBar.getGoalHeaderColor(goalType);
        color = ContextCompat.getColor(rootView.getContext(), color);
        header.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(imageButton)){
            getFragmentManager().popBackStack();
        }
    }
}
