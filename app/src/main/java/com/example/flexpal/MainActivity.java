package com.example.flexpal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    BottomNavigationView bottomNavigationView;
    boolean workoutDoneToday = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView wk1bg, wk2bg, wk3bg, wk4bg, wk5bg, wk6bg, wk7bg;
        TextView wk1, wk2, wk3, wk4, wk5, wk6, wk7;

        //weekly calendar
        Calendar cal = Calendar.getInstance();

// Check if today is not Sunday (first day of the week), then find the next Sunday
        SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);
        Set<String> workoutDates = preferences.getStringSet("doneworkout", new HashSet<>());

// Log the contents of workoutDates
        Log.d("ReportActivity", "Workout Dates: " + workoutDates.toString());

        Set<String> processedDates = new HashSet<>();

        // Set the calendar to the first day of the current week (Monday)
        // Use a LocalDate for comparison
        LocalDate currentDate = LocalDate.now();

// Set the calendar to the first day of the current week (Monday)
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = cal.getTime();

// Update each TextView and ImageView with the day number and background tint
        for (int i = 1; i <= 7; i++) {
            String textViewId = "wk" + i;
            int resID = getResources().getIdentifier(textViewId, "id", getPackageName());

            TextView textView = findViewById(resID);

            // Format the date for the current day
            LocalDate formattedDate = LocalDate.ofInstant(monday.toInstant(), ZoneId.systemDefault());
            textView.setText(String.valueOf(formattedDate.getDayOfMonth()));

            // Check if the current date is in workoutDates and hasn't been processed before
            if (workoutDates.contains(formattedDate.toString()) && !processedDates.contains(formattedDate)) {
                // If the current date is in workoutDates and hasn't been processed, change the text color and background tint
                textView.setTextColor(Color.WHITE); // Change to your desired text color

                // Also, change the background tint of the corresponding ImageView
                String imageViewId = textViewId + "bg";
                int resImageID = getResources().getIdentifier(imageViewId, "id", getPackageName());
                ImageView imageView = findViewById(resImageID);
                imageView.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK)); // Change to your desired tint color

                // Add the current date to the processed set
                processedDates.add(formattedDate.toString());
            }

            // Move to the next day (Monday to Sunday)
            cal.add(Calendar.DAY_OF_WEEK, 1);
            monday = cal.getTime();
        }


        //buttons hori-view

        Button disN = (Button) findViewById(R.id.HbtndiscoverN);
        disN.setOnClickListener(v -> openDiscoverWorkout());

        Button checkr = (Button) findViewById(R.id.btncheckreports);
        checkr.setOnClickListener(this);

        Button startw = (Button) findViewById(R.id.startworkout);
        startw.setOnClickListener(this);

        CardView toabsegin = (CardView) findViewById(R.id.bgabs);
        toabsegin.setOnClickListener(this);

        CardView tochestegin = (CardView) findViewById(R.id.bgchest);
        tochestegin.setOnClickListener(this);

        CardView toarmsbegin = (CardView) findViewById(R.id.bgarms);
        toarmsbegin.setOnClickListener(this);

        CardView tolegsbegin = (CardView) findViewById(R.id.bglegs);
        tolegsbegin.setOnClickListener(this);

        CardView toinabs = (CardView) findViewById(R.id.intrabs);
        toinabs.setOnClickListener(this);

        CardView tochesinter = (CardView) findViewById(R.id.intrchest);
        tochesinter.setOnClickListener(this);

        CardView toarmsinter = (CardView) findViewById(R.id.intrarms);
        toarmsinter.setOnClickListener(this);

        CardView tolegsin = (CardView) findViewById(R.id.intrlegs);
        tolegsin.setOnClickListener(this);

        CardView toabsadv = (CardView) findViewById(R.id.advabs);
        toabsadv.setOnClickListener(this);

        CardView tochestadv = (CardView) findViewById(R.id.advchest);
        tochestadv.setOnClickListener(this);

        CardView tocarmstadv = (CardView) findViewById(R.id.advarms);
        tocarmstadv.setOnClickListener(this);

        CardView tolegstadv = (CardView) findViewById(R.id.advlegs);
        tolegstadv.setOnClickListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.discover:
                        startActivity(new Intent(getApplicationContext(), DiscoverWorkoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.reports:
                        startActivity(new Intent(getApplicationContext(), ReportsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Disable the back button
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openSchedule(){
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivity(intent);
    }
    public void openDiscoverWorkout(){
        Intent intent = new Intent(this, DiscoverWorkoutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startworkout:
                startActivity(new Intent(this, Absbeginner.class));
                break;
            case R.id.btncheckreports:
                startActivity(new Intent(this, ReportsActivity.class));
                break;
            case R.id.bgabs:
                startActivity(new Intent(this, Absbeginner.class));
                break;
            case R.id.bgchest:
                startActivity(new Intent(this, Chestbeginner.class));
                break;
            case R.id.bgarms:
                startActivity(new Intent(this, Armsbeginner.class));
                break;
            case R.id.bglegs:
                startActivity(new Intent(this, Legsbeginner.class));
                break;
                //intermediate
            case R.id.intrabs:
                startActivity(new Intent(this, Absintermediate.class));
                break;
            case R.id.intrchest:
                startActivity(new Intent(this, Chestintermediate.class));
                break;
            case R.id.intrarms:
                startActivity(new Intent(this, ArmsIntermediate.class));
                break;
            case R.id.intrlegs:
                startActivity(new Intent(this, Legsintermediate.class));
                break;
                //advance
            case R.id.advabs:
                startActivity(new Intent(this, Absadvance.class));
                break;
            case R.id.advchest:
                startActivity(new Intent(this, Chestadvance.class));
                break;
            case R.id.advarms:
                startActivity(new Intent(this, Armsadvance.class));
                break;
            case R.id.advlegs:
                startActivity(new Intent(this, Legsadvance.class));
                break;
        }
    }

}
