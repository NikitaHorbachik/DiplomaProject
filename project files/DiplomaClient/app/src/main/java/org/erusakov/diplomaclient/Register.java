package org.erusakov.diplomaclient;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    Button backButton, mainButton; // vars for elements of interface
    ImageView image;
    TextView text;
    TextInputLayout username, password, passwordConfirmation, email, fullName;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register); // choose xml file for interface

        // hooks
        backButton = findViewById(R.id.backButton);
        image = findViewById(R.id.logoImage); // loading elements of interface in vars by id
        text = findViewById(R.id.textView);
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.password);
        email = findViewById(R.id.inputEmail);
        fullName = findViewById(R.id.inputFullName);
        passwordConfirmation = findViewById(R.id.passwordConfirmation);
        mainButton = findViewById(R.id.mainButton);
        context = getApplicationContext();

        // checks text fields and sends info
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        (Validation.validateIsEmpty(username, context))
                        &&
                                (Validation.validateIsEmpty(password, context))
                        &&
                                (Validation.validateIsEmpty(email, context))
                        &&
                                (Validation.validateIsEmpty(passwordConfirmation, context))
                        &&
                                (Validation.validateIsEmpty(fullName, context))
                        &&
                                (Validation.validateIsLonger8(password, context))
                        &&
                                (Validation.validateIsLonger8(passwordConfirmation, context))
                        &&
                                (Validation.validateIsShorter15(username, context))
                        &&
                                (Validation.validateWhiteSpace(username, context))
                        &&
                                (Validation.validateEmail(email, context))
                        &&
                                (Validation.validateEqualString(passwordConfirmation, context, password.getEditText().getText().toString()))
                        &&
                                (Validation.validatePassword(password, context))
                        &&
                                (Validation.validatePassword(passwordConfirmation, context))
                ) {

                }
            }
        });

        // setting up to change activities by click on button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);

                // Select elements which will have transition animation
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View, String>(image, getString(R.string.transition_logo_string)); // string is written inside both xml's
                pairs[1] = new Pair<View, String>(text, getString(R.string.transition_big_text_string));
                pairs[2] = new Pair<View, String>(username, getString(R.string.transition_username_string));
                pairs[3] = new Pair<View, String>(password, getString(R.string.transition_password_string));
                pairs[4] = new Pair<View, String>(mainButton, getString(R.string.transition_main_button_string));
                pairs[5] = new Pair<View, String>(backButton, getString(R.string.transition_second_button_string));

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs); // setting up transition animations
                startActivity(intent, options.toBundle()); // launching new activity

            }
        });
    }
}
