package com.example.root.sens.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.game.Animal;


public class fragmentGame extends Fragment {

    Animal animal;
    TextView animalspeak;



    public fragmentGame() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        animalspeak = v.findViewById(R.id.speaklines);

        animal = new Animal(50);
        animalspeak.setText(animal.talk());

        // Inflate the layout for this fragment
        return v;
    }


}
