package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.root.sens.R;
import com.example.root.sens.auxiliary.DayDataGoalMapper;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.recyclers.adapter.OverviewAdapter;

import java.util.Date;
import java.util.Map;

public class DayDataFragment extends Fragment{
    private Map<String, Integer> goalMap;
    private OverviewAdapter overviewAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_screen_cards, container, false);

        // Getting the selected data
        Bundle bundle = savedInstanceState != null ? savedInstanceState : getArguments();
        Date date = (Date) bundle.getSerializable("date");

        // Loading data from Realm
        UserDAO user = UserDAO.getInstance();
        DayDataGoalMapper dayDataGoalMapper = new DayDataGoalMapper(user, date);
        goalMap = dayDataGoalMapper.getGoalMap();

        // Adding button
        Button buttonBack = rootView.findViewById(R.id.button_close_full_screen_cards);
        buttonBack.setOnClickListener((View v) -> getActivity().onBackPressed());

        // Recycler stuff
        View myLayout = rootView.findViewById( R.id.frameLayout );
        recyclerView = myLayout.findViewById( R.id.goalsRecycler );
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        overviewAdapter = new OverviewAdapter(this.getContext(), date);
        recyclerView.setAdapter(overviewAdapter);

        return rootView;
    }
}
