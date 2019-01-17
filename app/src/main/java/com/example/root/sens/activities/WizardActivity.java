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
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
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
                    finish();
                    break;
                default:
                    Log.e(TAG, "Page does not exist");
                    finish();
            }
        });


        back = findViewById(R.id.btn_user_config_a_back_button_wizard);
        back.setOnClickListener((View v) -> {
            finish();
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
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            Bundle args = new Bundle();
            args.putInt("PageNum",position);
            switch (position) {
                case 0:
                    oversigtFragment.setArguments(args);
                    return oversigtFragment;
                case 1:
                    calenderFragment.setArguments(args);
                    return calenderFragment;
                case 2:
                    maalkortFragment.setArguments(args);
                    return maalkortFragment;
                case 3:
                    maalkortInfoFragment.setArguments(args);
                    return maalkortInfoFragment;
                case 4:
                    hoejdepunkterFragment.setArguments(args);
                    return hoejdepunkterFragment;
                case 5:
                    burgerMenuIkonFragment.setArguments(args);
                    return burgerMenuIkonFragment;
                case 6:
                    burgerMenuFragment.setArguments(args);
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
