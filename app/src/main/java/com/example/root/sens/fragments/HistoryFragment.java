package com.example.root.sens.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;

    ArrayList<String> result;
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
        final int dayinmilliseconds = 86400000;
        final int counterLowerBound = 3;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.history_f_content, container, false);
        recyclerView = v.findViewById(R.id.historyRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);
        HashMap<Date, Boolean> userHistory = UserDAO.getInstance().userFulfilledGoals();
        ArrayList<Date> tempDates = new ArrayList<>();
        for(Date date : userHistory.keySet()){
            boolean res = userHistory.get(date).booleanValue();
            if(!res){ //TODO: CHANGE THIS!!!
                tempDates.add(date);
            }
        }
        result = new ArrayList<>();
        Collections.sort(tempDates);
        if(tempDates.size() > 1){
            int counter = 0;
            Date endDate = null;
            for(int i = 0; i < tempDates.size()-1; i++){
                if(tempDates.get(i+1).getTime()-tempDates.get(i).getTime() <= dayinmilliseconds ){
                    counter++;
                    endDate = tempDates.get(i+1);
                }else{
                    addResult(counterLowerBound, counter, endDate);
                    counter = 0;
                }
                if(i == tempDates.size()-2){
                    addResult(counterLowerBound,counter,endDate);
                }
            }
        }
        return v;
    }

    private void addResult(int counterLowerBound, int counter, Date endDate) {
        if(counter >= counterLowerBound){
            SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("d. MMM YYYY", Locale.US);
            result.add(dateFormatForMonth.format(endDate)+","+counter);
        }
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
            String[] arr = result.get(i).split(",");
            int count = Integer.parseInt(arr[1]);
            listElementViewHolder.date.setText( arr[0]);
            listElementViewHolder.title.setText(arr[1]+" dage!");
            listElementViewHolder.info.setText("Du har opnået alle dine mål " + arr[1] + " dage i træk!");
            if(count < 5){
                listElementViewHolder.award.setImageResource(R.drawable.ic_bronze_medal);
            }else if(count < 15){
                listElementViewHolder.award.setImageResource(R.drawable.ic_silver_medal);
            }else{
                listElementViewHolder.award.setImageResource(R.drawable.ic_gold_medal);
            }
        }

        @Override
        public int getItemCount() {
            return result.size();
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
