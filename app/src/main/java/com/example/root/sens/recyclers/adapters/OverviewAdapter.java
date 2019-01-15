package com.example.root.sens.recyclers.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.User;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.example.root.sens.fragments.interfaces.TypeCalendar;
import com.example.root.sens.fragments.interfaces.TypeProgress;
import com.example.root.sens.recyclers.viewholder.ViewHolder;
import com.example.root.sens.recyclers.viewholder.ViewHolderCalendar;
import com.example.root.sens.recyclers.viewholder.ViewHolderProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import io.realm.RealmList;


public class OverviewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context mContext;
    private final List<OverviewListItem> mItems;
    private int i = 0;
    private List<Integer> typeList;
    private Date wantedDate;

    public OverviewAdapter(Context ctx, Date wantedDate, boolean calendarVisible) {
        this.mContext = ctx;
        this.wantedDate = wantedDate;

        mItems = new ArrayList<>();

        if (calendarVisible) {
            mItems.add(new TypeCalendar());
        }
        GoalHistory goals = UserDAO.getInstance().getGoalSpecificDate(wantedDate);
        typeList = new ArrayList<>();
        int counter = 0;
        for (Goal goal : goals.getGoals()) {
            if (goal.getValue() != 0) {
                mItems.add(new TypeProgress(goal.getType()));
                typeList.add(counter);
            }
            counter++;
        }


    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getListItemType();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        ViewHolder viewHolderType = null;
        switch (type) {
            case OverviewListItem.TYPE_CALENDAR:
                view = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.typecalendar, viewGroup, false);
                viewHolderType = new ViewHolderCalendar(view);
                break;
            case OverviewListItem.TYPE_PROGRESS:
                view = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.activity_main_cardview, viewGroup, false);
                viewHolderType = new ViewHolderProgressBar(view, typeList.get(i), mContext, wantedDate);
                i++;
                break;
        }
        return viewHolderType;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        if (pos == 0 && viewHolder instanceof ViewHolderProgressBar) {
            View view = ((ViewHolderProgressBar) viewHolder).getGoalbox().getRootView();

            // This converts an integer to dp, depending on device
            Resources r = mContext.getResources();
            int eightDP = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    8,
                    r.getDisplayMetrics()
            );

            // Retrieving all the declarative set params and adding one
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.topMargin = eightDP;
            view.setLayoutParams(marginLayoutParams);
        }

        OverviewListItem item = mItems.get(pos);
        viewHolder.bindType(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
