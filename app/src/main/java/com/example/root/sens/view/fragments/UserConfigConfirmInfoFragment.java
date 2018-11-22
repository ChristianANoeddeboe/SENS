package com.example.root.sens.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.dto.ConfirmGoalItemModel;
import com.example.root.sens.R;
import com.example.root.sens.adapters.ConfirmGoalAdapter;
import com.example.root.sens.view.activities.UserConfigActivity;

import java.util.ArrayList;
import java.util.List;

public class UserConfigConfirmInfoFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public final static String USER_DATA_RECEIVE = "user_data_receive";
    public final static String GOAL_DATA_RECEIVE = "goal_data_receive";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ConfirmGoalItemModel> recyclerItems = new ArrayList<>();

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
        mAdapter = new ConfirmGoalAdapter(getContext(), createItem());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private List<ConfirmGoalItemModel> createItem() {
        recyclerItems.add(new ConfirmGoalItemModel("Fornavn",  ""));
        recyclerItems.add(new ConfirmGoalItemModel("Efternavn",  ""));
        recyclerItems.add(new ConfirmGoalItemModel("Fødselsdag", ""));
        recyclerItems.add(new ConfirmGoalItemModel("Cykling", ""));
        recyclerItems.add(new ConfirmGoalItemModel("Gang", ""));
        recyclerItems.add(new ConfirmGoalItemModel("Træning", ""));
        recyclerItems.add(new ConfirmGoalItemModel("Stå", ""));
        recyclerItems.add(new ConfirmGoalItemModel("Anden bevægelse", ""));

        return recyclerItems;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //nothing
    }

    @Override
    public void onPageSelected(int i) {
        if (i == UserConfigActivity.NUM_PAGES - 1) {
            replaceOldListWithNewList();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //nothing
    }

    private void replaceOldListWithNewList() {
        // clear old list
        recyclerItems.clear();

        // add new list
        List<ConfirmGoalItemModel> newList = new ArrayList<>();
        newList.add(new ConfirmGoalItemModel("Fornavn", getArguments().getString(USER_DATA_RECEIVE)));
        newList.add(new ConfirmGoalItemModel("Efternavn", getArguments().getString(USER_DATA_RECEIVE)));
        newList.add(new ConfirmGoalItemModel("Fødselsdag", getArguments().getString(USER_DATA_RECEIVE)));
        newList.add(new ConfirmGoalItemModel("Cykling", getArguments().getString(GOAL_DATA_RECEIVE)));
        newList.add(new ConfirmGoalItemModel("Gang", getArguments().getString(GOAL_DATA_RECEIVE)));
        newList.add(new ConfirmGoalItemModel("Træning", getArguments().getString(GOAL_DATA_RECEIVE)));
        newList.add(new ConfirmGoalItemModel("Stå", getArguments().getString(GOAL_DATA_RECEIVE)));
        newList.add(new ConfirmGoalItemModel("Anden bevægelse", getArguments().getString(GOAL_DATA_RECEIVE)));
        recyclerItems.addAll(newList);

        // notify adapter
        mAdapter.notifyDataSetChanged();
    }
}
