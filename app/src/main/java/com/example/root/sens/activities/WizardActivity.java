package com.example.root.sens.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
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
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.root.sens.R;
import com.example.root.sens.fragments.WizardContentFragment;

public class WizardActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    private WizardContentFragment oversigtFragment = new WizardContentFragment();
    private WizardContentFragment calenderFragment = new WizardContentFragment();
    private WizardContentFragment maalkortFragment = new WizardContentFragment();
    private WizardContentFragment maalkortInfoFragment = new WizardContentFragment();
    private WizardContentFragment maalkortMereInfoFragment = new WizardContentFragment();
    private WizardContentFragment hoejdepunkterFragment = new WizardContentFragment();
    private WizardContentFragment burgerMenuIkonFragment = new WizardContentFragment();
    private WizardContentFragment burgerMenuFragment = new WizardContentFragment();
    private final String TAG = WizardActivity.class.getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    private final int NUM_PAGES = 8;

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

        findViewById(R.id.user_config_a_slide_button_wizard).setOnClickListener((View v) -> switchPage(true));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

//        This is to aggressive, still figuering it out- Thyge
//        findViewById(R.id.page_wizard).setOnTouchListener((v, event) -> {
//            int x = (int) event.getX();
//            int y = (int) event.getY();
//
//            if(y > height-60){
//                return true;
//            }
//
//            if(x >width/2){
//                switchPage(false);
//            }
//            else {
//                switchPage(true);
//            }
//
//            return true;
//        });

        findViewById(R.id.btn_user_config_a_back_button_wizard).setOnClickListener((View v) -> finish());
    }

    private void switchPage(boolean forward) {
        int currentItem = mPager.getCurrentItem();
        if(forward){
            if(currentItem == 6){
                finish();
            }
            mPager.setCurrentItem(currentItem + 1, true);
        } else {
            mPager.setCurrentItem(currentItem - 1, true);
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
                    maalkortMereInfoFragment.setArguments(args);
                    return maalkortMereInfoFragment;
                case 5:
                    hoejdepunkterFragment.setArguments(args);
                    return hoejdepunkterFragment;
                case 6:
                    burgerMenuIkonFragment.setArguments(args);
                    return burgerMenuIkonFragment;
                case 7:
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
