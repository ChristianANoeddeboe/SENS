package com.example.root.sens.recyclers.adapter;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.fragments.interfaces.OverviewListItem;
import com.example.root.sens.recyclers.viewholder.ViewHolder;
import com.example.root.sens.recyclers.viewholder.ViewHolderCalendar;
import com.example.root.sens.recyclers.viewholder.ViewHolderProgressBar;

import java.util.List;


public class OverviewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context mContext;
    private final List<OverviewListItem> mItems;
    private int i = 0;
    public OverviewAdapter(Context ctx, List<OverviewListItem> items) {
        mContext = ctx;
        mItems = items;
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
                        .inflate(R.layout.typegoal, viewGroup, false);
                viewHolderType = new ViewHolderProgressBar(view,i,mContext);
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
