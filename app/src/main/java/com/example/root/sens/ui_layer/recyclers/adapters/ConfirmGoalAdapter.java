package com.example.root.sens.ui_layer.recyclers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.sens.auxiliary.ProgressTextGenerator;
import com.example.root.sens.ui_layer.recyclers.itemmodels.ConfirmGoalItemModel;
import com.example.root.sens.R;

import java.util.List;

public class ConfirmGoalAdapter extends RecyclerView.Adapter<ConfirmGoalAdapter.ViewHolder> {
    private static final String TAG = ConfirmGoalAdapter.class.getSimpleName();
    private List<ConfirmGoalItemModel> mDataSet;
    private LayoutInflater mInflater;

    /*
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

    /*
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the DemoData to populate views to be used by RecyclerView.
     */
    public ConfirmGoalAdapter(Context context, List<ConfirmGoalItemModel> dataSet) {
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = mInflater.inflate(R.layout.confirm_goal_element, viewGroup, false);
        Log.d(TAG,""+viewType);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.getTextViewDescription().setText(mDataSet.get(position).getDescription());
        if(mDataSet.get(position).getType() == 0){
            viewHolder.getTextViewValue().setText(new ProgressTextGenerator().generateProgressText(mDataSet.get(position).getValue()));
        }else{
            Log.d(TAG,"Test");
            Log.d(TAG,mDataSet.get(position).getValue()+":"+mDataSet.get(position).getDescription()+":"+mDataSet.get(position).getType());
            viewHolder.getTextViewValue().setText(mDataSet.get(position).getValue());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public List<ConfirmGoalItemModel> getmDataSet() {
        return mDataSet;
    }
}