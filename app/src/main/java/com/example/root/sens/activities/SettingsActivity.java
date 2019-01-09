package com.example.root.sens.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.root.sens.R;

import java.util.ArrayList;
import java.util.List;

import com.example.root.sens.recyclers.adapter.ItemClickListener;
import com.example.root.sens.recyclers.adapter.SettingsAdapter;
import com.example.root.sens.recyclers.itemmodels.ItemModel;

public class SettingsActivity extends AppCompatActivity implements ItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        recyclerView =  findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SettingsAdapter(createItem());
        ((SettingsAdapter) adapter).setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private List<ItemModel> createItem(){
        ArrayList<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("Ryd data",
                "Dette vil fjerne alt dine data!", R.drawable.ic_trash_alt_solid));
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
        switch (position){
            case 0:
                Snackbar.make(findViewById(R.id.settings_layout),
                        "Alt din data er succesfuldt blevet slettet. (Ikke implementeret)",
                        Snackbar.LENGTH_LONG).show();
                break;
            default:
                Snackbar.make(findViewById(R.id.settings_layout),
                        "Endnu ikke implementeret!",
                        Snackbar.LENGTH_LONG).show();
        }

    }
}
