package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.root.sens.R;

public class LoginHelpFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_help, container, false);

        ConstraintLayout constraintLayout = rootView.findViewById(R.id.layout_login_help);
        constraintLayout.setOnClickListener((View v) -> getActivity().onBackPressed());

        Button exitButton = rootView.findViewById(R.id.button_login_helper_exit);
        exitButton.setOnClickListener((View v) -> getActivity().onBackPressed());


        return rootView;
    }
}
