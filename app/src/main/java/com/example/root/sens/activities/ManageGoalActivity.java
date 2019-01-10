package com.example.root.sens.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.User;
import com.example.root.sens.recyclers.adapter.SetGoalAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmList;


public class ManageGoalActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<String> data;
    private boolean changed = false;
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
            RealmList<Goal> temp = UserDAO.getInstance().getNewestGoal().getGoals();
            for(Goal g : temp){
                if(g.getType().toString().equals(data.get(i))){
                    listElementViewHolder.seekBar.setProgress(g.getValue());
                    listElementViewHolder.total.setText(SetGoalAdapter.generateProgressText(g.getValue()));
                    break;
                }
            }

            listElementViewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    listElementViewHolder.total.setText(SetGoalAdapter.generateProgressText(progress));
                    changed = true;
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
        if(v.equals(donebtn)){
            if(changed){
                HashMap<String, Integer> temp = new HashMap<>();
                RealmList<Goal> s = UserDAO.getInstance().getNewestGoal().getGoals();
                for(Goal goal : s){
                    for(int i = 0; i < recyclerView.getChildCount(); i++){
                        ListElementViewHolder listElementViewHolder = (ListElementViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if(listElementViewHolder.header.getText().toString().equals(goal.getType().toString())){
                            temp.put(listElementViewHolder.header.getText().toString(),listElementViewHolder.seekBar.getProgress());
                            break;
                        }
                    }
                }
                if(temp.size() > 0){
                       UserDAO.getInstance().updateOrMergeGoals(temp);
                       User a = UserDAO.getInstance().getUserLoggedIn();
                       GoalHistory t = UserDAO.getInstance().getNewestGoal();
                       finish();
                }else{
                    // No goals were added to the new goal set
                }
            }else{
                //Goals were not changed but user pressed save button
            }

        }else if(v.equals(cancelbtn)){
            finish();
        }
    }



    class ListElementViewHolder extends RecyclerView.ViewHolder {
        TextView header,total;
        SeekBar seekBar;
        public ListElementViewHolder(View itemView) {
            super(itemView);
        }
    }

}
