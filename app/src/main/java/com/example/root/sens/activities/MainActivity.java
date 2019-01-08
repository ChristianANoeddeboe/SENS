package com.example.root.sens.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.root.sens.R;
import com.example.root.sens.dao.SensDAO;
import com.example.root.sens.dao.interfaces.SensObserver;
import com.example.root.sens.dao.interfaces.Subject;
import com.example.root.sens.fragments.HistoryFragment;
import com.example.root.sens.fragments.OverviewFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensObserver {
    private ViewPager viewPager;
    private Subject s;
    private static String[] viewNames = {"Overview", "Historik"};
    private SharedPreferences sharedPreferences;
    private ViewpagerAdapter viewpagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_a_burgermenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final CoordinatorLayout coordinatorLayout = findViewById(R.id.main_a_coordinator_layout);
        try {
            String snackbarText = getIntent().getExtras().getString("snackbar");

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, snackbarText, Snackbar.LENGTH_LONG);
            snackbar.show();

        } catch (NullPointerException e) {

        }

        sharedPreferences = getApplication().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        viewPager = findViewById(R.id.viewPager);
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewpagerAdapter);
        viewPager.setCurrentItem(sharedPreferences.getInt(getString(R.string.pagerWindowNumber)
                ,0));
        sharedPreferences.edit().remove(getString(R.string.pagerWindowNumber)).apply();
        /**
         * Declare the view pager sliding tab
         */
        PagerSlidingTabStrip pagerSlidingTabStrip = findViewById(R.id.pagerTitleStrip);
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setIndicatorColorResource(R.color.sensBlue);
        pagerSlidingTabStrip.setViewPager(viewPager);
        /**
         * Fetch data from SENS.
         * TODO: Do this periodically aswell, right now only called when app is started.
         */
        s = SensDAO.getInstance();
        s.registerObserver(this); // We register this view as an observer, this is used for when fetching data from SENS
        SensDAO.getInstance().getData("xt9w2r",14);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_manage) {
            // SettingsActivity

            // Save state:
            sharedPreferences.edit().putInt(getString(R.string.pagerWindowNumber), viewPager.getCurrentItem()).apply();
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_about) {
            // Show to about fragment
            Toast.makeText(this, "Denne app er lavet af Gruppe 6", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * When daydata is fetched from sens sucessfully, this is called, telling the view to be refreshed.
     */
    @Override
    public void onDataReceived() {
        viewpagerAdapter.notifyDataSetChanged();
    }
    /*
    The view pager is handled here
     */
    private class ViewpagerAdapter extends FragmentPagerAdapter {
        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return viewNames[position];
        }

        @Override
        public Fragment getItem(int i) {
            Fragment f;
            if (i == 0) {
                f = new OverviewFragment();
            } else {
                f = new HistoryFragment();
            }
            return f;

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    /**
     * When view is removed, we remove ourself from the observer list
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        s.removeObserver(this);
    }
}
