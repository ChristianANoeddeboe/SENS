package com.example.root.sens.recyclers.adapters;

import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.sens.fragments.TimePickerFragment;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;
import com.example.root.sens.R;

import java.util.List;

public class SetGoalAdapter extends RecyclerView.Adapter<SetGoalAdapter.ViewHolder> {
    private static final String TAG = SetGoalAdapter.class.getSimpleName();
    private SetGoalAdapterOnItemClickListener listener;
    private List<SetGoalItemModel> dataSet;
    private final static int MIN_PER_DAY = 24*60; // TODO change to standard
    private RecyclerView recyclerViewAdapter;

    public interface SetGoalAdapterOnItemClickListener {
        void onItemClick(View item, int position);
    }

    /*
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */

    public SetGoalAdapter(SetGoalAdapterOnItemClickListener listener, List<SetGoalItemModel> dataSet){
        this.dataSet = dataSet;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPrimary;
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            textViewPrimary = v.findViewById(R.id.textView_set_goal_element_header);
            textView = v.findViewById(R.id.button_set_goal);

        }

        public TextView getTextViewPrimary() {
            return textViewPrimary;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.set_goal_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.getTextViewPrimary().setText(dataSet.get(position).getPrimaryTxt());
        viewHolder.getTextView().setText(generateProgressText(dataSet.get(position).getValue()));
        viewHolder.getTextView().setOnClickListener((View v) -> {
            listener.onItemClick(viewHolder.getTextView(), position);
        });
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public List<SetGoalItemModel> getDataSet(){
        return dataSet;
    }

    public static String generateProgressText(int progress){
        String result;
        int hours = progress/60;
        int minutes = progress%60;
        result = ""+hours+" timer & "+minutes+" minutter";
        return result;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerViewAdapter = recyclerView;
    }
}