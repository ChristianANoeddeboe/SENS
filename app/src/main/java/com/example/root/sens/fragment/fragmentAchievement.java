package com.example.root.sens.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.root.sens.R;


public class fragmentAchievement extends Fragment {


    public fragmentAchievement() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_achievement, container, false);
        String[] lande = {"Danmark", "Norge", "Sverige", "Finland",
                "Holland", "Italien", "Tyskland", "Frankrig", "Spanien", "Portugal",
                "Nepal", "Indien", "Kina", "Japan", "Thailand",
                "Danmark", "Norge", "Sverige", "Finland",
                "Holland", "Italien", "Tyskland", "Frankrig", "Spanien", "Portugal",
                "Nepal", "Indien", "Kina", "Japan", "Thailand"};

        ArrayAdapter adapter = new ArrayAdapter(v.getContext(), R.layout.achievement_list_element, R.id.listelement_title, lande);

        GridView gridView = v.findViewById(R.id.achieve_gridview);

        gridView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return v;
    }

}
