package org.erusakov.diplomaclient;

import java.util.HashMap;

public class UserHolder {
    private static String username = null;
    private static String fullname = null;
    private static String email = null;
    private static String role = null;
    private static int numberOfNotifications = 0;

    private UserHolder() {

    }

    public static synchronized String getUsername() {
        return username;
    }

    public static synchronized void setUsername(String username) {
        UserHolder.username = username;
    }
}
