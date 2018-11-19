package com.example.root.sens.adapters;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.sens.DTO.Goal;
import com.example.root.sens.R;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeData;

public class ViewHolderData extends ViewHolder {
    private final TextView mTextView;
    private final ProgressBar mProgressBar;
    public ViewHolderData(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.header);
        mProgressBar = itemView.findViewById(R.id.progessbar);
    }

    public void bindType(ListItem item) {
        Goal g = ((TypeData) item).getG();

        mTextView.setText(g.getType().getName()+":"+g.getValue()+" timer");
        mProgressBar.setMax(g.getValue());
        mProgressBar.setProgress(g.getValue()/3);

    }
}
