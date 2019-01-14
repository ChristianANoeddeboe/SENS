package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.example.root.sens.fragments.GoalInfoFragment;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.Date;

import io.realm.RealmList;

public class ViewHolderProgressBar extends ViewHolder {
    private final DecoView progressCircle;
    private TextView progressTextView, title, unitTextview;
    private ImageView imageView;
    private LinearLayout header;
    private CardView goalbox;
    private int type;
    private String goalType;
    private Date wantedDate;

    public ViewHolderProgressBar(View itemView, int i, Context viewGroup, Date wantedDate) {
        super(itemView);
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        progressTextView = itemView.findViewById(R.id.goalstatusTextView);
        imageView = itemView.findViewById(R.id.goalIconImageView);
        goalbox = itemView.findViewById(R.id.goalchart_cardview);
        header = itemView.findViewById(R.id.typegoal_LinearLayout_header);
        unitTextview = itemView.findViewById(R.id.goalbox_Textview_unit);
        title = itemView.findViewById(R.id.goalbox_TextView_title);
        this.wantedDate = wantedDate;

        goalbox.setOnClickListener((View v)->{
            Animation animationUp = AnimationUtils.loadAnimation(viewGroup, R.anim.slide_up);
            Animation animationDown = AnimationUtils.loadAnimation(viewGroup, R.anim.slide_down);

            Bundle args = new Bundle();
            args.putString("title",title.getText().toString());
            args.putString("progress",progressTextView.getText().toString());
            args.putString("unit",unitTextview.getText().toString());
            args.putString("goalType",goalType);

            Fragment f = new GoalInfoFragment();
            f.setArguments(args);
            FragmentManager fm = ((AppCompatActivity) viewGroup).getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.goalsContentContainer, f)
                    .addToBackStack(null)
                    .commit();

        });
        type = i;
    }

    public void bindType(OverviewListItem item) {
        UserDAO userDAO = UserDAO.getInstance();
        DayData dayData = userDAO.getDataSpecificDate(wantedDate);
        RealmList<Goal> goals = UserDAO.getInstance().getNewestGoal().getGoals();
        Goal currGoal = goals.get(type);
        int current = 0;
        int max = 1;
        goalType = currGoal.getType().toString();
        if(dayData != null){
            RealmList<Record> temp = dayData.getRecords();
            for(Record record : temp){
                if(record.getType().equals(currGoal.getType())){
                    current = (int) record.getValue();
                    break;
                }
            }

            max = currGoal.getValue();
            if(max > 0 ) {

                if(current > max){
                    current = max;
                }

            }

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
    //TODO: Move the two methods below in some utility class
    public static int getGoalHeaderColor(ActivityCategories curr) {
        switch (curr) {
            case Søvn:
                return R.color.restingHeaderColor;
            case Stå:
                return R.color.standingHeaderColor;
            case Gang:
                return R.color.walkingHeaderColor;
            case Cykling:
                return R.color.cyclingHeaderColor;
            case Træning:
                return R.color.exerciseHeaderColor;
            default:
                return R.color.white;
        }
    }

    public static int getGoalColor(ActivityCategories curr) {
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


    public static int generateIcons(ActivityCategories curr) {
        switch (curr) {
            case Søvn:
                return R.mipmap.icon_resting_inverted;
            case Stå:
                return R.mipmap.icon_standing_inverted;
            case Gang:
                return R.mipmap.icon_walking_inverted;
            case Cykling:
                return R.mipmap.icon_cycling_inverted;
            case Træning:
                return R.mipmap.icon_exercise_inverted;
        }
        return R.mipmap.award;
    }
}
