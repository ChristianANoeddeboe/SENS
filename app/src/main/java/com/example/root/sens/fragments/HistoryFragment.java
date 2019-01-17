package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.recyclers.adapters.HistoryAdapter;
import com.example.root.sens.recyclers.itemmodels.HistoryItemModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class HistoryFragment extends Fragment {
    private ArrayList<String> result;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.history_f_content, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.historyRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        HistoryAdapter adapter = new HistoryAdapter(getContext(), generateData());
        recyclerView.setAdapter(adapter);

        return v;
    }

    private List<HistoryItemModel> generateData(){
        List<String> stringValues = new UserManager().getGoalStreak();
        List<HistoryItemModel> data = new ArrayList<>();
        String[] arr = null;
        if(stringValues != null){
            for(String str : stringValues){
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
            data.add(new HistoryItemModel(12 + " dage!",
                    "I dag", "En kort tekst",
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
