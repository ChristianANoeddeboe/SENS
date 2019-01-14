package com.example.root.sens.recyclers.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.auxiliary.ColorManagement;
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
    private TextView progressTextView, title, unitTextView;
    private ImageView imageView;
    private CardView goalbox, header;
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
        header = itemView.findViewById(R.id.cardview_header);
        unitTextView = itemView.findViewById(R.id.goalbox_Textview_unit);
        title = itemView.findViewById(R.id.goalbox_TextView_title);
        ctx = viewGroup;

        goalbox.setOnClickListener((View v)->{
            Bundle args = new Bundle();
            args.putString("title",title.getText().toString());
            args.putString("progress",progressTextView.getText().toString());
            args.putString("unit", unitTextView.getText().toString());
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

    @Override
    public void bindType(OverviewListItem item) {
        UserDAO userDAO = UserDAO.getInstance();
        DayData dayData = userDAO.getDataSpecificDate(wantedDate);
        RealmList<Goal> goals = UserDAO.getInstance().getNewestGoal().getGoals();
        Goal currGoal = goals.get(type);
        int current = 0;
        int max = 1;

        try{
            goalType = currGoal.getType().toString();
        } catch (NullPointerException e){
            e.printStackTrace();
        }


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
            } else {
                current = max;
            }

        }

        int color = new ColorManagement().getGoalColor(currGoal.getType());

        progressTextView.setText(Integer.toString(current)+"/"+Integer.toString(currGoal.getValue()));
        unitTextView.setText("minutes");

        title.setText(currGoal.getType().toString());

        imageView.setImageDrawable(generateIcons(currGoal.getType()));

        // TODO: Change deprecated method
        header.setBackgroundTintList(ctx.getResources().getColorStateList(color));

        progressCircle.addSeries(new SeriesItem.Builder(Color.argb(255, 237, 28, 38))
                .setRange(0, max, current)
                .setLineWidth(20)
                .setInset(new PointF(0, 2))
                .build());
    }


    private Drawable generateIcons(ActivityCategories curr) {
        Drawable icon;
        switch (curr) {
            case Søvn:
                icon = ctx.getDrawable(R.mipmap.icon_resting);
                return icon;
            case Stå:
                icon = ctx.getDrawable(R.mipmap.icon_standing);
                return icon;
            case Gang:
                icon = ctx.getDrawable(R.mipmap.icon_walking);
                return icon;
            case Cykling:
                icon = ctx.getDrawable(R.mipmap.icon_cycling);
                return icon;
            case Træning:
                icon = ctx.getDrawable(R.mipmap.icon_exercise);
                return icon;
        }
        return ctx.getDrawable(R.mipmap.award);
    }
}
