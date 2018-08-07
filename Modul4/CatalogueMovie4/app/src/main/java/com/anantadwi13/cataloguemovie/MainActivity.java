package com.anantadwi13.cataloguemovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.anantadwi13.cataloguemovie.Database.FavFilmDB;
import com.anantadwi13.cataloguemovie.Halaman.FavFragment;
import com.anantadwi13.cataloguemovie.Halaman.NowPlayingFragment;
import com.anantadwi13.cataloguemovie.Halaman.SearchFragment;
import com.anantadwi13.cataloguemovie.Halaman.UpcomingFragment;
import com.anantadwi13.cataloguemovie.Model.Film;
import com.anantadwi13.cataloguemovie.Notification.DailyReminderReceiver;
import com.anantadwi13.cataloguemovie.Notification.ReleaseTodayReminderReceiver;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Fragment upcomingFragment = new UpcomingFragment(),
             nowplayingFragment = new NowPlayingFragment(),
             searchFragment = new SearchFragment(),
             favFragment = new FavFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState==null){
            fm.beginTransaction().add(R.id.content_fragment, nowplayingFragment).commit();
            navigationView.getMenu().findItem(R.id.nav_nowplaying).setChecked(true);
        }

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setTitleActionBarWithFragment();
            }
        });

        DailyReminderReceiver.setNotification(this);
        ReleaseTodayReminderReceiver.setNotification(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitleActionBarWithFragment();
    }

    private void setTitleActionBarWithFragment(){
        String title = "";
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_fragment);
        if (fragment instanceof NowPlayingFragment)
            title = getResources().getString(R.string.now_playing);
        else if (fragment instanceof UpcomingFragment)
            title = getResources().getString(R.string.upcoming);
        else if (fragment instanceof SearchFragment)
            title = getResources().getString(R.string.search_result);
        else if (fragment instanceof FavFragment)
            title = getResources().getString(R.string.favourite);
        if (getSupportActionBar()!=null)
            getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.input_title));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.content_fragment);
                if (topFragment instanceof SearchFragment){
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction()
                            .remove(topFragment)
                            .commit();
                    fm.popBackStack();
                }
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString(SearchFragment.EXTRA_JUDUL, query);
                Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.content_fragment);
                if (topFragment instanceof SearchFragment){
                    topFragment.setArguments(bundle);
                    ((SearchFragment) topFragment).reload();
                }
                else {
                    searchFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_fragment, searchFragment)
                            .addToBackStack(null)
                            .commit();
                }
                if (getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(getResources().getString(R.string.search_result));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent setting = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(setting);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        String title = "";

        if (id == R.id.nav_nowplaying) {
            fragment = nowplayingFragment;
            title = getResources().getString(R.string.now_playing);
        } else if (id == R.id.nav_upcoming) {
            fragment = upcomingFragment;
            title = getResources().getString(R.string.upcoming);
        } else if (id == R.id.nav_fav) {
            fragment = favFragment;
            title = getResources().getString(R.string.favourite);
        }

        if (fragment!=null){
            if (getSupportActionBar()!=null)
                getSupportActionBar().setTitle(title);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_fragment,fragment)
                    .commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
