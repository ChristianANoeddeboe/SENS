package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.root.sens.R;
import com.example.root.sens.auxiliary.ResourceManagement;
import com.example.root.sens.dao.UserDAO;
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
    private TextView progressTextView, title, unitTextView;
    private ImageView imageView;

    public CardView getGoalbox() {
        return goalbox;
    }

    public void setGoalbox(CardView goalbox) {
        this.goalbox = goalbox;
    }

    private CardView goalbox;
    private LinearLayout header;
    private int type;
    private String goalType;
    private Date wantedDate;
    private Context ctx;

    public ViewHolderProgressBar(View itemView, int i, Context viewGroup, Date wantedDate) {
        super(itemView);
        this.wantedDate = wantedDate;
        progressCircle = itemView.findViewById(R.id.dynamicArcView);
        progressTextView = itemView.findViewById(R.id.goalstatusTextView);
        imageView = itemView.findViewById(R.id.goalIconImageView);
        goalbox = itemView.findViewById(R.id.goalchart_cardview);
        header = itemView.findViewById(R.id.typegoal_LinearLayout_header);
        unitTextView = itemView.findViewById(R.id.goalbox_Textview_unit);
        title = itemView.findViewById(R.id.goalbox_TextView_title);
        ctx = viewGroup;

        goalbox.setOnClickListener((View v)->{
            Bundle args = new Bundle();
            args.putString("title",title.getText().toString());
            args.putString("progress",progressTextView.getText().toString());
            args.putString("unit", unitTextView.getText().toString());
            args.putString("goalType",goalType);

            Fragment goalInfoFragment = new GoalInfoFragment();
            goalInfoFragment.setArguments(args);
            
            FragmentManager fragmentManager = ((AppCompatActivity) viewGroup).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_overlay_layout_main, goalInfoFragment)
                    .addToBackStack(null)
                    .commit();
        });

        type = i;
    }

    @Override
    public void bindType(OverviewListItem item) {
        UserDAO userDAO = UserDAO.getInstance();
        DayData dayData = userDAO.getDataSpecificDate(wantedDate);
        RealmList<Goal> goals = UserDAO.getInstance().getGoalSpecificDate(wantedDate).getGoals();
        Goal currentGoal = goals.get(type);
        int current = 0;
        int max = 1;

        try{
            goalType = currentGoal.getType().toString();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        if(dayData != null){
            RealmList<Record> temp = dayData.getRecords();
            for(Record record : temp){
                if(record.getType().equals(currentGoal.getType())){
                    current = (int) record.getValue();
                    break;
                }
            }

            max = currentGoal.getValue();
            if(max > 0 ) {
                if(current > max){
                    current = max;
                }
            } else {
                current = max;
            }
        }

        int color = new ResourceManagement().getGoalColor(currentGoal.getType());

        progressTextView.setText(Integer.toString(current)+"/"+Integer.toString(currentGoal.getValue()));
        unitTextView.setText(R.string.Minutes);

        title.setText(currentGoal.getType().toString());

        imageView.setImageResource(new ResourceManagement().generateIcons(currentGoal.getType()));

        // TODO: Change deprecated method
        header.setBackgroundResource(color);

        progressCircle.addSeries(new SeriesItem.Builder(ContextCompat.getColor(ctx, color))
                .setRange(0, max, current)
                .setLineWidth(18)
                .setInset(new PointF(0, 0))
                .build());
    }
}
