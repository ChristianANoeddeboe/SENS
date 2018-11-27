package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.root.sens.R;


public class HistoryFragment extends Fragment {


    public HistoryFragment() {
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
        View v = inflater.inflate(R.layout.history_f_content, container, false);
        String[] lande = {"Danmark", "Norge", "Sverige", "Finland",
                "Holland", "Italien", "Tyskland", "Frankrig", "Spanien", "Portugal",
                "Nepal", "Indien", "Kina", "Japan", "Thailand",
                "Danmark", "Norge", "Sverige", "Finland",
                "Holland", "Italien", "Tyskland", "Frankrig", "Spanien", "Portugal",
                "Nepal", "Indien", "Kina", "Japan", "Thailand"};

        ArrayAdapter adapter = new ArrayAdapter(v.getContext(), R.layout.history_f_listelement, R.id.listelement_title, lande);

        GridView gridView = v.findViewById(R.id.achieve_gridview);

        gridView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return v;
    }

}
