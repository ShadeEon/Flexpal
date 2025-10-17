package com.example.flexpal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_USE_BIO = "use_bio";
    private static final String FIRST_TIME_KEY = "isFirstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstTime = settings.getBoolean(FIRST_TIME_KEY, true);
        boolean useBio = settings.getBoolean(KEY_USE_BIO, false);

        // Show the splash screen for 3 seconds
        new Handler().postDelayed(() -> {
            if (isFirstTime) {
                Intent intent = new Intent(SplashActivity.this, WelcomePage.class);
                startActivity(intent);
                finish();
            }
            else {
                if (useBio) {
                    Intent intent = new Intent(SplashActivity.this, fgplogin.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);

        final ImageView imageView = findViewById(R.id.splashbg);
        final ImageView imageView1 = findViewById(R.id.splashicon);
        final ImageView imageView2 = findViewById(R.id.splashtxt);

        // Set up the up animation for splashbg
        Animation upAnimation = new TranslateAnimation(0, 0, 1000, 0); // Adjust the "1000" as needed
        upAnimation.setDuration(1000);

        // Set the duration of the translate animation in milliseconds for splashicon
        Animation alphaAnimation1 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation1.setDuration(2500); // Adjust the duration as needed

        // Set the duration of the alpha (fade) animation in milliseconds for splashtxt
        Animation alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation2.setDuration(2500); // Adjust the duration as needed

        // Start the animations for each ImageView
        imageView.startAnimation(upAnimation);
        imageView1.startAnimation(alphaAnimation1);
        imageView2.startAnimation(alphaAnimation2);

        // You can add animation listeners if needed
        upAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started for splashbg
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended for splashbg
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated for splashbg
            }
        });
    }
}