package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.recyclers.adapter.OverviewAdapter;
import com.example.root.sens.fragments.interfaces.ListItem;
import com.example.root.sens.fragments.interfaces.TypeCalendar;
import com.example.root.sens.fragments.interfaces.TypeGraph;

import java.util.ArrayList;


public class OverviewFragment extends Fragment {
    private OverviewAdapter overviewAdapter;
    public OverviewFragment() {
        // Required empty public constructor
    }

    public OverviewAdapter getOverviewAdapter() {
        return overviewAdapter;
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
        overviewAdapter = new OverviewAdapter(this.getContext(),generateData());
        recyclerView.setAdapter(overviewAdapter);

        return v;
    }
    public static ArrayList<ListItem> generateData(){
        ArrayList<ListItem> temp = new ArrayList<>();
        temp.add(new TypeCalendar());

        int amount = UserDAO.getInstance().getNewestGoal().getGoals().size()-1;
        for(int i = 0; i < amount; i++) {
            temp.add(new TypeGraph());
        }
        return temp;
    }



}
