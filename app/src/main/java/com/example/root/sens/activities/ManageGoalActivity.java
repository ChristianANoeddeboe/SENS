package com.example.root.sens.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.sens.R;

import java.util.ArrayList;


public class ManageGoalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_goal);
        recyclerView = findViewById(R.id.recyclerView_manage_goal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        data = new ArrayList<>();
        data.add("Cykling");
        data.add("Gang");
        data.add("Træning");
        data.add("Stå");
        data.add("Søvn");

    }


    private RecyclerView.Adapter adapter = new RecyclerView.Adapter<ListElementViewHolder>() {

        @NonNull
        @Override
        public ListElementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.set_goal_element,viewGroup,false);
            ListElementViewHolder vh = new ListElementViewHolder(view);
            vh.seekBar = view.findViewById(R.id.seekBar_set_goal);
            vh.header = view.findViewById(R.id.textView_set_goal_total);
            vh.total = view.findViewById(R.id.textView_set_goal_element_header);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ListElementViewHolder listElementViewHolder, int i) {
            listElementViewHolder.header.setText(data.get(i));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    };

    class ListElementViewHolder extends RecyclerView.ViewHolder {
        TextView header,total;
        SeekBar seekBar;
        public ListElementViewHolder(View itemView) {
            super(itemView);
        }
    }

}
