package com.example.root.sens.recyclers.viewholder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.Record;
import com.example.root.sens.fragments.AboutFragment;
import com.example.root.sens.fragments.HistoryFragment;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import io.realm.RealmList;

public class ViewHolderProgressBar extends ViewHolder {
    private final DecoView progressCircle;
    private TextView progressTextView, title, unitTextview;
    private ImageView imageView;
    private LinearLayout goalbox, header;
    private ImageButton expandButton;
    private int type;
    public ViewHolderProgressBar(View itemView, int i, Context viewGroup) {
        super(itemView);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        progressTextView = itemView.findViewById(R.id.goalstatusTextView);
        imageView = itemView.findViewById(R.id.goalIconImageView);
        goalbox = itemView.findViewById(R.id.goalbox_LinearLayout_container);
        header = itemView.findViewById(R.id.typegoal_LinearLayout_header);
        unitTextview = itemView.findViewById(R.id.goalbox_Textview_unit);
        title = itemView.findViewById(R.id.goalbox_TextView_title);
        expandButton = itemView.findViewById(R.id.typegoal_ImageButton_showmore);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new AboutFragment();
                FragmentManager manager = ((AppCompatActivity)viewGroup).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.test1234,f).commit();
                //myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,f).commit();
                Log.d("test1234", "onClick: aa");
            }
        });
        type = i;
    }

    public void bindType(OverviewListItem item) {
        final int BACKGROUNDSERIESWIDTH = 10;
        DayData d = getNewestData();
        RealmList<Goal> goals = UserDAO.getInstance().getNewestGoal().getGoals();



        //https://github.com/bmarrdev/android-DecoView-charting
        /*progressCircle.addSeries(new SeriesItem.Builder(Color.argb(180, 218, 218, 218))
                .setRange(0, 100, 100)
                .setLineWidth(BACKGROUNDSERIESWIDTH)
                .build());*/
        Goal currGoal = goals.get(type);
        RealmList<Record> temp = d.getRecords();
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

            color = getGoalHeaderColor(currGoal.getType());

            color = ContextCompat.getColor(itemView.getContext(), color);

            header.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

            generateIcons(currGoal, current);
            progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 255, 255, 255))
                    .setRange(0, max, current)
                    .setLineWidth(20)
                    .setInset(new PointF(0, 2))
                    .build());
        }
    }

    private int getGoalHeaderColor(ActivityCategories curr) {
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

    private int getGoalColor(ActivityCategories curr) {
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


    private void generateIcons(Goal curr, int currentGoalValue) {
        progressTextView.setText(Integer.toString(currentGoalValue)+"/"+Integer.toString(curr.getValue()));
        unitTextview.setText("meter");
        title.setText(curr.getType().toString());
        int color = ContextCompat.getColor(itemView.getContext(), R.color.white);
        progressTextView.setTextColor(color);
        unitTextview.setTextColor(color);
        title.setTextColor(color);
        switch (curr.getType()) {
            case Resting:
                imageView.setImageResource(R.mipmap.icon_resting_inverted);
                break;
            case Standing:
                imageView.setImageResource(R.mipmap.icon_standing_inverted);
                break;
            case Walking:
                imageView.setImageResource(R.mipmap.icon_walking_inverted);
                break;
            case Cycling:
                imageView.setImageResource(R.mipmap.icon_cycling_inverted);
                break;
            case Exercise:
                imageView.setImageResource(R.mipmap.icon_exercise_inverted);
                break;
        }
    }

    private DayData getNewestData() {

        RealmList<DayData> daydata = UserDAO.getInstance().getUserLoggedIn().getDayData();
        DayData data = daydata.get(0);
        DayData temp;
        for(int i = 0 ; i < daydata.size()-1 ; i++) {
            temp = daydata.get(i);
            if(temp.getEnd_time().getTime() > data.getEnd_time().getTime()) {
                data = temp;
            }
        }
        return data;
    }

}
