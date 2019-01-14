package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.recyclers.adapters.OverviewAdapter;

import java.util.Date;


public class OverviewFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.goals_f_content, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.goalsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        OverviewAdapter overviewAdapter = new OverviewAdapter(this.getContext(), new Date(), true);
        recyclerView.setAdapter(overviewAdapter);

        return v;
    }
}
