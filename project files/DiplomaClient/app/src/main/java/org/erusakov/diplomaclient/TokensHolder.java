package org.erusakov.diplomaclient;

import java.util.HashMap;

public class TokensHolder {
    private static String refreshToken = null;
    private static String accessToken = null;

    private TokensHolder() {

    }

    public static synchronized void setTokens(HashMap<String, String> tokens) {
        accessToken = tokens.get("accessToken");
        refreshToken = tokens.get("refreshToken");
    }

    public static synchronized HashMap<String, String> getTokens() {
        HashMap<String, String> buffer = new HashMap<String, String>();
        buffer.put("accessToken", accessToken);
        buffer.put("refreshToken", refreshToken);
        return buffer;
    }
}
