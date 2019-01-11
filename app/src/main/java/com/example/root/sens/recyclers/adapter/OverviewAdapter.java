package com.example.root.sens.recyclers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.example.root.sens.fragments.interfaces.TypeCalendar;
import com.example.root.sens.fragments.interfaces.TypeProgress;
import com.example.root.sens.recyclers.viewholder.ViewHolder;
import com.example.root.sens.recyclers.viewholder.ViewHolderCalendar;
import com.example.root.sens.recyclers.viewholder.ViewHolderProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class OverviewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context mContext;
    private final List<OverviewListItem> mItems;
    private int i = 0;
    private Date wantedDate;

    public OverviewAdapter(Context ctx, Date wantedDate, boolean calendarVisible) {
        this.mContext = ctx;
        this.wantedDate = wantedDate;

        mItems = new ArrayList<>();

        if(calendarVisible){
            mItems.add(new TypeCalendar());
        }

        int amount = UserDAO.getInstance().getNewestGoal().getGoals().size()-1;
        for(int i = 0; i < amount; i++) {
            mItems.add(new TypeProgress());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getListItemType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = null;
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
                viewHolderType = new ViewHolderProgressBar(view,i,mContext, wantedDate);
                i++;
                break;
        }
        return viewHolderType;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        OverviewListItem item = mItems.get(pos);
        viewHolder.bindType(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
