package com.example.root.sens.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.root.sens.dao.interfaces.DatabaseObserver;
import com.example.root.sens.dao.interfaces.SensObserver;
import com.example.root.sens.dao.interfaces.SensSubject;
import com.example.root.sens.data;
import com.example.root.sens.dto.User;
import com.example.root.sens.fragments.AboutFragment;
import com.example.root.sens.fragments.DayDataFragment;
import com.example.root.sens.fragments.HistoryFragment;
import com.example.root.sens.fragments.OverviewFragment;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.notification.NotificationsManager;
import com.example.root.sens.notification.TimeReceiver;
import com.example.root.sens.observers.CreateNewGoalObserver;
import com.example.root.sens.observers.MainFullScreenObserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, SensObserver,
        DatabaseObserver, MainFullScreenObserver, CreateNewGoalObserver {
    private final String TAG = MainActivity.class.getSimpleName();
    private static String[] viewNames = {"Overblik", "Højdepunkter"};
    private static String standardToolbarTitle = "SENS";
    private ViewPager viewPager;
    private SensSubject sensSubject;
    private SharedPreferences sharedPreferences;
    private ViewpagerAdapter viewpagerAdapter;
    private ProgressBar progressBar;
    private Snackbar snackbar;
    private AsyncTask asyncTask;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_content);
        coordinatorLayout = findViewById(R.id.main_a_coordinator_layout);
        setupNavigationDrawer();
        setupViewPager();
        setupDataFetcher();
    }

    private void setupNavigationDrawer() {
        /*
            Why are we setting the Toolbar programmatically?

            Simply because AppCompat theme uses an Actionbar.
            An Actionbar does not have as many features as a
            Toolbar, therefor we set the Toolbar programmatically.

            See this post:
            https://developer.android.com/training/appbar/setting-up
         */
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener((View v) -> burgerMenuOnClickMethod());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
         * Set text in Navigation drawer
         */
        View navigationHeader = navigationView.getHeaderView(0);

        TextView navigationDrawerName = navigationHeader.findViewById(R.id.textViewNavDrawerName);
        TextView navigationDrawerPatientKey = navigationHeader.findViewById(R.id.textViewNavDrawerPatientKey);

        User currentUser = new UserManager().getUserLoggedIn();

        navigationDrawerName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        navigationDrawerPatientKey.setText(currentUser.getPatientKey());
    }



    private void burgerMenuOnClickMethod() {
        if (!drawer.isDrawerOpen(GravityCompat.START) && isFullScreenFragmentOpen()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                changeToolbarTextImage(standardToolbarTitle, R.drawable.ic_burger_menu_icon);
            }
            super.onBackPressed();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isFullScreenFragmentOpen()) {
            super.onBackPressed();
        } else if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    private void setupViewPager() {
        sharedPreferences = getApplication().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        viewPager = findViewById(R.id.viewPager);
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewpagerAdapter);
        viewPager.setCurrentItem(sharedPreferences.getInt(getString(R.string.pagerWindowNumber)
                , 0));
        sharedPreferences.edit().remove(getString(R.string.pagerWindowNumber)).apply();

        PagerSlidingTabStrip pagerSlidingTabStrip = findViewById(R.id.pagerTitleStrip);
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setIndicatorColorResource(R.color.sensBlue);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }


    private void setupDataFetcher() {
        sensSubject = SensDAO.getInstance();
        sensSubject.registerObserver(this); // We register this view as an observer, this is used for when fetching data from SENS
        UserDAO.getInstance().registerObserver(this);
        SensDAO.getInstance().getData(new UserManager().getUserLoggedIn().getPatientKey(), 14);
        fetchDataProgressBar("Henter data");

        new Handler().postDelayed(() -> asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                snackbar.show();
                SensDAO.getInstance().getData(new UserManager().getUserLoggedIn().getPatientKey(), 14);
                return null;
            }
        }.execute(), 1800000); // Fetch data every 30 min
    }

    private void fetchDataProgressBar(String s) {
        snackbar = Snackbar.make(coordinatorLayout, s, Snackbar.LENGTH_INDEFINITE);
        ViewGroup contentLay = (ViewGroup) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
        progressBar = new ProgressBar(coordinatorLayout.getContext());
        contentLay.addView(progressBar, 0);
        snackbar.show();
        startNotification();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if (!isFullScreenFragmentOpen()) {
                changeToolbarTextImage(standardToolbarTitle, R.drawable.ic_burger_menu_icon);
            }
        }
    }

    private void changeToolbarTextImage(String tileText, int image) {
        toolbar.setTitle(tileText);
        toolbar.setNavigationIcon(image);
    }

    private boolean isFullScreenFragmentOpen() {
        int windows = getSupportFragmentManager().getBackStackEntryCount();
        return windows != 0;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            sharedPreferences.edit().putInt(getString(R.string.pagerWindowNumber), viewPager.getCurrentItem()).apply();
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_send_notification) {
            NotificationsManager notificationsManager =
                    new NotificationsManager("String", this);
            notificationsManager.displayNotification();
        } else if (id == R.id.nav_manageGoals) {
            Intent i = new Intent(getApplicationContext(), ManageGoalActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_overlay_layout_main, new AboutFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_initialize_data){
            data.initializeData();
            Snackbar.make(coordinatorLayout, "Demo dataet loadet ind.", Snackbar.LENGTH_LONG).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /*
     * When daydata is fetched from sens successfully,
     * this is called, telling the view to be refreshed.
     */
    @Override
    public void onDataReceived(boolean found) {
        if(!found){
            new Handler().postDelayed(() -> Snackbar.make(coordinatorLayout,"Der er ikke noget data for den valgte dato", Snackbar.LENGTH_LONG).show(),1500);

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    snackbar.dismiss();
                    viewpagerAdapter.notifyDataSetChanged();
                }
            },2000);
        }
    }

    @Override
    public void showFragment(boolean found, Date date) {
        if (isFullScreenFragmentOpen()) {
            return;
        }
        if (!found) {
            /*Snackbar.make(findViewById(R.id.fragment_overlay_layout_main),
                    R.string.MainNoDataForGivenDate,
                    Snackbar.LENGTH_LONG).show();*/
            SensDAO.getInstance().getDataSpecificDate(new UserManager().getUserLoggedIn().getPatientKey(),date);
            fetchDataProgressBar("Dataet hentes, prøv igen om lidt.");
            return;
        }
        String dates = date.toString();

        String dateFormatted = new SimpleDateFormat("EEEE 'den' dd'.' MMMM yyyy",
                new Locale("da")).format(date);
        changeToolbarTextImage(dateFormatted, R.drawable.ic_baseline_clear);

        Bundle bundle = new Bundle();
        bundle.putSerializable("date", date);

        Fragment dayDataFragment = new DayDataFragment();
        dayDataFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_overlay_layout_main, dayDataFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showDataFetchSnack() {
        fetchDataProgressBar("Henter data");
    }

    // Database observer
    @Override
    public void onDataChanged() {
        viewpagerAdapter.notifyDataSetChanged();
    }

    /*
     * When view is removed, we remove our self from the observer list
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensSubject.removeObserver(this);
        UserDAO.getInstance().removeObserver(this);
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }

    /*
     * Setting an alarm to trigger an event every 15 minute.
     * The event to trigger is sending a notification.
     */
    private void startNotification() {
        Intent notifyIntent = new Intent(this, TimeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 42, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    @Override
    public void startManageGoalActivity() {
        Intent i = new Intent(getApplicationContext(), ManageGoalActivity.class);
        startActivity(i);
    }

    /*
     * The view pager is handled here
     * TODO: Move out
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
            return viewNames.length;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}

