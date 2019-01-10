package com.example.root.sens.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.root.sens.R;
import com.example.root.sens.fragments.ManageGoalFragment;
import com.example.root.sens.fragments.UserConfigGoalInfoFragment;


public class ManageGoalActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_goal);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.managegoal_FrameLayout_container, new ManageGoalFragment())
                .commit();


    }

}
