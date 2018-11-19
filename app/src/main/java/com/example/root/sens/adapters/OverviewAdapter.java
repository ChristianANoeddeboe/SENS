package com.example.root.sens.adapters;



import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class OverviewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context mContext;
    private final List<ListItem> mItems;

    public OverviewAdapter(Context ctx, List<ListItem> items) {
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
        switch (type) {
            case ListItem.TYPE_A:
                view = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.typecalendar, viewGroup, false);
                CompactCalendarView temp = view.findViewById(R.id.compactcalendar_view);
                temp.addEvent(new Event(Color.GREEN,1542530497*1000L,"Tesaasdt"));
                temp.addEvent(new Event(Color.BLUE,Calendar.getInstance().getTimeInMillis(),"asdfsdfsdf"));

                return new ViewHolderCalendar(view);
            case ListItem.TYPE_B:
                view = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.typedata, viewGroup, false);

                return new ViewHolderData(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        ListItem item = mItems.get(pos);
        viewHolder.bindType(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
