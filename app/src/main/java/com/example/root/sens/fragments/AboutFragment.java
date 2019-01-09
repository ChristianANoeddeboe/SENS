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

public class AboutFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        ArrayList<AboutItemModel> aboutItemModelArrayList = new ArrayList<>();
        aboutItemModelArrayList.add(new AboutItemModel("Chrsitan Andersen Nøddeboe", "s164150", R.drawable.christian));
        aboutItemModelArrayList.add(new AboutItemModel("Mathias", "s175192", R.drawable.mathias));
        aboutItemModelArrayList.add(new AboutItemModel("Jeppe Trip Kofoed", "s175197", R.drawable.jeppe));
        aboutItemModelArrayList.add(new AboutItemModel("Thyge Skødt Steffensen", "s175176", R.drawable.thyge));

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_about);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());

        recyclerView.setLayoutManager(layoutManager);

        AboutAdapter aboutAdapter = new AboutAdapter(recyclerView.getContext(), aboutItemModelArrayList);

        recyclerView.setAdapter(aboutAdapter);

        ImageButton imageButton = rootView.findViewById(R.id.image_button_exit_about);
        imageButton.setOnClickListener((View v) -> {
            getActivity().onBackPressed();
        });

        FrameLayout frameLayout = rootView.findViewById(R.id.frameLayout_about);
        frameLayout.setOnClickListener((View v) -> {
            getActivity().onBackPressed();
        });

        return rootView;
    }
}
