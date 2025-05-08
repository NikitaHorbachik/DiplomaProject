package org.erusakov.diplomaclient;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

public class NavigationUtils {
    public static Intent getNavigationIntent(MenuItem item, Context context) {
        Intent intentBuffer = null;
        int itemId = item.getItemId();

        if(itemId == R.id.nav_admin_panel) {
            intentBuffer = new Intent(context, AdminPanel.class);
        } else if (itemId == R.id.nav_profile) {
            intentBuffer = new Intent(context, UserProfile.class);
        } else if (itemId == R.id.nav_notifications) {
            intentBuffer = new Intent(context, NotificationsPanel.class);
        } else if (itemId == R.id.nav_automobiles) {
            intentBuffer = new Intent(context, AutomobilePanel.class);
        }

        return intentBuffer;
    }
}
