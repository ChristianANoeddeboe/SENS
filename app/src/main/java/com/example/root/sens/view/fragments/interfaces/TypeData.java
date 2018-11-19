package com.example.root.sens.view.fragments.interfaces;

import com.example.root.sens.DTO.Goal;

public class TypeData implements ListItem {
    private Goal g;
    public TypeData(Goal g) {
        this.g = g;
    }

    public Goal getG() {
        return g;
    }

    public void setG(Goal g) {
        this.g = g;
    }

    @Override
    public int getListItemType() {
        return ListItem.TYPE_B;
    }
}
