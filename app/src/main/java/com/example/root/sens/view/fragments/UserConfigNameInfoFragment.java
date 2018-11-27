package com.example.root.sens.view.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.sens.R;

import java.util.Calendar;

public class UserConfigNameInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.user_config_f_viewpager_name_info, container, false);
        TextView datePickerText = rootView.findViewById(R.id.tv_user_config_birth_date);

        datePickerText.setOnClickListener((View v) -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            DialogFragment newFragment = new com.example.root.sens.fragment.DatePickerFragment();
            newFragment.show(fm, "datePicker");
        });

        return rootView;
    }

}
