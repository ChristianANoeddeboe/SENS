package com.example.root.sens.recyclers.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;

import java.util.List;

import com.example.root.sens.recyclers.adapters.itemmodels.ItemModel;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
    private static final String TAG = "SettingsAdapter";
    private List<ItemModel> mDataSet;
    private ItemClickListener clickListener;


    public SettingsAdapter(List<ItemModel> dataSet) {
        this.mDataSet = dataSet;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.settings_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextViewPrimary().setText(mDataSet.get(position).getPrimaryText());
        viewHolder.getImageView().setImageResource(mDataSet.get(position).getImgId());

        String secondaryText = mDataSet.get(position).getSecondaryText();
        if (secondaryText != null) {
            viewHolder.getTextViewSecondary().setText(secondaryText);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewPrimary, textViewSecondary;
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            textViewPrimary = v.findViewById(R.id.textViewPrimary);
            textViewSecondary = v.findViewById(R.id.textViewSecondary);
            imageView = v.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        public TextView getTextViewPrimary() {
            return textViewPrimary;
        }

        public TextView getTextViewSecondary() {
            return textViewSecondary;
        }

        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}