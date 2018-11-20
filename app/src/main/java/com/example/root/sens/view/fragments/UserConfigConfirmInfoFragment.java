package com.example.root.sens.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.DTO.ConfirmGoalItemModel;
import com.example.root.sens.R;
import com.example.root.sens.adapter.ConfirmGoalAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserConfigConfirmInfoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.user_config_f_viewpager_confirm_info, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerView_confirm_goal);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ConfirmGoalAdapter(createItem());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private List<ConfirmGoalItemModel> createItem() {
        ArrayList<ConfirmGoalItemModel> items = new ArrayList<>();

        items.add(new ConfirmGoalItemModel("Fornavn", 1 + ""));
        items.add(new ConfirmGoalItemModel("Efternavn", 1 + ""));
        items.add(new ConfirmGoalItemModel("Fødselsdag", 1 + ""));
        items.add(new ConfirmGoalItemModel("Cykling", 1 + ""));
        items.add(new ConfirmGoalItemModel("Gang", 1 + ""));
        items.add(new ConfirmGoalItemModel("Træning", 1 + ""));
        items.add(new ConfirmGoalItemModel("Stå", 1 + ""));
        items.add(new ConfirmGoalItemModel("Anden bevægelse", 1 + ""));

        return items;
    }
}
