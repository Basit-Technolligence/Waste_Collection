package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Home");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else

            super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_trip) {
            Intent i = new Intent(HomeActivity.this, TripsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_dritime) {
            Intent i = new Intent(HomeActivity.this, DrivingTimeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_rissue) {
            Intent i = new Intent(HomeActivity.this, IssueActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_logout) {
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}

