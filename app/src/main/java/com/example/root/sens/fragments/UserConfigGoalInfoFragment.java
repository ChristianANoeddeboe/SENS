package com.example.root.sens.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
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

public class UserConfigGoalInfoFragment extends Fragment
        implements SetGoalAdapter.SetGoalAdapterOnItemClickListener {
    private SetGoalAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.user_config_f_viewpager_goal_info, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_set_goal);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SetGoalAdapter(this,createItem());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private List<SetGoalItemModel> createItem() {
        ArrayList<SetGoalItemModel> items = new ArrayList<>();

        items.add(new SetGoalItemModel("Cykling", 0));
        items.add(new SetGoalItemModel("Gang", 0));
        items.add(new SetGoalItemModel("Træning", 0));
        items.add(new SetGoalItemModel("Stå", 0));
        items.add(new SetGoalItemModel("Søvn", 0));
        items.add(new SetGoalItemModel("Skridt",0));
        return items;
    }

    @Override
    public void onItemClick(View item, int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setCancelable(false);
        newFragment.setTargetFragment(this, position);
        newFragment.show(fm, "TAG");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int hourValue = 0;
        int minuteValue = 0;

        if (resultCode == Activity.RESULT_OK) {
            if (data.getExtras().containsKey("hour")) {
                hourValue = data.getExtras().getInt("hour");
            }
            if(data.getExtras().containsKey("minute")){
                minuteValue = data.getExtras().getInt("minute");
            }
            adapter.getDataSet().get(requestCode).setValue(hourValue*60+minuteValue);
            adapter.notifyDataSetChanged();
        }
    }
}
