package com.example.root.sens.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.root.sens.view.fragments.interfaces.ListItem;

public abstract class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bindType(ListItem item);
}
