package com.example.root.sens.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.root.sens.R;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Record;
import com.example.root.sens.recyclers.adapters.intefaces.ItemClickListener;
import com.example.root.sens.recyclers.adapters.SettingsAdapter;
import com.example.root.sens.recyclers.itemmodels.ItemModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class SettingsActivity extends AppCompatActivity implements ItemClickListener {
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
        ((SettingsAdapter) adapter).setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    // TODO: Add fake settings for prettiness
    private List<ItemModel> createItem() {
        ArrayList<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("Ryd data",
                "Dette vil fjerne alt dine data!", R.drawable.ic_trash_alt));
        items.add(new ItemModel("Entry " + 1, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry " + 2, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry " + 3, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry " + 4, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry " + 5, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry " + 6, R.mipmap.ic_launcher));
        items.add(new ItemModel("Entry " + 7, R.mipmap.ic_launcher));

        return items;
    }

    private AlertDialog buildAlert() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.SettingDelete)
                .setMessage(R.string.SettingConfirmDelete)
                .setPositiveButton(R.string.ja, (dialog, which) -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    RealmList<DayData> dayData = UserDAO.getInstance().getUserLoggedIn().getDayData();
                    for (DayData currentDayData : dayData) {
                        for (Record currentRecord : currentDayData.getRecords()) {
                            System.out.println(currentRecord.getType() + " " + currentRecord.getValue());
                            currentRecord.setValue(0);
                        }
                    }

                    realm.commitTransaction();
                    Snackbar.make(findViewById(R.id.settings_layout),
                            R.string.SettingsDataDeleted,
                            Snackbar.LENGTH_LONG).show();
                })
                .setNegativeButton(R.string.nej, (dialog, which) -> {
                    // Closes dialog if nothing else is entered
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false);

        return builder.create();
    }

    @Override
    public void onClick(View view, int position) {
        switch (position) {
            case 0:

                AlertDialog current = buildAlert();
                current.show();

                break;
            default:
                Snackbar.make(findViewById(R.id.settings_layout),
                        R.string.NotYetImplemented,
                        Snackbar.LENGTH_LONG).show();
                break;
        }

    }
}
