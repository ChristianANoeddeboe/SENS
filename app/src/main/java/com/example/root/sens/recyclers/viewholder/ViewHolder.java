package com.example.root.sens.recyclers.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.root.sens.fragments.interfaces.OverviewListItem;

public abstract class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bindType(OverviewListItem item);
}
