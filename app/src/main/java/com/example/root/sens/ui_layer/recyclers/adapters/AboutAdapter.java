package com.example.root.sens.ui_layer.recyclers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.ui_layer.recyclers.itemmodels.AboutItemModel;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    private static final String TAG = AboutAdapter.class.getSimpleName();
    private List<AboutItemModel> mDataSet;
    private LayoutInflater mInflater;

    /*
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewSt;
        private final ImageView img;

        public ViewHolder(View v) {
            super(v);
            textViewName = v.findViewById(R.id.name);
            textViewSt = v.findViewById(R.id.studentNumber);
            img = v.findViewById(R.id.imageView);
        }


        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewSt() {
            return textViewSt;
        }

        public ImageView getImageView() {
            return img;
        }

    }

    /*
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public AboutAdapter(Context context, List<AboutItemModel> dataSet) {
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = mInflater.inflate(R.layout.about_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextViewName().setText(mDataSet.get(position).getName());
        viewHolder.getTextViewSt().setText(mDataSet.get(position).getStudentNumber());
        viewHolder.getImageView().setImageResource(mDataSet.get(position).getImg());
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}