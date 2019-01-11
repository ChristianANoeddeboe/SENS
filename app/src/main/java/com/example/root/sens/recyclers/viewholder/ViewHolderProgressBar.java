package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.Record;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.github.mikephil.charting.charts.BarChart;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.Date;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;

public class ViewHolderProgressBar extends ViewHolder {
    private final DecoView progressCircle;
    private TextView progressTextView, title, unitTextview;
    private ImageView imageView;
    private LinearLayout header;
    private CardView goalbox;
    private int type;
    private BarChart chart;
    private String goalType;
    private Date wantedDate;

    public ViewHolderProgressBar(View itemView, int i, Context viewGroup, Date wantedDate) {
        super(itemView);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        progressTextView = itemView.findViewById(R.id.goalstatusTextView);
        imageView = itemView.findViewById(R.id.goalIconImageView);
        goalbox = itemView.findViewById(R.id.goalbox_LinearLayout_container);
        header = itemView.findViewById(R.id.typegoal_LinearLayout_header);
        unitTextview = itemView.findViewById(R.id.goalbox_Textview_unit);
        title = itemView.findViewById(R.id.goalbox_TextView_title);
        chart = itemView.findViewById(R.id.cardviewGoalInfoChart);
        this.wantedDate = wantedDate;

        goalbox.setOnClickListener((View v)->{
            Animation animationUp = AnimationUtils.loadAnimation(viewGroup, R.anim.slide_up);
            Animation animationDown = AnimationUtils.loadAnimation(viewGroup, R.anim.slide_down);

            if(chart.isShown()){
                chart.setVisibility(View.GONE);
                chart.startAnimation(animationUp);
            }
            else{
                chart.setVisibility(View.VISIBLE);
                chart.startAnimation(animationDown);
            }

        });

        setupChart();
        updateChart(generateData(gendata.oneweekdata));
        type = i;
    }

    public void bindType(OverviewListItem item) {
        UserDAO userDAO = UserDAO.getInstance();
        DayData dayData = userDAO.getDataSpecificDate(wantedDate);
        RealmList<Goal> goals = UserDAO.getInstance().getNewestGoal().getGoals();
        Goal currGoal = goals.get(type);
        goalType = currGoal.getType().toString();
        RealmList<Record> temp = dayData.getRecords();
        int current = 0;
        for(Record record : temp){
            if(record.getType().equals(currGoal.getType())){
                current = (int) record.getValue();
                break;
            }
        }

        int max = currGoal.getValue();
        if(max > 0 ) {

            if(current > max){
                current = max;
            }
            int color = getGoalColor(currGoal.getType());

            color = ContextCompat.getColor(itemView.getContext(), color);
            goalbox.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

            progressTextView.setText(Integer.toString(current)+"/"+Integer.toString(currGoal.getValue()));
            unitTextview.setText("minutes");

            title.setText(currGoal.getType().toString());

            imageView.setImageResource(generateIcons(currGoal.getType()));

            progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 255, 255, 255))
                    .setRange(0, max, current)
                    .setLineWidth(20)
                    .setInset(new PointF(0, 2))
                    .build());
        }
    }
    //TODO: Move the two methods below in some utility class
    public static int getGoalHeaderColor(ActivityCategories curr) {
        switch (curr) {
            case Resting:
                return R.color.restingHeaderColor;
            case Standing:
                return R.color.standingHeaderColor;
            case Walking:
                return R.color.walkingHeaderColor;
            case Cycling:
                return R.color.cyclingHeaderColor;
            case Exercise:
                return R.color.exerciseHeaderColor;
            default:
                return R.color.white;
        }
    }

    public static int getGoalColor(ActivityCategories curr) {
        switch (curr) {
            case Resting:
                //return Color.argb(255, 0, 150, 136);
                return R.color.restingColor;
            case Standing:
                //return Color.argb(255, 63, 81, 181);
                return R.color.standingColor;
            case Walking:
                //return Color.argb(255, 76, 175, 80);
                return R.color.walkingColor;
            case Cycling:
                //return Color.argb(255, 255, 152, 0);
                return R.color.cyclingColor;
            case Exercise:
                //return Color.argb(244, 244, 67, 54);
                return R.color.exerciseColor;
            default:
                return R.color.white;
        }
    }


    public static int generateIcons(ActivityCategories curr) {
        switch (curr) {
            case Resting:
                return R.mipmap.icon_resting_inverted;
            case Standing:
                return R.mipmap.icon_standing_inverted;
            case Walking:
                return R.mipmap.icon_walking_inverted;
            case Cycling:
                return R.mipmap.icon_cycling_inverted;
            case Exercise:
                return R.mipmap.icon_exercise_inverted;
        }
        return R.mipmap.award;
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

    private void updateChart(List<BarEntry> data) {
        BarDataSet dataSet = new BarDataSet(data,"test"); // add entries to dataset
        BarData lineData = new BarData(dataSet);
        lineData.setDrawValues(false);
        chart.setData(lineData);
        chart.invalidate();
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
}
