package com.example.root.sens.view.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.adapters.SetGoalAdapter;
import com.example.root.sens.view.fragments.UserConfigConfirmInfoFragment;
import com.example.root.sens.view.fragments.UserConfigGoalInfoFragment;
import com.example.root.sens.view.fragments.UserConfigNameInfoFragment;

public class UserConfigActivity extends AppCompatActivity {
    private final static String TAG = UserConfigActivity.class.getSimpleName();
    public final static int CONFIRM_INFO_POS = 2;
    UserConfigNameInfoFragment nameInfoFragment = new UserConfigNameInfoFragment();
    UserConfigGoalInfoFragment goalInfoFragment = new UserConfigGoalInfoFragment();
    UserConfigConfirmInfoFragment confirmInfoFragment = new UserConfigConfirmInfoFragment();

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    public static final int NUM_PAGES = 3;

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
        mPager.addOnPageChangeListener(confirmInfoFragment);


        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mPager, true);

        // Removes tabLayout functionality
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }

        slide = findViewById(R.id.user_config_a_slide_button);
        slide.setOnClickListener((View v) -> {
            if(mPager.getCurrentItem()==NUM_PAGES-2){
                setDataArgs();
            }
            if(mPager.getCurrentItem()==NUM_PAGES-1){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            else {
                mPager.setCurrentItem(mPager.getCurrentItem()+1, true);
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplication().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
                case 0: return nameInfoFragment;
                case 1: return goalInfoFragment;
                case 2: return confirmInfoFragment;
                default: Log.d(TAG, "Fatal pager error, position does not exist! "+position);
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private void setDataArgs(){
        Bundle dataArgs = new Bundle();
        dataArgs.putString("Fornavn", String.valueOf(((EditText) findViewById(R.id.tv_user_config_name_first)).getText()));
        dataArgs.putString("Efternavn", String.valueOf(((EditText) findViewById(R.id.tv_user_config_name_last)).getText()));
        dataArgs.putString("Fødselsdag", String.valueOf(((TextView) findViewById(R.id.tv_user_config_birth_date)).getText()));
        dataArgs.putString("Cykling", String.valueOf(((SetGoalAdapter) goalInfoFragment.getmAdapter()).getDataItem(0).getValue()));
        dataArgs.putString("Gang", String.valueOf(((SetGoalAdapter) goalInfoFragment.getmAdapter()).getDataItem(1).getValue()));
        dataArgs.putString("Træning", String.valueOf(((SetGoalAdapter) goalInfoFragment.getmAdapter()).getDataItem(2).getValue()));
        dataArgs.putString("Stå", String.valueOf(((SetGoalAdapter) goalInfoFragment.getmAdapter()).getDataItem(3).getValue()));
        dataArgs.putString("Anden bevægelse", String.valueOf(((SetGoalAdapter) goalInfoFragment.getmAdapter()).getDataItem(4).getValue()));
        confirmInfoFragment.setArguments(dataArgs);
    }
}
