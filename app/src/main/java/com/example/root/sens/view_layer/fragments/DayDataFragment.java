package com.example.root.sens.view_layer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.view_layer.recyclers.adapters.OverviewAdapter;

import java.util.Date;

public class DayDataFragment extends Fragment{
    private OverviewAdapter overviewAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_screen_cards, container, false);

        // Getting the selected DemoData
        Bundle bundle = savedInstanceState != null ? savedInstanceState : getArguments();
        Date date = (Date) bundle.getSerializable("date");

        // Recycler stuff
        View dataDataFragmentLayout = rootView.findViewById( R.id.frameLayout_DataDataFragment);
        recyclerView = dataDataFragmentLayout.findViewById( R.id.goalsRecycler );
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        overviewAdapter = new OverviewAdapter(this.getContext(), date, false);
        recyclerView.setAdapter(overviewAdapter);

        return rootView;
    }
}
