package org.erusakov.diplomaclient;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    Animation topAnimation, bottomAnimation; // vars for animations
    ImageView image; // var for logo image
    TextView text; // var for text

    @Override
    protected void onCreate(Bundle savedInstanceState) { // what happens when app starts
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removing status bars
        setContentView(R.layout.activity_main); // choosing xml file with interface

        // loading animations xml files to vars
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        // hooks
        image = findViewById(R.id.imageView); // loading elements of interface in vars by id
        text = findViewById(R.id.textView4);


        // setting up animation
        image.setAnimation(topAnimation);
        text.setAnimation(bottomAnimation);

        // do something after set time
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Main.this, Login.class); // load new activity

            // Select elements which will have transition animation
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(image, getString(R.string.transition_logo_string)); // string is written inside both xml's
            pairs[1] = new Pair<View, String>(text, getString(R.string.transition_big_text_string));

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Main.this, pairs); // setting up transition animations
            startActivity(intent, options.toBundle()); // launching new activity
            
        }, 3000); // delay
    }
}
