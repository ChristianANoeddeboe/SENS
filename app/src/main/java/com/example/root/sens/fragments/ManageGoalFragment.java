package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.recyclers.adapter.SetGoalAdapter;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;

import java.util.ArrayList;
import java.util.List;

public class ManageGoalFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.user_config_f_viewpager_goal_info, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView_set_goal);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SetGoalAdapter(createItem());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private List<SetGoalItemModel> createItem() {
        ArrayList<SetGoalItemModel> items = new ArrayList<SetGoalItemModel>();
        items.add(new SetGoalItemModel("Cykling"));
        items.add(new SetGoalItemModel("Gang"));
        items.add(new SetGoalItemModel("Træning"));
        items.add(new SetGoalItemModel("Stå"));
        items.add(new SetGoalItemModel("Søvn"));
        return items;
    }

    public RecyclerView.Adapter getmAdapter() {
        return mAdapter;
    }

}
