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

        int page = getArguments().getInt("PageNum");

        setupPageContent(page);

        return rootView;
    }

    private void setupPageContent(int page) {
        switch (page) {
            case 0:
                descriptionImage.setImageResource(R.mipmap.oversigt);
                descriptionText.setText("Denne side giver dig en hurtig oversigt over" +
                        " hvordan du ligger med dine mål.");
                break;
            case 1:
                descriptionImage.setImageResource(R.mipmap.oversigtcalender);
                descriptionText.setText("Farverne på kalenderen beskriver om du opfyldte dine mål (Grøn) den pågældende dag, " +
                        "eller om du ikke gjorde (rød).");
                break;
            case 2:
                descriptionImage.setImageResource(R.mipmap.oversigtcalender);
                descriptionText.setText("Hvis man trykker på en dato med en farve, vil " +
                        "man se de mål man havde for den pågældende dag.");
                break;
            case 3:
                descriptionImage.setImageResource(R.mipmap.oversigtmaalkort);
                descriptionText.setText("Kortene viser dine pågældende mål, med hensyn " +
                        "til hvor meget tid du har brugt på et mål og hvor meget du mangler.");
                break;
            case 4:
                descriptionImage.setImageResource(R.mipmap.historik);
                descriptionText.setText("Her ses de højdepunkter du har opnået, ved at opfylde dine mål hver dag i en periode.");
                break;
            case 5:
                descriptionImage.setImageResource(R.mipmap.award);
                descriptionText.setText("Øverst finder du menuen, hvor ydeligere funktioner ligger.");
                break;
            case 6:
                descriptionImage.setImageResource(R.mipmap.award);
                descriptionText.setText("Her har du mulighed for og redigere indstillinger, redigere dine mål og håndtere notifikationer.");
                break;
        }
    }
}
