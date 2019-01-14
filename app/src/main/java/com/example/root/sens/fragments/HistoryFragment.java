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
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.recyclers.adapters.HistoryAdapter;
import com.example.root.sens.recyclers.itemmodels.HistoryItemModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class HistoryFragment extends Fragment {
    private ArrayList<String> result;
    private final int COUNTER_LOWER_BOUND = 3; // This is for config
    private final int DAY_MILLISECONDS = 86400000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.history_f_content, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.historyRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        HistoryAdapter adapter = new HistoryAdapter(getContext(), generateData());
        recyclerView.setAdapter(adapter);

        //TODO: Alt data generering til recyclerviewet skal ud i sin egen klasse!
        HashMap<Date, Boolean> userHistory = UserDAO.getInstance().userFulfilledGoals();
        ArrayList<Date> tempDates = new ArrayList<>();

        for(Date date : userHistory.keySet()){
            boolean res = userHistory.get(date).booleanValue();
            /*
            * Remove the ! to get the actually intended code, the ! is there for testing purposes.
            * */
            if(!res){
                tempDates.add(date);
            }
        }

        result = new ArrayList<>();
        Collections.sort(tempDates);

        if(tempDates.size() > 1){
            int counter = 0;
            Date endDate = null;
            for(int i = 0; i < tempDates.size()-1; i++){
                if(tempDates.get(i+1).getTime()-tempDates.get(i).getTime() <= DAY_MILLISECONDS){
                    counter++;
                    endDate = tempDates.get(i+1);
                }else{
                    addResult(counter, endDate);
                    counter = 0;
                }
                if(i == tempDates.size()-2){
                    addResult(counter,endDate);
                }
            }
        }
        return v;
    }

    private void addResult(int counter, Date endDate) {
        if(counter >= COUNTER_LOWER_BOUND){
            SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("d. MMM YYYY", Locale.US);
            result.add(dateFormatForMonth.format(endDate)+","+counter);
        }
    }

    private List<HistoryItemModel> generateData(){
        List<HistoryItemModel> data = new ArrayList<>();
        String[] arr = null;
        if(result != null){
            for(String str : result){
                arr = str.split(",");
                data.add(new HistoryItemModel(arr[1]+" dage!",
                        arr[0], "Du har opnået alle dine mål " + arr[1] + " dage i træk!",
                        getGraphicsId(Integer.parseInt(arr[1]))
                ));
            }
        }
        //DUMMY DATA!
        else{
            data.add(new HistoryItemModel(50+" dage!",
                    "Dato her!", "Du har opnået alle dine mål " +50 + " dage i træk!",
                    getGraphicsId(Integer.parseInt("50"))
            ));
        }
        return data;
    }

    private int getGraphicsId(int count){
        if(count < 5){
            return R.drawable.ic_bronze_medal;
        }else if(count < 15){
            return R.drawable.ic_silver_medal;
        }else{
            return R.drawable.ic_gold_medal;
        }
    }
}
