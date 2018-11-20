package com.example.root.sens.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.root.sens.R;
import com.example.root.sens.fragment.UserConfigConfirmInfoFragment;
import com.example.root.sens.fragment.UserConfigGoalInfoFragment;
import com.example.root.sens.fragment.UserConfigNameInfoFragment;

public class UserConfigActivity extends AppCompatActivity {
    private final static String TAG = UserConfigActivity.class.getSimpleName();
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private Button slide;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_config_a);
        // Give an slide effect, like view pagers, but for activities
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mPager, true);

        slide = findViewById(R.id.user_config_a_slide_button);
        slide.setOnClickListener((View v) -> {
            if(mPager.getCurrentItem()==NUM_PAGES-1){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            else{
                mPager.setCurrentItem(mPager.getCurrentItem()+1, true);
            }
            setSliderButtonText();
        });

        back = findViewById(R.id.btn_user_config_a_back_button);
        back.setOnClickListener((View v) -> {
            if(mPager.getCurrentItem()==0){
                finish();
                overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
            }
            else{
                mPager.setCurrentItem(mPager.getCurrentItem()-1, true);
            }
            setSliderButtonText();
        });
    }

    private void setSliderButtonText(){
        int counter = mPager.getCurrentItem();
        switch (counter){
            case 0:
                slide.setText("Videre");
                back.setText("Annullér");
                break;
            case 1:
                slide.setText("Videre");
                back.setText("Annullér");
                break;
            case 2:
                slide.setText("Bekræft");
                back.setText("Annullér");
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
            // An exit animation
            overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
        setSliderButtonText();
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0: return new UserConfigNameInfoFragment();
                case 1: return new UserConfigGoalInfoFragment();
                case 2: return new UserConfigConfirmInfoFragment();
                default: Log.d(TAG, "Fatal pager error, position does not exist! "+position);
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
