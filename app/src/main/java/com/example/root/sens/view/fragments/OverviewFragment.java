package com.example.root.sens.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.DTO.Goal;
import com.example.root.sens.R;
import com.example.root.sens.adapters.OverviewAdapter;
import com.example.root.sens.data;
import com.example.root.sens.view.fragments.interfaces.ListItem;
import com.example.root.sens.view.fragments.interfaces.TypeCalendar;
import com.example.root.sens.view.fragments.interfaces.TypeData;

import java.util.ArrayList;


public class OverviewFragment extends Fragment {

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.goals_f_content, container, false);
        // Vi laver en arrayliste så vi kan fjerne/indsætte elementer
        recyclerView = v.findViewById(R.id.goalsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(new OverviewAdapter(this.getContext(),generateData()));

        return v;
    }
    public static ArrayList<ListItem> generateData(){
        ArrayList<ListItem> temp = new ArrayList<>();
        temp.add(new TypeCalendar());
        for(Goal g : data.user.getGoals()){
            temp.add(new TypeData(g));
        }
        return temp;
    }



}
