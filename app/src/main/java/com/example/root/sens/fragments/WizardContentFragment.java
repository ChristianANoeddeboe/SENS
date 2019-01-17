package com.example.root.sens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;

public class WizardContentFragment extends Fragment {

    private TextView descriptionText;
    private ImageView descriptionImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_content, container, false);

        descriptionText = rootView.findViewById(R.id.WizardDescriotionTextView);
        descriptionImage = rootView.findViewById(R.id.WizardDescriptionImageView);

        return rootView;
    }

    public void setDescriptionText(String text) {
        descriptionText.setText(text);
    }

    public void setDescriptionImage(int resource) {
        descriptionImage.setImageResource(resource);
    }
}
