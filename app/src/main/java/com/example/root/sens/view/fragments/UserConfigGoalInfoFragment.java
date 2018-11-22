package com.example.root.sens.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.dto.SetGoalItemModel;
import com.example.root.sens.R;
import com.example.root.sens.adapters.SetGoalAdapter;
import com.example.root.sens.view.activities.UserConfigActivity;

import java.util.ArrayList;
import java.util.List;

public class UserConfigGoalInfoFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    GoalDataPassListener mCallback;

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //nothing
    }

    @Override
    public void onPageSelected(int i) {
        if(i==UserConfigActivity.NUM_PAGES-1) mCallback.passGoalData("goal_data");
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //nothing
    }

    public interface GoalDataPassListener{
        public void passGoalData(String data);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mCallback = (GoalDataPassListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.user_config_f_viewpager_goal_info, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_set_goal);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SetGoalAdapter(createItem());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mCallback.passGoalData("goal data");
    }

    private List<SetGoalItemModel> createItem() {
        ArrayList<SetGoalItemModel> items = new ArrayList<SetGoalItemModel>();

        items.add(new SetGoalItemModel("Cykling"));
        items.add(new SetGoalItemModel("Gang"));
        items.add(new SetGoalItemModel("Træning"));
        items.add(new SetGoalItemModel("Stå"));
        items.add(new SetGoalItemModel("Anden bevægelse"));

        return items;
    }
}
