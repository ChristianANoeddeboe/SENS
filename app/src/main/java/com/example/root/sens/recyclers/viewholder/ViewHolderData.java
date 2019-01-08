package com.example.root.sens.recyclers.viewholder;

import android.graphics.Color;
import android.graphics.PointF;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.R;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.User;
import com.example.root.sens.fragments.interfaces.ListItem;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmList;

public class ViewHolderData extends ViewHolder {
    private final DecoView progressCircle;
    private TextView textView;
    private ImageView imageView;
    private int type;
    public ViewHolderData(View itemView, int i) {
        super(itemView);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        textView = itemView.findViewById(R.id.goalstatusTextView);
        imageView = itemView.findViewById(R.id.goalIconImageView);
        type = i;
    }

    public void bindType(ListItem item) {
        final int BACKGROUNDSERIESWIDTH = 10;
        DayData d = getNewestData();
        User activeUser = UserDAO.getInstance().getUserLoggedIn();
        RealmList<GoalHistory> g = activeUser.getGoals();
        //TODO: Actually loop through and check that the newest date
        RealmList<Goal> goals = activeUser.getGoals().get(0).getGoals();

        //https://github.com/bmarrdev/android-DecoView-charting
        progressCircle.addSeries(new SeriesItem.Builder(Color.argb(180, 218, 218, 218))
                .setRange(0, 100, 100)
                .setLineWidth(BACKGROUNDSERIESWIDTH)
                .build());
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
            generateIcons(currGoal, current);
            progressCircle.addSeries(new SeriesItem.Builder(getGoalColor(currGoal.getType()))
                    .setRange(0, max, current)
                    .setLineWidth(20)
                    .setInset(new PointF(0, 2))
                    .build());
        }
    }

    private int getGoalColor(ActivityCategories curr) {
        switch (curr) {
            case Resting:
                return Color.argb(255, 0, 150, 136);
            case Standing:
                return Color.argb(255, 63, 81, 181);
            case Walking:
                return Color.argb(255, 76, 175, 80);
            case Cycling:
                return Color.argb(255, 255, 152, 0);
            case Exercise:
                return Color.argb(244, 244, 67, 54);
            default:
                return Color.argb(255, 0, 0, 0);
        }
    }


    private void generateIcons(Goal curr, int currentGoalValue) {
        textView.setText(Integer.toString(currentGoalValue)+"/"+Integer.toString(curr.getValue()));
        switch (curr.getType()) {
            case Resting:
                imageView.setImageResource(R.mipmap.icon_resting);
                break;
            case Standing:
                imageView.setImageResource(R.mipmap.icon_standing);
                break;
            case Walking:
                imageView.setImageResource(R.mipmap.icon_walking);
                break;
            case Cycling:
                imageView.setImageResource(R.mipmap.icon_cycling);
                break;
            case Exercise:
                imageView.setImageResource(R.mipmap.icon_exercise);
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
