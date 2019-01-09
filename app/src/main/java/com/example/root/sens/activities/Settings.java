package com.example.root.sens.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.root.sens.R;

import java.util.ArrayList;
import java.util.List;

import com.example.root.sens.recyclers.adapter.SettingsAdapter;
import com.example.root.sens.recyclers.itemmodels.ItemModel;

public class Settings extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SettingsAdapter(createItem());
        recyclerView.setAdapter(adapter);
    }

    private List<ItemModel> createItem(){
        ArrayList<ItemModel> items = new ArrayList<>();
        for(int i=0; i<10; i++) {
            items.add(new ItemModel("Entry "+i, R.mipmap.ic_launcher));
        }
        return items;
    }
}
