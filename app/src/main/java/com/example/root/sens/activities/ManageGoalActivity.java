package com.example.root.sens.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.fragments.GoalManagementFragment;
import com.example.root.sens.fragments.TimePickerFragment;
import com.example.root.sens.recyclers.adapters.SetGoalAdapter;
import com.example.root.sens.recyclers.itemmodels.SetGoalItemModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;


public class ManageGoalActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private boolean changed = false;
    private ImageButton cancelBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goal_management);
        cancelBtn = findViewById(R.id.managegoal_cancelbtn);
        doneBtn = findViewById(R.id.managegoal_donebtn);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new GoalManagementFragment();
        fm.beginTransaction()
                .replace(R.id.managegoal_FrameLayout_Container, fragment)
                .commit();

        cancelBtn.setOnClickListener((View v) ->{

        });
        doneBtn.setOnClickListener((View v) -> {

        });
    }
}
