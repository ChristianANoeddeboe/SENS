package com.example.root.sens.recyclers.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;
import com.example.root.sens.R;

import java.util.List;

public class SetGoalAdapter extends RecyclerView.Adapter<SetGoalAdapter.ViewHolder> {
        private static final String TAG = SetGoalAdapter.class.getSimpleName();
        private List<SetGoalItemModel> dataSet;
        private final static int MIN_PER_DAY = 24*60; // TODO change to standard
        private RecyclerView recyclerViewAdapter;
        /*
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */

        public SetGoalAdapter(List<SetGoalItemModel> dataSet){
            this.dataSet = dataSet;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewPrimary;
            private final TextView textViewSecondary;
            private SeekBar seekBar;
            public ViewHolder(View v) {
                super(v);
                textViewPrimary = v.findViewById(R.id.textView_set_goal_element_header);
                textViewSecondary = v.findViewById(R.id.textView_set_goal_total);

                seekBar = v.findViewById(R.id.seekBar_set_goal);
                seekBar.setMax(MIN_PER_DAY);
                seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

            }

            SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // updated continuously as the user slides the thumb
                    textViewSecondary.setText(generateProgressText(progress));

                    int countTemp = 0;
                    for(int j = 0; j < recyclerViewAdapter.getChildCount(); j++){
                        ViewHolder temp = (ViewHolder) recyclerViewAdapter.findViewHolderForAdapterPosition(j);
                        countTemp += temp.seekBar.getProgress();
                    }
                    for(int j = 0; j < recyclerViewAdapter.getChildCount(); j++){
                        ViewHolder temp = (ViewHolder) recyclerViewAdapter.findViewHolderForAdapterPosition(j);
                        temp.seekBar.setMax(((24*60)-countTemp)+temp.seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // called when the user first touches the SeekBar
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    dataSet.get(getAdapterPosition()).setValue(seekBar.getProgress());
                }
            };

            public TextView getTextViewPrimary() {
                return textViewPrimary;
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