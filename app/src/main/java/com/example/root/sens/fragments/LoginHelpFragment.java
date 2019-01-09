package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.root.sens.R;
import com.example.root.sens.recyclers.adapter.AboutAdapter;
import com.example.root.sens.recyclers.itemmodels.AboutItemModel;

import java.util.ArrayList;

public class LoginHelpFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_help, container, false);

        FrameLayout frameLayout = rootView.findViewById(R.id.frameLayout_login_help);
        frameLayout.setOnClickListener((View v) -> {
            getActivity().onBackPressed();
        });

        return rootView;
    }
}
