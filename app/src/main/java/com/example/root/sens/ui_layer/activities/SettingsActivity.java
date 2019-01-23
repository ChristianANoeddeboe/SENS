package com.example.root.sens.ui_layer.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.root.sens.R;
import com.example.root.sens.dao.SensDAO;
import com.example.root.sens.dao.interfaces.DatabaseObserver;
import com.example.root.sens.dao.interfaces.SensObserver;
import com.example.root.sens.dao.interfaces.SensSubject;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.ui_layer.recyclers.adapters.intefaces.ItemClickListener;
import com.example.root.sens.ui_layer.recyclers.adapters.SettingsAdapter;
import com.example.root.sens.ui_layer.recyclers.itemmodels.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements ItemClickListener, SensObserver, DatabaseObserver {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Snackbar snackbar;
    private SensSubject sensSubject;

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

        sensSubject = SensDAO.getInstance();
        sensSubject.registerObserver(this);
    }

    private List<ItemModel> createItem() {
        ArrayList<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("Ryd DemoData",
                "Dette vil fjerne alt dine DemoData!", R.drawable.ic_baseline_delete_forever_24px));
        items.add(new ItemModel("Hent DemoData", "Henter ny DemoData fra sensoren.", R.drawable.ic_baseline_cloud_download_24px));
        items.add(new ItemModel("Tilgængelighed", "Handicap hjælp",  R.drawable.ic_baseline_accessibility_24px));
        items.add(new ItemModel("Ændre skrifttype","", R.drawable.ic_baseline_font_download_24px));
        items.add(new ItemModel("Farveblind indstillinger","", R.drawable.ic_baseline_invert_colors_24px));

        return items;
    }

    private AlertDialog buildAlert() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.SettingDelete)
                .setMessage(R.string.SettingConfirmDelete)
                .setPositiveButton(R.string.ja, (dialog, which) -> {
                    new UserManager().deleteData();
                    Snackbar.make(findViewById(R.id.settings_layout),
                            R.string.SettingsDataDeleted,
                            Snackbar.LENGTH_LONG).show();
                })
                .setNegativeButton(R.string.nej, (dialog, which) -> {
                    // Closes dialog if nothing else is entered
                })
                .setIcon(R.drawable.ic_baseline_warning_24px)
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
            case 1:
                SensDAO.getInstance().getData(getString(R.string.SensPatientKey), 14);
                snackbar = Snackbar.make(recyclerView, R.string.DownloadingData, Snackbar.LENGTH_INDEFINITE);
                ViewGroup contentLay = (ViewGroup) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
                ProgressBar progressBar = new ProgressBar(this);
                contentLay.addView(progressBar,0);
                snackbar.show();
                break;
            default:
                Snackbar.make(findViewById(R.id.settings_layout),
                        R.string.NotYetImplemented,
                        Snackbar.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onDataReceived(boolean b) {
        if(snackbar != null){
            snackbar.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensSubject = SensDAO.getInstance();
        sensSubject.removeObserver(this);
    }

    @Override
    public void onDataChanged() {

    }
}
