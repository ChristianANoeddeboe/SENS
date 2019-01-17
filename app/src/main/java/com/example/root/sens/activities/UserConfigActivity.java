package com.example.root.sens.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.recyclers.adapters.SetGoalAdapter;
import com.example.root.sens.controllers.interfaces.ILoginController;
import com.example.root.sens.controllers.LoginController;
import com.example.root.sens.fragments.UserConfigConfirmFragment;
import com.example.root.sens.fragments.UserConfigGoalInfoFragment;
import com.example.root.sens.fragments.UserConfigNameInfoFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserConfigActivity extends AppCompatActivity {
    private final static String TAG = UserConfigActivity.class.getSimpleName();
    public static final int NUM_PAGES = 3;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private Button slide;
    private Button back;

    private UserConfigNameInfoFragment nameInfoFragment = new UserConfigNameInfoFragment();
    private UserConfigGoalInfoFragment goalInfoFragment = new UserConfigGoalInfoFragment();
    private UserConfigConfirmFragment confirmInfoFragment = new UserConfigConfirmFragment();
    private ILoginController loginController = new LoginController();


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
        mPager.setOffscreenPageLimit(10);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mPager, true);

        // Removes tabLayout functionality
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
        slide = findViewById(R.id.user_config_a_slide_button);
        slide.setOnClickListener((View v) -> {
            switch (mPager.getCurrentItem()) {
                case 0:
                    String firstName = String.valueOf(((EditText) findViewById(R.id.tv_user_config_name_first)).getText());
                    String lastName = String.valueOf(((EditText) findViewById(R.id.tv_user_config_name_last)).getText());
                    String sensorID = getIntent().getExtras().getString("sensorID", "0");
                    Date date = parseDate(String.valueOf(((TextView) findViewById(R.id.tv_user_config_birth_date)).getText()));

                    loginController.save1(firstName, lastName, date, sensorID , confirmInfoFragment);

                    switchPage(v, 1);
                    break;
                case 1:
                    loginController.save2(((SetGoalAdapter) goalInfoFragment.getAdapter()).getDataSet());
                    switchPage(v, 2);
                    break;
                case 2:
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    loginController.confirm();
                    break;
                default:
                    Log.e(TAG, "Page does not exist");
                    finish();
            }
            setSliderButtonText();
        });

        back = findViewById(R.id.btn_user_config_a_back_button);
        back.setOnClickListener((View v) -> {
            if (mPager.getCurrentItem() == 0) {
                finish();
                overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
            } else {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
            }
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
            case 1:
                slide.setText(R.string.Continue);
                back.setText(R.string.Cancel);
                break;
            case 2:
                slide.setText(R.string.Confirm);
                back.setText(R.string.Cancel);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_out_r, R.anim.slide_in_r);
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
        setSliderButtonText();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return nameInfoFragment;
                case 1:
                    return goalInfoFragment;
                case 2:
                    return confirmInfoFragment;
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

    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy", new Locale("da")).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
