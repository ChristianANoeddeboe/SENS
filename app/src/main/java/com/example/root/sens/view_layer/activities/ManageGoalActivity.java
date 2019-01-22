package com.example.root.sens.view_layer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.root.sens.R;
import com.example.root.sens.view_layer.fragments.EditGoalFragment;


public class ManageGoalActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_goal);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new EditGoalFragment();
        fm.beginTransaction()
                .replace(R.id.activity_goal_man_frame, fragment)
                .commit();
    }
}
