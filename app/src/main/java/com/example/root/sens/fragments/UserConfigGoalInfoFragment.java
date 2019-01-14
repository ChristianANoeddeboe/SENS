package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;
import com.example.root.sens.R;
import com.example.root.sens.recyclers.adapters.SetGoalAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserConfigGoalInfoFragment extends Fragment {
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.user_config_f_viewpager_goal_info, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_set_goal);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SetGoalAdapter(createItem());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private List<SetGoalItemModel> createItem() {
        ArrayList<SetGoalItemModel> items = new ArrayList<>();

        items.add(new SetGoalItemModel("Cykling"));
        items.add(new SetGoalItemModel("Gang"));
        items.add(new SetGoalItemModel("Træning"));
        items.add(new SetGoalItemModel("Stå"));
        items.add(new SetGoalItemModel("Søvn"));
        return items;
    }
}
