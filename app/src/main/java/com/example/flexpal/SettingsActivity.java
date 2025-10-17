package com.example.flexpal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    Switch enablebm;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_USE_BIO = "use_bio";
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        enablebm = findViewById(R.id.enablebm);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useBio = preferences.getBoolean(KEY_USE_BIO, false);

        // Update the state of the switch based on the preference value
        enablebm.setChecked(useBio);
        enablebm.setText(useBio ? "ON" : "OFF");


        // Listen for changes in the switch state
        enablebm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update the preference value when the switch state changes
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(KEY_USE_BIO, isChecked);
            editor.apply();

            enablebm.setText(isChecked ? "ON" : "OFF");
        });

        CardView abs = (CardView) findViewById(R.id.Saboutus);
        abs.setOnClickListener(v -> openaboutus());

        CardView helps = (CardView) findViewById(R.id.Shelp);
        helps.setOnClickListener(v -> openheplg());

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.discover:
                        startActivity(new Intent(getApplicationContext(), DiscoverWorkoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reports:
                        startActivity(new Intent(getApplicationContext(), ReportsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });

        CardView slog = findViewById(R.id.Slogout);
        slog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the CardView click event
                showConfirmationDialog();
            }
        });

        CardView sclear = findViewById(R.id.Sclear);
        sclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the CardView click event
                showClearPreferencesConfirmationDialog();
            }
        });
    }

    private void showClearPreferencesConfirmationDialog() {
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        // Set the dialog title and message
        builder.setTitle("Clear Progress");
        builder.setMessage("Are you sure you want to clear all your data?");

        // Add buttons for positive and negative actions
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // User confirmed, clear all shared preferences
            clearAllPreferences();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            // User canceled, do nothing
        });

        // Create and show the AlertDialog
        builder.create().show();
    }

    private void clearAllPreferences() {
        // This method is called when the user confirms to clear preferences
        SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Add another preference
        SharedPreferences preferences2 = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.apply();

        restartApp();
    }

    private void restartApp() {
        Intent intent = new Intent(SettingsActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    private void showConfirmationDialog() {
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        // Set the dialog title and message
        builder.setTitle("Close Flexpal");
        builder.setMessage("Are you sure you want to close the app?");

        // Add buttons for positive and negative actions
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // User confirmed, close the app
            closeApp();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            // User canceled, do nothing
        });

        // Create and show the AlertDialog
        builder.create().show();
    }

    private void closeApp() {
        finishAffinity();
        System.exit(0);
    }

    public void openaboutus(){
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
        finish();
    }
    public void openheplg(){
        Intent intent = new Intent(this, Helps.class);
        startActivity(intent);
        finish();
    }
}