package org.erusakov.diplomaclient;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AutomobilePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout linearContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_panel_layout); // choose xml file for interface

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        linearContainer = findViewById(R.id.linearContainer);

        setSupportActionBar(toolbar); // set toolbar as action bar

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.email_error, R.string.email_error); // create toggle for menu
        drawerLayout.addDrawerListener(toggle); // add toggle to interface
        toggle.syncState(); // sync

        navigationView.setNavigationItemSelectedListener(this); // set menu listener
    }
    // menu listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        startActivity(NavigationUtils.getNavigationIntent(item, AutomobilePage.this));

        return true;
    }

    //close menu on back press
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
