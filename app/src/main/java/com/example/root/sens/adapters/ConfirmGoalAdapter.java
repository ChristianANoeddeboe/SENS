package com.example.root.sens.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.sens.dto.ConfirmGoalItemModel;
import com.example.root.sens.R;

import java.util.List;

public class ConfirmGoalAdapter extends RecyclerView.Adapter<ConfirmGoalAdapter.ViewHolder> {
    private static final String TAG = ConfirmGoalAdapter.class.getSimpleName();
    private List<ConfirmGoalItemModel> mDataSet;
    private LayoutInflater mInflater;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDescription;
        private final TextView textViewValue;

        public ViewHolder(View v) {
            super(v);
            textViewDescription = v.findViewById(R.id.textView_confirm_goal_description);
            textViewValue = v.findViewById(R.id.textView_confirm_goal_value);
        }


        public TextView getTextViewDescription() {
            return textViewDescription;
        }

        public TextView getTextViewValue() {
            return textViewValue;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public ConfirmGoalAdapter(Context context, List<ConfirmGoalItemModel> dataSet) {
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = mInflater.inflate(R.layout.confirm_goal_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextViewDescription().setText(mDataSet.get(position).getDescription());
        viewHolder.getTextViewValue().setText(mDataSet.get(position).getValue());
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public List<ConfirmGoalItemModel> getmDataSet() {
        return mDataSet;
    }
}