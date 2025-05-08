package org.erusakov.diplomaclient;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button callSignUp, mainButton; // vars for elements of interface
    ImageView image;
    TextView text;
    TextInputLayout username, password;
    TextInputEditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login); // choose xml file for interface

        // hooks
        image = findViewById(R.id.logoImage); // loading elements of interface in vars by id
        text = findViewById(R.id.textView);
        callSignUp = findViewById(R.id.signUpButton);
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.password);
        mainButton = findViewById(R.id.mainButton);
        usernameInput = findViewById(R.id.usernameField);
        passwordInput = findViewById(R.id.passwordField);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HttpRequestHandler.getTokens(savedInstanceState, Login.this, usernameInput.getText().toString(), passwordInput.getText().toString());
                Intent intent = new Intent(Login.this, AdminPanel.class);
                startActivity(intent);
            }
        });

        // setting up to change activities by click on button
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);


                // Select elements which will have transition animation
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View, String>(image, getString(R.string.transition_logo_string)); // string is written inside both xml's
                pairs[1] = new Pair<View, String>(text, getString(R.string.transition_big_text_string));
                pairs[2] = new Pair<View, String>(username, getString(R.string.transition_username_string));
                pairs[3] = new Pair<View, String>(password, getString(R.string.transition_password_string));
                pairs[4] = new Pair<View, String>(mainButton, getString(R.string.transition_main_button_string));
                pairs[5] = new Pair<View, String>(callSignUp, getString(R.string.transition_second_button_string));

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs); // setting up transition animations
                startActivity(intent, options.toBundle()); // launching new activity

            }
        });

    }

    private boolean verifyCreds(){


        return true;
    }
}
