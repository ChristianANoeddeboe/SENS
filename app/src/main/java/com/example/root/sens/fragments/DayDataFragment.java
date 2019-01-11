package com.example.root.sens.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.auxiliary.DayDataGoalMapper;
import com.example.root.sens.dao.UserDAO;

import java.util.Date;
import java.util.Map;

public class DayDataFragment extends Fragment{
    private Map<String, Integer> goalMap;
    private Map<String, Float> dataMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        Bundle bundle = savedInstanceState != null ? savedInstanceState : getArguments();
        Date date = (Date) bundle.getSerializable("date");

        UserDAO user = UserDAO.getInstance();
        DayDataGoalMapper dayDataGoalMapper = new DayDataGoalMapper(user, date);
        goalMap = dayDataGoalMapper.getGoalMap();
        dataMap = dayDataGoalMapper.getDataMap();

        return rootView;
    }
}
