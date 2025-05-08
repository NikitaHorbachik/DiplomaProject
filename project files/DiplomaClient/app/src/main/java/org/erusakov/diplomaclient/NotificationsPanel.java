package org.erusakov.diplomaclient;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class NotificationsPanel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout linearContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notifications_panel); // choose xml file for interface

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

        createNotification("MLEM", "fghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg d", getResources().getDrawable(R.drawable.background_notification_red_shape));
        createNotification("MLEM", "fghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg d", getResources().getDrawable(R.drawable.background_notification_green_shape));
        createNotification("MLEM", "fghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg d", getResources().getDrawable(R.drawable.background_notification_yellow_shape));
        createNotification("MLEM", "fghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg dfghfghnfldhgjlthf fdglhn flgjh lfgh flgkhj fl llllll dlfjkg jldfkg d", getResources().getDrawable(R.drawable.background_notification_grey_shape));
    }

    private void createNotification(String title, String notificationText, Drawable background) {
        LinearLayout linearVertical;
        TextView textTitle, textNotification;

        linearVertical = new LinearLayout(NotificationsPanel.this);
        linearVertical.setOrientation(LinearLayout.VERTICAL);
        linearVertical.setGravity(Gravity.CENTER);
        linearVertical.setBackground(background);
        LinearLayout.LayoutParams layoutParamsLinearVertical = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsLinearVertical.setMargins(30,30,30,0);
        linearVertical.setPadding(30,30,30,30);
        linearVertical.setLayoutParams(layoutParamsLinearVertical);

        textTitle = new TextView(NotificationsPanel.this);
        LinearLayout.LayoutParams layoutParamsTextTitle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsTextTitle.setMargins(0,0,0,30);
        textTitle.setTextColor(getResources().getColor(R.color.text_color));
        textTitle.setTextSize(24);
        textTitle.setText(title);
        textTitle.setLayoutParams(layoutParamsTextTitle);

        textNotification = new TextView(NotificationsPanel.this);
        LinearLayout.LayoutParams layoutParamsTextNotification = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textNotification.setTextColor(getResources().getColor(R.color.faded_text_color));
        textNotification.setTextSize(20);
        textNotification.setText(notificationText);
        textNotification.setLayoutParams(layoutParamsTextNotification);

        linearContainer.addView(linearVertical);
        linearVertical.addView(textTitle);
        linearVertical.addView(textNotification);
    }

    // menu listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        startActivity(NavigationUtils.getNavigationIntent(item, NotificationsPanel.this));

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
