package com.example.root.sens.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.sens.DTO.SetGoalItemModel;
import com.example.root.sens.R;

import java.util.List;

public class SetGoalAdapter extends RecyclerView.Adapter<SetGoalAdapter.ViewHolder> {
        private static final String TAG = "SettingsAdapter";

        private List<SetGoalItemModel> mDataSet;

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewPrimary;
            private final TextView textViewSecondary;

            public ViewHolder(View v) {
                super(v);
                textViewPrimary = v.findViewById(R.id.textView_set_goal_element_header);
                textViewSecondary = v.findViewById(R.id.textView_set_goal_total);

                SeekBar seekBar = v.findViewById(R.id.seekBar_set_goal);
                seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

            }

            SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // updated continuously as the user slides the thumb
                    textViewSecondary.setText(progress + " minutter");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // called when the user first touches the SeekBar
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // called after the user finishes moving the SeekBar
                }
            };

            public TextView getTextViewPrimary() {
                return textViewPrimary;
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public SetGoalAdapter(List<SetGoalItemModel> dataSet) {
            mDataSet = dataSet;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.set_goal_element, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            viewHolder.getTextViewPrimary().setText(mDataSet.get(position).getPrimaryTxt());
        }


        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
}