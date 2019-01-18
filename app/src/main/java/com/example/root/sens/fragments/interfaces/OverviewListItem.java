package com.example.root.sens.fragments.interfaces;

/**
 * Source: https://medium.com/@ruut_j/a-recyclerview-with-multiple-item-types-bce7fbd1d30e
 */
public interface OverviewListItem {
    int TYPE_CALENDAR = 1;
    int TYPE_PROGRESS = 2;
    int TYPE_NO_DATA = 3;

    int getListItemType();
}
