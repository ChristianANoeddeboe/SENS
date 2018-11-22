package com.example.root.sens.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.sens.R;

import java.util.List;

import com.example.root.sens.DTO.ItemModel;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
        private static final String TAG = "SettingsAdapter";

        private List<ItemModel> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewPrimary, textViewSecondary;
            private final ImageView imageView;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(v1 -> Toast.makeText(v1.getContext(),
                        "Ikke implementeret", Toast.LENGTH_LONG).show());
                textViewPrimary =  v.findViewById(R.id.textViewPrimary);
                textViewSecondary = v.findViewById(R.id.textViewSecondary);
                imageView = v.findViewById(R.id.imageView);
            }

            public TextView getTextViewPrimary() {
                return textViewPrimary;
            }
            public TextView getTextViewSecondary() {
                return textViewSecondary;
            }

            public ImageView getImageView(){
                return imageView;
            }
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public SettingsAdapter(List<ItemModel> dataSet) {
            this.mDataSet = dataSet;
        }

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.settings_element, viewGroup, false);

            return new ViewHolder(v);
        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.getTextViewPrimary().setText(mDataSet.get(position).getPrimaryText());
            String secondaryText = mDataSet.get(position).getSecondaryText();
            if(secondaryText != null){
                viewHolder.getTextViewSecondary().setText(secondaryText);
            }
            viewHolder.getImageView().setImageResource(mDataSet.get(position).getImgId());
        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
}