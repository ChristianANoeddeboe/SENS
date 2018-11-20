package com.example.root.sens.adapters;

import android.view.View;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeCalendar;

public class ViewHolderCalendar extends ViewHolder {
    //private final TextView mTextView;

    public ViewHolderCalendar(View itemView) {
        super(itemView);

        //mTextView = (TextView) itemView.findViewById(R.id.txtView);
    }

    public void bindType(ListItem item) {
        //mTextView.setText(((TypeCalendar) item).getText());
    }
}
