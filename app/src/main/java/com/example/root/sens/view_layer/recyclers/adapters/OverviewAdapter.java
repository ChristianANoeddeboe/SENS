package com.example.root.sens.view_layer.recyclers.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.view_layer.fragments.interfaces.OverviewListItem;
import com.example.root.sens.view_layer.fragments.interfaces.TypeCalendar;
import com.example.root.sens.view_layer.fragments.interfaces.TypeNoData;
import com.example.root.sens.view_layer.fragments.interfaces.TypeProgress;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.observers.CreateNewGoalObserver;
import com.example.root.sens.view_layer.recyclers.viewholder.ViewHolder;
import com.example.root.sens.view_layer.recyclers.viewholder.ViewHolderCalendar;
import com.example.root.sens.view_layer.recyclers.viewholder.ViewHolderNoData;
import com.example.root.sens.view_layer.recyclers.viewholder.ViewHolderProgressBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class OverviewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context mContext;
    private final List<OverviewListItem> mItems;
    private int i = 0;
    private List<ActivityCategories> typeList;
    private Date wantedDate;

    public OverviewAdapter(Context ctx, Date wantedDate, boolean calendarVisible) {
        Map<ActivityCategories, Integer> goals = new UserManager().getGoals(wantedDate);
        typeList = new ArrayList<>();
        this.mContext = ctx;
        this.wantedDate = wantedDate;

        mItems = new ArrayList<>();

        if (calendarVisible) {
            mItems.add(new TypeCalendar());
        }

        for (Map.Entry<ActivityCategories, Integer> entry : goals.entrySet())

        {
            if (entry.getValue() != 0) {
                mItems.add(new TypeProgress(entry.getKey()));
                typeList.add(entry.getKey());
            }
        }

        if(mItems.size() <= 1){
            mItems.add(new TypeNoData());
        }

        typeList.sort((o1, o2) -> {
            if(o1.getValue() < o2.getValue()) return 1;
            else return -1;
        });
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
            case OverviewListItem.TYPE_NO_DATA:
                view = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.activity_main_no_data, viewGroup, false);
                viewHolderType = new ViewHolderNoData(view);

                view.findViewById(R.id.buttonNoGoalsCreateGoals).setOnClickListener((View v) ->
                        ((CreateNewGoalObserver) mContext).startManageGoalActivity());

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
