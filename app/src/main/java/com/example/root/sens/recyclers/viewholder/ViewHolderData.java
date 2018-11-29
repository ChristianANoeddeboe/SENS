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
import com.example.root.sens.dto.User;
import com.example.root.sens.fragments.interfaces.ListItem;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.Calendar;

import io.realm.RealmList;

public class ViewHolderData extends ViewHolder {
    private final DecoView progressCircle;
    private TextView exerciseText, standingText, walkingText, restText, cyclingText;
    private ImageView exerciseLogo, standingLogo, walkingLogo, restLogo, cyclingLogo;
    private LinearLayout layout;
    public ViewHolderData(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.iconContainer);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        /*
        exerciseText = itemView.findViewById(R.id.exerciselogotxt);
        standingText = itemView.findViewById(R.id.standinglogotxt);
        walkingText = itemView.findViewById(R.id.walkinglogotxt);
        restText = itemView.findViewById(R.id.restlogotxt);
        cyclingText = itemView.findViewById(R.id.cyclinglogotxt);

        exerciseLogo = itemView.findViewById(R.id.exerciselogo);
        standingLogo = itemView.findViewById(R.id.standinglogo);
        walkingLogo = itemView.findViewById(R.id.walkinglogo);
        restLogo = itemView.findViewById(R.id.restlogo);
        cyclingLogo = itemView.findViewById(R.id.cyclinglogo);
        */
    }

    public void bindType(ListItem item) {

        DayData d = getNewestData();
        User activeUser = UserDAO.getInstance().getUserLoggedIn();
        RealmList<GoalHistory> g = activeUser.getGoals();
        int size = activeUser.getGoals().size();
        //TODO: Actually loop through and check that the newest date
        RealmList<Goal> goals = activeUser.getGoals().get(0).getGoals();
        int numofgoals = goals.size();

        int emptyGoals = 0;

        for(Goal curr : goals) {
            if(curr.getValue() == 0) {
                emptyGoals++;
            }
        }

        float progresswidth = 24f;
        float totalprogresswidth = (numofgoals-emptyGoals)*progresswidth; //120



        //https://github.com/bmarrdev/android-DecoView-charting
        progressCircle.addSeries(new SeriesItem.Builder(Color.argb(180, 218, 218, 218))
                .setRange(0, 100, 100)
                .setLineWidth(totalprogresswidth)
                .build());

        float inset = -((progresswidth * ((numofgoals-emptyGoals) - 1)) / 2);
        int max, current;

        for (int i = 0; i < numofgoals; i++) {
            Goal currGoal = goals.get(i);
            max = currGoal.getValue();
            current = (int) d.getRecords().get(i).getValue();
            if(max > 0) {
                if(current > max) {
                    current = max;
                }
                //addGoalIcon(currGoal, current);
                generateIcons(currGoal,current);
                progressCircle.addSeries(new SeriesItem.Builder(getGoalColor(currGoal.getType()))
                        .setRange(0, max, current)
                        .setLineWidth(progresswidth)
                        .setInset(new PointF(inset, inset))
                        .build());

                inset += progresswidth;
            } else {
                //removeGoalIcon(currGoal.getType());
            }

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
        ImageView temp = new ImageView(layout.getContext());
        LinearLayout ll = new LinearLayout(layout.getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 8, 0);


        TextView t = new TextView(layout.getContext());
        t.setText(Integer.toString(currentGoalValue)+"/"+Integer.toString(curr.getValue()));
        t.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.addView(t);

        switch (curr.getType()) {
            case Resting:
                temp.setImageResource(R.mipmap.icon_resting);
                break;
            case Standing:
                temp.setImageResource(R.mipmap.icon_standing);
                break;
            case Walking:
                temp.setImageResource(R.mipmap.icon_walking);
                break;
            case Cycling:
                temp.setImageResource(R.mipmap.icon_cycling);
                break;
            case Exercise:
                temp.setImageResource(R.mipmap.icon_exercise);
                break;
        }
        ll.addView(temp);
        layout.addView(ll,layoutParams);
    }


    /*
    private void chooseGoalIcons(RealmList<Goal> allGoals, DayData dayGoals) {
        for(Goal curr : allGoals) {
            if(curr.getValue() > 0) {
                double currentGoalValue = dayGoals.getGoalData()[curr.getType().getTypeId()-1];
                addGoalIcon(curr, currentGoalValue);
            } else {
                removeGoalIcon(curr);
            }
        }
    }*/

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

    private boolean isToday(DayData d) {
        Calendar todayCalendar = Calendar.getInstance();
        Calendar dCalendar = Calendar.getInstance();
        dCalendar.setTime(d.getEnd_time());
        if(dCalendar.get(Calendar.DAY_OF_YEAR) == todayCalendar.get(Calendar.DAY_OF_YEAR) && dCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)){
            return true;
        }else{
            return false;
        }
    }
}
