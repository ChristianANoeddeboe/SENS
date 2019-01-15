package com.example.root.sens.fragments.interfaces;

import com.example.root.sens.dto.ActivityCategories;

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
