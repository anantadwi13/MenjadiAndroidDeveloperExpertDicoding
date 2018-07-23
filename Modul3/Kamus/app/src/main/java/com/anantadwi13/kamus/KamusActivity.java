package com.anantadwi13.kamus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.anantadwi13.kamus.Halaman.EngInd;
import com.anantadwi13.kamus.Halaman.IndEng;

public class KamusActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ENG_IND = 1, IND_ENG = 2;
    private int TYPE=1;

    private Fragment engIndFragment = new EngInd(),
            indEngFragment = new IndEng();
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState==null) {
            TYPE = ENG_IND;
            getSupportFragmentManager().beginTransaction().add(R.id.content_halaman_fragment, engIndFragment).commit();
        }

        updateMode();
    }

    private void updateMode(){
        switch (TYPE){
            case ENG_IND:
                navigationView.getMenu().findItem(R.id.engind).setChecked(true);
                getSupportActionBar().setTitle(getString(R.string.english_indonesia));
                break;
            case IND_ENG:
                navigationView.getMenu().findItem(R.id.indeng).setChecked(true);
                getSupportActionBar().setTitle(getString(R.string.indonesia_english));
                break;
        }
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

        if (id == R.id.engind) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_halaman_fragment, engIndFragment)
                    .commit();
            TYPE = ENG_IND;
        } else if (id == R.id.indeng) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_halaman_fragment, indEngFragment)
                    .commit();
            TYPE = IND_ENG;
        }

        updateMode();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
