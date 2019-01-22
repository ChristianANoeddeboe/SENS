package com.example.root.sens.view_layer.fragments.interfaces;

import com.example.root.sens.ActivityCategories;

public class TypeProgress implements OverviewListItem {
    private ActivityCategories type;

    public TypeProgress(ActivityCategories type) {
        this.type = type;
    }

    public ActivityCategories getType() {
        return type;
    }

    @Override
    public int getListItemType() {
        return OverviewListItem.TYPE_PROGRESS;
    }
}
