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
import com.example.root.sens.recyclers.adapter.SetGoalAdapter;

import java.util.ArrayList;


public class ManageGoalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<String> data;

    private ImageButton cancelbtn, donebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_goal);
        recyclerView = findViewById(R.id.recyclerView_manage_goal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        cancelbtn = findViewById(R.id.managegoal_cancelbtn);
        donebtn = findViewById(R.id.managegoal_donebtn);

        cancelbtn.setOnClickListener(this);
        donebtn.setOnClickListener(this);

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
            vh.total = view.findViewById(R.id.textView_set_goal_total);
            vh.header = view.findViewById(R.id.textView_set_goal_element_header);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ListElementViewHolder listElementViewHolder, int i) {
            listElementViewHolder.header.setText(data.get(i));
            listElementViewHolder.seekBar.setMax(24*60);
            listElementViewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    listElementViewHolder.total.setText(SetGoalAdapter.generateProgressText(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    };

    @Override
    public void onClick(View v) {

    }

    class ListElementViewHolder extends RecyclerView.ViewHolder {
        TextView header,total;
        SeekBar seekBar;
        public ListElementViewHolder(View itemView) {
            super(itemView);
        }
    }

}
