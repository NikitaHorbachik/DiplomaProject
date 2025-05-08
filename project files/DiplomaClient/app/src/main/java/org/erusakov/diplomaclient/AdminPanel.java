package org.erusakov.diplomaclient;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AdminPanel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        createUserPane("mlem", "pepe");
        createUserPane("mudadadadada", "pepe");
    }

    private void createUserPane(String username, String role) {
        LinearLayout linearHorizontal, linearVertical;
        TextView textUsername, textRole;
        Button editButton;

        linearHorizontal = new LinearLayout(AdminPanel.this);
        linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParamsLinearHorizontal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsLinearHorizontal.setMargins(30,30,30,0);
        linearHorizontal.setGravity(Gravity.CENTER);
        linearHorizontal.setBackgroundColor(getColor(R.color.background_color_variant));
        linearHorizontal.setLayoutParams(layoutParamsLinearHorizontal);

        linearVertical = new LinearLayout(AdminPanel.this);
        linearVertical.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParamsLinearVertical = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsLinearVertical.weight = 1;
        linearVertical.setPadding(30,30,30,30);
        linearVertical.setLayoutParams(layoutParamsLinearVertical);

        textUsername = new TextView(AdminPanel.this);
        LinearLayout.LayoutParams layoutParamsTextUsername = new LinearLayout.LayoutParams(500, ViewGroup.LayoutParams.WRAP_CONTENT);
        textUsername.setTextColor(getResources().getColor(R.color.text_color));
        textUsername.setTextSize(24);
        textUsername.setText(username);
        textUsername.setLayoutParams(layoutParamsTextUsername);

        textRole = new TextView(AdminPanel.this);
        textRole.setTextColor(getResources().getColor(R.color.faded_text_color));
        textRole.setTextSize(20);
        textRole.setText(role);

        editButton = new Button(AdminPanel.this);
        editButton.setText(R.string.edit_string_caps);
        LinearLayout.LayoutParams layoutParamsEditButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsEditButton.weight = 1;
        layoutParamsEditButton.setMargins(30,30,30,30);
        editButton.setBackgroundTintList(getResources().getColorStateList(R.color.button_color_list));
        editButton.setLayoutParams(layoutParamsEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(AdminPanel.this, username,Toast.LENGTH_LONG);
                toast.show();
            }
        });

        linearContainer.addView(linearHorizontal);
        linearHorizontal.addView(linearVertical);
        linearHorizontal.addView(editButton);
        linearVertical.addView(textUsername);
        linearVertical.addView(textRole);
    }

    // menu listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        startActivity(NavigationUtils.getNavigationIntent(item, AdminPanel.this));

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
