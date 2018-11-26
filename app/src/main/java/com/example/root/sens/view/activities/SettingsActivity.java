package com.example.root.sens.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.root.sens.R;

import java.util.ArrayList;
import java.util.List;

import com.example.root.sens.adapters.ItemClickListener;
import com.example.root.sens.adapters.SettingsAdapter;
import com.example.root.sens.dto.ItemModel;

public class SettingsActivity extends AppCompatActivity implements ItemClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mRecyclerView =  findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SettingsAdapter(createItem());
        ((SettingsAdapter) mAdapter).setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<ItemModel> createItem(){
        ArrayList<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("Ryd data", "Dette vil fjerne alt dine data!", R.drawable.ic_trash_alt_solid));
        items.add(new ItemModel("Entry "+ 1, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry "+ 2, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry "+ 3, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry "+ 4, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry "+ 5, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry "+ 6, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry "+ 7, R.mipmap.ic_launcher));

        return items;
    }

    @Override
    public void onClick(View view, int position) {
        // The onClick implementation of the RecyclerView item click
        switch (position){
            case 0:
                Toast.makeText(this, "Alt din data er succesfuldt blevet slettet.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Not yet implmented", Toast.LENGTH_SHORT).show();
        }

    }
}
