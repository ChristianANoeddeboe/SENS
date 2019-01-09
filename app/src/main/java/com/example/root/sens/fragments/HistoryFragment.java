package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.root.sens.R;


public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.history_f_content, container, false);
        recyclerView = v.findViewById(R.id.historyRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    private RecyclerView.Adapter adapter = new RecyclerView.Adapter<ListElementViewHolder>() {
        @NonNull
        @Override
        public ListElementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.history_f_listelement,viewGroup,false);
            ListElementViewHolder vh = new ListElementViewHolder(view);
            vh.award = view.findViewById(R.id.history_ImageView_award);
            vh.date = view.findViewById(R.id.history_TextView_date);
            vh.info = view.findViewById(R.id.history_TextView_info);
            vh.title = view.findViewById(R.id.history_TextView_title);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ListElementViewHolder listElementViewHolder, int i) {
//            listElementViewHolder.text.setText(lande[i]);
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    };

    class ListElementViewHolder extends RecyclerView.ViewHolder {
        TextView title,date,info;
        ImageView award;
        public ListElementViewHolder(View itemView) {
            super(itemView);
        }
    }
}
