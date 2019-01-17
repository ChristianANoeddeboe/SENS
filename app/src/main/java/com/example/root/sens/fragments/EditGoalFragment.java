package com.example.root.sens.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.recyclers.adapters.EditGoalAdapter;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EditGoalFragment extends Fragment implements EditGoalAdapter.OnItemClickListener {
    private EditGoalAdapter adapter;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_edit_goal, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_manage_goal);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EditGoalAdapter(this, createItem());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public RecyclerView.Adapter getAdapter () {
        return adapter;
    }

    @Override
    public void onItemClick (View item,int position){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setCancelable(false);
        newFragment.setTargetFragment(this, position);
        newFragment.show(fm, "TAG");
    }

    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        int hourValue = 0;
        int minuteValue = 0;

        if (resultCode == Activity.RESULT_OK) {
            if (data.getExtras().containsKey("hour")) {
                hourValue = data.getExtras().getInt("hour");
            }
            if (data.getExtras().containsKey("minute")) {
                minuteValue = data.getExtras().getInt("minute");
            }
            adapter.getDataSet().get(requestCode).setValue(hourValue * 60 + minuteValue);
            adapter.notifyDataSetChanged();
        }
    }

    private List<SetGoalItemModel> createItem(){
        List<SetGoalItemModel> tempList = new ArrayList<>();

        Map<ActivityCategories, Integer> oldValues = new UserManager().getGoals(new Date());

        for(ActivityCategories activityCategories : ActivityCategories.values()){
            tempList.add(new SetGoalItemModel(activityCategories, String.valueOf(activityCategories), oldValues.get(activityCategories)));
        }

        return tempList;
    }
}
