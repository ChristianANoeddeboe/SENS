package com.example.root.sens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.dao.interfaces.UserObserver;
import com.example.root.sens.recyclers.itemmodels.ConfirmGoalItemModel;
import com.example.root.sens.R;
import com.example.root.sens.recyclers.adapters.ConfirmGoalAdapter;
import com.example.root.sens.dto.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserConfigConfirmFragment extends Fragment implements UserObserver {
    private final static String TAG = UserConfigConfirmFragment.class.getSimpleName();

    private RecyclerView.Adapter mAdapter;
    private List<ConfirmGoalItemModel> recyclerItems = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.user_config_f_viewpager_confirm_info, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerView_confirm_goal);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
         mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
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
        recyclerItems.add(new ConfirmGoalItemModel("Søvn", ""));

        return recyclerItems;
    }

    @Override
    public void update(String tag, User user) {
        switch (tag) {
            case User.USERDATA:
                updateRecycler(0, "Fornavn", user.getFirstName());
                updateRecycler(1, "Efternavn", user.getLastName());
                updateRecycler(2, "Fødselsdag", dateFormat(user.getBirthday()));
                break;
            case User.GOALDATA:
                updateRecycler(3, "Cykling", String.valueOf(user.getGoals().get(0).getGoals().get(0).getValue()));
                updateRecycler(4, "Gang", String.valueOf(user.getGoals().get(0).getGoals().get(1).getValue()));
                updateRecycler(5, "Træning", String.valueOf(user.getGoals().get(0).getGoals().get(2).getValue()));
                updateRecycler(6, "Stå", String.valueOf(user.getGoals().get(0).getGoals().get(3).getValue()));
                updateRecycler(7, "Søvn", String.valueOf(user.getGoals().get(0).getGoals().get(4).getValue()));
                break;
            default:
        }
    }

    private void updateRecycler(int position, String description, String value){
        try {
            List<ConfirmGoalItemModel> list = ((ConfirmGoalAdapter) mAdapter).getmDataSet();
            list.set(position, new ConfirmGoalItemModel(description, value));
            mAdapter.notifyDataSetChanged();
        }catch (NullPointerException e){
            Log.e(TAG, e.getMessage() + "\n\n" + e.getStackTrace().toString());
        }
    }

    private String dateFormat(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("da"));
        return (date == null) ?  "" :  df.format(date);
    }
}
