package com.example.root.sens.recyclers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.recyclers.itemmodels.HistoryItemModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private final static String TAG = HistoryAdapter.class.getSimpleName();
    private List<HistoryItemModel> dataSet;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context, List<HistoryItemModel> dataSet){
        inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }
    /*
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDate;
        private final TextView textViewDescription;
        private final ImageView imageViewAward;

        public ViewHolder(View v) {
            super(v);
            textViewTitle = v.findViewById(R.id.history_textview_title);
            textViewDate = v.findViewById(R.id.history_textview_date);
            textViewDescription = v.findViewById(R.id.history_textview_info);
            imageViewAward = v.findViewById(R.id.history_imageview_award);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        public TextView getTextViewDescription() {
            return textViewDescription;
        }

        public ImageView getImageViewAward() {
            return imageViewAward;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.history_f_listelement, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getTextViewDate().setText(dataSet.get(i).getTextViewDate());
        viewHolder.getTextViewTitle().setText(dataSet.get(i).getTextViewTitle());
        viewHolder.getTextViewDescription().setText(dataSet.get(i).getTextViewDescription());
        viewHolder.getImageViewAward().setImageResource(dataSet.get(i).getImageId());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
