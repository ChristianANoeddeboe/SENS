package com.example.root.sens.fragments.interfaces;

/**
 * Source: https://medium.com/@ruut_j/a-recyclerview-with-multiple-item-types-bce7fbd1d30e
 */
public interface ListItem {
    int TYPE_A = 1;
    int TYPE_B = 2;

    int getListItemType();
}
