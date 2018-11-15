package com.example.root.sens.adapters;

import android.view.View;

import com.example.root.sens.view.fragments.interfaces.ListItem;

public class ViewHolderData extends ViewHolder {
    //private final TextView mTextView;

    public ViewHolderData(View itemView) {
        super(itemView);

        //mTextView = (TextView) itemView.findViewById(R.id.txtView);
    }

    public void bindType(ListItem item) {
        //mTextView.setText(((TypeCalendar) item).getText());
    }
}
