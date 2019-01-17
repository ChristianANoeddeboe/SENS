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
import com.example.root.sens.fragments.LoginHelpFragment;

public class WizardActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private Button slide;
    private Button back;

    private LoginHelpFragment aboutFragment1 = new LoginHelpFragment();
    private LoginHelpFragment aboutFragment2 = new LoginHelpFragment();
    private LoginHelpFragment aboutFragment3 = new LoginHelpFragment();
    private LoginHelpFragment aboutFragment4 = new LoginHelpFragment();
    private final String TAG = WizardActivity.class.getSimpleName();
    private final int NUM_PAGES = 4;
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
                default:
                    Log.e(TAG, "Page does not exist");
                    finish();
            }
            setSliderButtonText();
        });


        back = findViewById(R.id.btn_user_config_a_back_button_wizard);
        back.setOnClickListener((View v) -> {
            finish();
            overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
            setSliderButtonText();
        });
    }

    private void switchPage(View v, int nextPage) {
        mPager.setCurrentItem(nextPage, true);
        InputMethodManager imm = (InputMethodManager) getSystemService(getApplication().INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void setSliderButtonText() {
        int counter = mPager.getCurrentItem();
        switch (counter) {
            case 0:
                slide.setText(R.string.Continue);
                back.setText(R.string.Skip);
                break;
            case 1:
                slide.setText(R.string.Continue);
                back.setText(R.string.Skip);
                break;
            case 2:
                slide.setText(R.string.Continue);
                back.setText(R.string.Skip);
                break;
            case 3:
                slide.setText(R.string.Continue);
                back.setText(R.string.Skip);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
        setSliderButtonText();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
        setSliderButtonText();
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
                    return aboutFragment1;
                case 1:
                    return aboutFragment2;
                case 2:
                    return aboutFragment3;
                case 3:
                    return aboutFragment4;
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
