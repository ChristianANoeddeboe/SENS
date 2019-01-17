package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.auxiliary.ResourceManagement;
import com.example.root.sens.fragments.GoalInfoFragment;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.Date;
import java.util.Map;

public class ViewHolderProgressBar extends ViewHolder {
    private final DecoView progressCircle;
    private TextView progressTextView, title;
    private ImageView imageView;
    private CardView goalbox;
    private LinearLayout header;
    private ActivityCategories activityCategory;
    private Date wantedDate;
    private Context ctx;

    public ViewHolderProgressBar(View itemView, ActivityCategories activityCategory, Context viewGroup, Date wantedDate) {
        super(itemView);
        this.wantedDate = wantedDate;
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        progressTextView = itemView.findViewById(R.id.goalstatusTextView);
        imageView = itemView.findViewById(R.id.goalIconImageView);
        goalbox = itemView.findViewById(R.id.goalchart_cardview);
        header = itemView.findViewById(R.id.typegoal_LinearLayout_header);
        title = itemView.findViewById(R.id.goalbox_TextView_title);
        ctx = viewGroup;

        goalbox.setOnClickListener((View v)->{
            Bundle args = new Bundle();
            args.putString("title",title.getText().toString());
            args.putString("progress",progressTextView.getText().toString());
            args.putString("goalType", String.valueOf(activityCategory));
            args.putSerializable("date", wantedDate);

            Fragment goalInfoFragment = new GoalInfoFragment();
            goalInfoFragment.setArguments(args);
            
            FragmentManager fragmentManager = ((AppCompatActivity) viewGroup).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_overlay_layout_main, goalInfoFragment)
                    .addToBackStack(null)
                    .commit();
        });

        this.activityCategory = activityCategory;
    }

    @Override
    public void bindType(OverviewListItem item) {
        IUserManager userManager = new UserManager();
        Map<ActivityCategories, Float> dayData = userManager.getDayData(wantedDate);
        Map<ActivityCategories, Integer> goals = userManager.getGoals(wantedDate);
        int max = goals.get(activityCategory);
        int current = (dayData.get(activityCategory).intValue()> max) ? max : dayData.get(activityCategory).intValue();

        int color = new ResourceManagement().getGoalColor(activityCategory);

        progressTextView.setText(createProgressText(current, max));
        //unitTextView.setText(R.string.Minutes);

        title.setText(String.valueOf(activityCategory));

        imageView.setImageResource(new ResourceManagement().generateIcons(activityCategory));

        // TODO: Change deprecated method
        header.setBackgroundResource(color);

        progressCircle.addSeries(new SeriesItem.Builder(ContextCompat.getColor(ctx, color))
                .setRange(0, max, current)
                .setLineWidth(18)
                .setInset(new PointF(0, 0))
                .build());
    }

    private String createProgressText(int current, int goal) {
        String currentTime, goalTime;
        switch (activityCategory){
            case Skridt:
                currentTime = Integer.toString(current) + " skridt";
                goalTime = Integer.toString(goal) + " skridt";
                break;
            default:
                currentTime = formatProgressText(current);
                goalTime = formatProgressText(goal);
                break;
        }
        return currentTime+" / "+goalTime;
    }

    private String formatProgressText(int time) {
        int hour, minutes;
        String result = "";

        hour = (int) Math.floor(time/60);
        minutes = time - (hour * 60);

        if(hour >= 1) {
            result += hour+" timer";
            if(minutes >= 1) {
                result += " & ";
            }
        }
        if(minutes >= 1) {
            result += minutes+" min.";
        }
        if(result.equals("")) {
            result = "0 min.";
        }
        return result;
    }

    public CardView getGoalbox() {
        return goalbox;
    }
}
