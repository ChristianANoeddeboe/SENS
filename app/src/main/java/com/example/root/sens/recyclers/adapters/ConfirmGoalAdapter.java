package com.example.root.sens.recyclers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.sens.recyclers.itemmodels.ConfirmGoalItemModel;
import com.example.root.sens.R;

import java.util.List;
import java.util.regex.Pattern;

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
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public ConfirmGoalAdapter(Context context, List<ConfirmGoalItemModel> dataSet) {
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = mInflater.inflate(R.layout.confirm_goal_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextViewDescription().setText(mDataSet.get(position).getDescription());
        viewHolder.getTextViewValue().setText(generateProgressText(mDataSet.get(position).getValue()));
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public List<ConfirmGoalItemModel> getmDataSet() {
        return mDataSet;
    }

    // TODO: Extract resource with para
    private String generateProgressText(String text){
        String result = null;
        if(Pattern.matches("[0-9]+", text)){
            int progress = Integer.parseInt(text);
            int hours = progress/60;
            int minuttes = progress%60;
            result = ""+hours+" timer & "+minuttes+" minutter";
        }
        else result = text;
        return result;

    }
}