package com.example.root.sens.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.root.sens.R;
import com.example.root.sens.dao.SensDAO;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dao.interfaces.SensObserver;
import com.example.root.sens.dao.interfaces.Subject;
import com.example.root.sens.dto.User;
import com.example.root.sens.fragments.AboutFragment;
import com.example.root.sens.fragments.HistoryFragment;
import com.example.root.sens.fragments.OverviewFragment;
import com.example.root.sens.notification.NotificationsManager;
import com.example.root.sens.notification.TimeReceiver;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensObserver {
    private ViewPager viewPager;
    private Subject s;
    private static String[] viewNames = {"Overview", "Historik"};
    private SharedPreferences sharedPreferences;
    private ViewpagerAdapter viewpagerAdapter;
    private ProgressBar progressBar;
    private Snackbar snackbar;
    private AsyncTask asyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Navigation Drawer
         */
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

        /**
         * Set text in Navigation drawer
         */
        View navigationHeader = navigationView.getHeaderView(0);

        TextView navigationDrawerName = navigationHeader.findViewById(R.id.textViewNavDrawerName);
        TextView navigationDrawerSensorId = navigationHeader.findViewById(R.id.textViewNavDrawerSensorID);

        User currentUser = UserDAO.getInstance().getUserLoggedIn();
        System.out.println(currentUser.getFirstName());
        System.out.println(currentUser.getLastName());

        navigationDrawerName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        navigationDrawerSensorId.setText(currentUser.getSensors().get(0).getId());


        /**
         * Initializing the view pager
         */
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
         */
        CoordinatorLayout coordinatorLayout = findViewById(R.id.main_a_coordinator_layout);
        s = SensDAO.getInstance();
        s.registerObserver(this); // We register this view as an observer, this is used for when fetching data from SENS
        SensDAO.getInstance().getData("xt9w2r");
        fetchDataProgressBar(coordinatorLayout);

        new Handler().postDelayed(() -> asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                snackbar.show();
                SensDAO.getInstance().getData("xt9w2r");
                return null;
            }
        }.execute(), 1800000); // Fetch data every 30 min
    }

    private void fetchDataProgressBar(CoordinatorLayout coordinatorLayout) {
        snackbar = Snackbar.make(coordinatorLayout, "Henter data", Snackbar.LENGTH_INDEFINITE);
        ViewGroup contentLay = (ViewGroup) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
        progressBar = new ProgressBar(coordinatorLayout.getContext());
        contentLay.addView(progressBar,0);
        snackbar.show();
        startNotification();
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
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            sharedPreferences.edit().putInt(getString(R.string.pagerWindowNumber), viewPager.getCurrentItem()).apply();
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        }
        else if(id == R.id.nav_send_notification){
            NotificationsManager notificationsManager = new NotificationsManager("String", this);
            notificationsManager.displayNotification();
        }
        else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_overlay_layout_main, new AboutFragment())
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * When daydata is fetched from sens successfully, this is called, telling the view to be refreshed.
     */
    @Override
    public void onDataReceived() {
        snackbar.dismiss();
        viewpagerAdapter.notifyDataSetChanged();
    }

    /**
     * The view pager is handled here
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
        if(asyncTask != null){
            asyncTask.cancel(true);
        }
    }

    /**
     * Setting an alarm to trigger an event every 15 minute.
     * The event to trigger is sending a notification.
     */
    private void startNotification(){
        Intent notifyIntent = new Intent(this,TimeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 42, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }
}
