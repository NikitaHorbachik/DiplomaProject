package org.erusakov.diplomaclient;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {
    static String serverIP = "26.143.11.56";
    static String serverPort = "9090";


    public static void getTokens(Bundle bundle, Context context, String login, String password){
        ProgressDialog dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);

        HashMap<String, String> buffer = new HashMap<>();

        String url = "http://" + serverIP + ":" + serverPort + "/api/v1/auth";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    buffer.put("accessToken", response.getString("accessToken"));
                    buffer.put("refreshToken", response.getString("refreshToken"));
                    TokensHolder.setTokens(buffer);

                    dialog.hide();

                    Intent intent = new Intent(context, UserProfile.class);
                    startActivity(context, intent, bundle);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
                Toast toast = Toast.makeText(context, "Invalid credentials",Toast.LENGTH_LONG);
                toast.show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s", login, password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };

        HttpRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void sendUser(Bundle bundle, Context context, String username, String password, String fullname, String email, String token) {
        String url = "http://" + serverIP + ":" + serverPort + "/api/v1/auth";

        JSONObject userInfo = new JSONObject();
        try {
            userInfo.put("username", username);
            userInfo.put("email", email);
            userInfo.put("name", fullname.split(" ")[0]);
            userInfo.put("surname", fullname.split(" ")[1]);
            userInfo.put("password", password);

            JSONArray userRoles = new JSONArray();
            userRoles.put("user");

            userInfo.put("roles", userRoles);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userInfo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
    }

    public static HashMap<String, Object> getUser(Context context, String token) {
        HashMap<String, Object> buffer = new HashMap<>();
        String url = "http://" + serverIP + ":" + serverPort + "/api/v1/users/17";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    buffer.put("username", response.getString("username"));
                    System.out.println("User: " + buffer.get("username"));
                    buffer.put("roles", response.getJSONArray("roles"));
                    System.out.println(response.getJSONArray("roles").getString(0));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };

        HttpRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);

        return buffer;
    }
}
