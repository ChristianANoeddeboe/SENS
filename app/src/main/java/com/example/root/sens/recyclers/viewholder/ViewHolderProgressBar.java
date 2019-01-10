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
import com.example.root.sens.fragments.GoalInfoFragment;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import io.realm.RealmList;

public class ViewHolderProgressBar extends ViewHolder {
    private final DecoView progressCircle;
    private TextView progressTextView, title, unitTextview;
    private ImageView imageView;
    private LinearLayout header;
    private CardView goalbox;
    private ImageButton expandButton;
    private int type;
    private String goalType;

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
                if(v.equals(expandButton)) {
                    Bundle args = new Bundle();
                    args.putString("title",title.getText().toString());
                    args.putString("progress",progressTextView.getText().toString());
                    args.putString("unit",unitTextview.getText().toString());
                    args.putString("goalType",goalType);

                    Fragment f = new GoalInfoFragment();
                    f.setArguments(args);
                    FragmentManager manager = ((AppCompatActivity) viewGroup).getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.goalsContentContainer, f).addToBackStack(null).commit();
                }
            }
        });
        type = i;
    }

    public void bindType(OverviewListItem item) {
        DayData d = getNewestData();
        RealmList<Goal> goals = UserDAO.getInstance().getNewestGoal().getGoals();
        Goal currGoal = goals.get(type);
        goalType = currGoal.getType().toString();
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
