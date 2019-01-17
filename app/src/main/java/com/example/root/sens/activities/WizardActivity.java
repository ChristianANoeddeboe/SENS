package com.example.root.sens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.root.sens.R;
import com.example.root.sens.fragments.WizardContentFragment;

public class WizardActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private Button slide;
    private Button back;

    private WizardContentFragment oversigtFragment = new WizardContentFragment();
    private WizardContentFragment calenderFragment = new WizardContentFragment();
    private WizardContentFragment maalkortFragment = new WizardContentFragment();
    private WizardContentFragment maalkortInfoFragment = new WizardContentFragment();
    private WizardContentFragment hoejdepunkterFragment = new WizardContentFragment();
    private WizardContentFragment burgerMenuIkonFragment = new WizardContentFragment();
    private WizardContentFragment burgerMenuFragment = new WizardContentFragment();
    private final String TAG = WizardActivity.class.getSimpleName();
    private final int NUM_PAGES = 7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.page_wizard);
        mPagerAdapter = new WizardActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(10);

        TabLayout tabLayout = findViewById(R.id.tablayout_wizard);
        tabLayout.setupWithViewPager(mPager, true);

        // Removes tabLayout functionality
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
        slide = findViewById(R.id.user_config_a_slide_button_wizard);
        slide.setOnClickListener((View v) -> {
            switch (mPager.getCurrentItem()) {
                case 0:
                    switchPage(v, 1);
                    break;
                case 1:
                    switchPage(v, 2);
                    break;
                case 2:
                    switchPage(v,3);
                    break;
                case 3:
                    switchPage(v,4);
                    break;
                case 4:
                    switchPage(v,5);
                    break;
                case 5:
                    switchPage(v,6);
                    break;
                case 6:
                    switchPage(v,7);
                    break;
                default:
                    Log.e(TAG, "Page does not exist");
                    finish();
            }
        });


        back = findViewById(R.id.btn_user_config_a_back_button_wizard);
        back.setOnClickListener((View v) -> {
            finish();
            overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
        });
    }

    private void switchPage(View v, int nextPage) {
        mPager.setCurrentItem(nextPage, true);
        InputMethodManager imm = (InputMethodManager) getSystemService(getApplication().INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    oversigtFragment.setDescriptionImage(R.mipmap.oversigt);
                    oversigtFragment.setDescriptionText("Denne side giver dig en hurtig oversigt over" +
                            " hvordan du ligger med dine mål.");
                    return oversigtFragment;
                case 1:
                    calenderFragment.setDescriptionImage(R.mipmap.oversigtcalender);
                    calenderFragment.setDescriptionText("Kalenderen viser dig et tilbageblik på hvordan " +
                            "du præsterede bagud i tiden, samt hvilken dag du nu er på. " +
                            "Farverne beskriver om du opfyldte dine mål (Grøn) den pågældende dag, " +
                            "eller om du ikke gjorde (rød).");
                    return calenderFragment;
                case 2:
                    maalkortFragment.setDescriptionImage(R.mipmap.oversigtcalender);
                    maalkortFragment.setDescriptionText("Hvis man trykker på en dato med en farve, vil " +
                            "man se de mål man havde for den pågældende dag.");
                    return maalkortFragment;
                case 3:
                    maalkortInfoFragment.setDescriptionImage(R.mipmap.oversigtmaalkort);
                    maalkortInfoFragment.setDescriptionText("Kortene viser dine pågældende mål, med hensyn " +
                            "til hvor meget tid du har brugt på et mål og hvor meget du mangler.");
                    return maalkortInfoFragment;
                case 4:
                    hoejdepunkterFragment.setDescriptionImage(R.mipmap.historik);
                    hoejdepunkterFragment.setDescriptionText("Kortene viser dine pågældende mål, med hensyn " +
                            "til hvor meget tid du har brugt på et mål og hvor meget du mangler.");
                    return hoejdepunkterFragment;
                case 5:
                    burgerMenuIkonFragment.setDescriptionImage(R.mipmap.award);
                    burgerMenuIkonFragment.setDescriptionText("Øverst finder du menuen, hvor ydeligere funktioner ligger.");
                    return burgerMenuIkonFragment;
                case 6:
                    burgerMenuFragment.setDescriptionImage(R.mipmap.award);
                    burgerMenuFragment.setDescriptionText("Her har du mulighed for og redigere indstillinger, redigere dine mål og håndtere notifikationer.");
                    return burgerMenuFragment;
                default:
                    Log.d(TAG, "Fatal pager error, position does not exist! " + position);
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
