package com.example.flexpal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.Random;

public class ReportsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private EditText weightEditText;
    private EditText heightEditText;
    private Spinner weightSpinner;
    private Spinner heightSpinner;
    private TextView bmiResultTextView, bmiResultTxt, fillworkout, filltime, fillcal;

    private CardView cd15,cd16,cd18,cd25, cd30, cd35, cd40;

    private static final double KG_TO_LB_CONVERSION = 2.20462;
    private static final double CM_TO_M_CONVERSION = 0.01;
    private static final double INCH_TO_M_CONVERSION = 0.0254;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        //tips chooser
        TextView randtips = findViewById(R.id.Rtipstxt);

        // Array of possible texts
        String[] possibleTexts = {
                "Drink water before, during, and after your workout to stay properly hydrated",
                "Consistency is the key to unlocking your fitness potential.",
                "Challenge yourself; that's where growth begins.",
                "Fuel your body with positivity, nourishing food, and hard work.",
                "Believe in yourself; your body is capable of amazing things.",
                "Celebrate small victories on the way to your ultimate goal.",
                "Your only competition is the person you were yesterday.",
                "Quality over quantity – make every workout count.",
                "Listen to your body; it knows what it needs.",
        };

        // Get a random index
        int randomIndex = new Random().nextInt(possibleTexts.length);

        // Set the random text in the TextView
        randtips.setText(possibleTexts[randomIndex]);

        // Retrieving the stored value
        SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);
        int retrievedbworkout = preferences.getInt("addworkout", 0);
        fillworkout = findViewById(R.id.fillworkouts);
        fillworkout.setText(String.valueOf(retrievedbworkout));

        long retrievedbtime = preferences.getLong("totalTime", 0);
        double minutes = retrievedbtime / 60.0; // Convert seconds to minutes
        String formattedTime = String.format("%.1f", minutes);
        filltime = findViewById(R.id.filltimes);
        filltime.setText(formattedTime);

        float retrievedbcal = preferences.getFloat("totalcal", 0);
        fillcal = findViewById(R.id.fillcals);
        fillcal.setText(String.format(Locale.getDefault(), "%.2f", retrievedbcal));

        Log.d("ReportActivity", "addworkout: " + retrievedbworkout + ", totalTime: " + retrievedbtime);

        //progressbar
        ProgressBar pbarWorkouts = findViewById(R.id.pbarworkouts);
        ProgressBar pbbarCalories = findViewById(R.id.pbbarcalories);
        ProgressBar pbMinutes = findViewById(R.id.pbminutes);

        int progressCal = Math.round(retrievedbcal);
        int progressMinutes = (int) Math.round(minutes);

        int maxProgress1 = 10;

        //workout maxprogress
        if (retrievedbworkout >= 10 && retrievedbworkout < 50) {
            maxProgress1 = 50;
        }
        else if (retrievedbworkout >= 50 && retrievedbworkout < 100) {
            maxProgress1 = 100;
        }
        else if (retrievedbworkout >= 100 && retrievedbworkout < 150) {
            maxProgress1 = 150;
        }
        else if (retrievedbworkout >= 150 && retrievedbworkout < 200) {
            maxProgress1 = 200;
        }
        else if (retrievedbworkout >= 200 && retrievedbworkout < 300) {
            maxProgress1 = 300;
        }

        pbarWorkouts.setMax(maxProgress1);
        pbarWorkouts.setProgress(retrievedbworkout);
        TextView progressTextView = findViewById(R.id.pbwkprogress);
        progressTextView.setText(retrievedbworkout + "/" + maxProgress1);

        int percentage = (int) (((float) retrievedbworkout / maxProgress1) * 100);
        TextView percentageTextView = findViewById(R.id.pbwkpercntage);
        percentageTextView.setText(percentage + "%");

        int maxProgress2 = 10;

        //calories maxprogress
        if (progressCal >= 10 && progressCal < 50) {
            maxProgress2 = 50;
        }
        else if (progressCal >= 50 && progressCal < 100) {
            maxProgress2 = 100;
        }
        else if (progressCal >= 100 && progressCal < 150) {
            maxProgress2 = 150;
        }
        else if (progressCal >= 150 && progressCal < 200) {
            maxProgress2 = 200;
        }
        else if (progressCal >= 200 && progressCal < 300) {
            maxProgress2 = 300;
        }

        pbbarCalories.setMax(maxProgress2);
        pbbarCalories.setProgress(progressCal);
        TextView progressTextView2 = findViewById(R.id.pbcalprogress);
        progressTextView2.setText(progressCal + "/" + maxProgress2);

        int percentage2 = (int) (((float) progressCal / maxProgress2) * 100);
        TextView percentageTextView2 = findViewById(R.id.pbcalpercntage);
        percentageTextView2.setText(percentage2 + "%");

        int maxProgress3 = 10;

        //minutes maxprogress
        if (progressMinutes >= 10 && progressMinutes < 50) {
            maxProgress3 = 50;
        }
        else if (progressMinutes >= 50 && progressMinutes < 100) {
            maxProgress3 = 100;
        }
        else if (progressMinutes >= 100 && progressMinutes < 150) {
            maxProgress3 = 150;
        }
        else if (progressMinutes >= 150 && progressMinutes < 200) {
            maxProgress3 = 200;
        }
        else if (progressMinutes >= 200 && progressMinutes < 300) {
            maxProgress3 = 300;
        }

        pbMinutes.setMax(maxProgress3);
        pbMinutes.setProgress(progressMinutes);
        TextView progressTextView3 = findViewById(R.id.pbmprogress);
        progressTextView3.setText(progressMinutes + "/" + maxProgress3);

        int percentage3 = (int) (((float) progressMinutes / maxProgress3) * 100);
        TextView percentageTextView3 = findViewById(R.id.pbmpercntage);
        percentageTextView3.setText(percentage3 + "%");

        weightEditText = findViewById(R.id.Rbmiweight);
        heightEditText = findViewById(R.id.Rbmiheight);
        weightSpinner = findViewById(R.id.Rbmifwg);
        heightSpinner = findViewById(R.id.Rbmifhg);
        bmiResultTextView = findViewById(R.id.Rbmiresult);
        bmiResultTxt = findViewById(R.id.Rbmiresulttxt);

        //cardviews
        cd15 = findViewById(R.id.Rbmi15);
        cd16 = findViewById(R.id.Rbmi16);
        cd18 = findViewById(R.id.Rbmi18);
        cd25 = findViewById(R.id.Rbmi25);
        cd30 = findViewById(R.id.Rbmi30);
        cd35 = findViewById(R.id.Rbmi35);
        cd40 = findViewById(R.id.Rbmi40);

        // Set up the spinner adapters
        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(
                this, R.array.weight_units, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> heightAdapter = ArrayAdapter.createFromResource(
                this, R.array.height_units, android.R.layout.simple_spinner_item);

        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weightSpinner.setAdapter(weightAdapter);
        heightSpinner.setAdapter(heightAdapter);


        retrievePreferences();

        //textwatchers
        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateBMI();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateBMI();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Add item selected listeners to the spinners
        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                calculateBMI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                calculateBMI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //bottom nav code
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.reports);

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
        private void calculateBMI() {
            // Retrieve weight and height values from EditText fields
            String weightStr = weightEditText.getText().toString();
            String heightStr = heightEditText.getText().toString();

            if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
                // Convert weight to kilograms
                double weight = 0;
                if (weightSpinner.getSelectedItem().equals("lb")) {
                    weight = Double.parseDouble(weightStr) / KG_TO_LB_CONVERSION; // Convert pounds to kg
                }
                else if (weightSpinner.getSelectedItem().equals("kg")){
                    weight = Double.parseDouble(weightStr); // Weight is already in kg
                }

                // Convert height to centimeters (change the conversion factor)
                double height = 0;
                if (heightSpinner.getSelectedItem().equals("in")) {
                    height = Double.parseDouble(heightStr) * INCH_TO_M_CONVERSION; // Convert feet to cm
                }
                else if (heightSpinner.getSelectedItem().equals("cm")){
                    height = Double.parseDouble(heightStr) * CM_TO_M_CONVERSION; // Height is already in cm
                }
                // Calculate BMI
                double bmi = weight / (height * height);

                // Display BMI result
                bmiResultTextView.setText(String.format("BMI: %.2f", bmi));

                // Change the background tint of CardViews based on BMI result
                savePreferences(weightStr, heightStr, weightSpinner.getSelectedItemPosition(), heightSpinner.getSelectedItemPosition());

                changeCardViewBackground(bmi);
            }
            else {
                // Clear BMI result if either weight or height is empty
                bmiResultTextView.setText("00.00");
                bmiResultTxt.setText("BMI Category");
                changeCardViewBackground(0.0);
            }
        }

    private void savePreferences(String weight, String height, int weightSpinnerPos, int heightSpinnerPos) {
        SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("weight", weight);
        editor.putString("height", height);
        editor.putInt("weightSpinnerPos", weightSpinnerPos);
        editor.putInt("heightSpinnerPos", heightSpinnerPos);

        Log.d("saved INFO", "weight: " + weight + ", height: " + height);

        editor.apply();
    }

    private void retrievePreferences() {
        SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);

        // Retrieve weight, height, and spinner positions
        String savedWeight = preferences.getString("weight", "");
        String savedHeight = preferences.getString("height", "");
        int savedWeightSpinnerPos = preferences.getInt("weightSpinnerPos", 0);
        int savedHeightSpinnerPos = preferences.getInt("heightSpinnerPos", 0);

        // Check if values are not default before setting them
        if (!savedWeight.isEmpty()) {
            weightEditText.setText(savedWeight);
        }

        if (!savedHeight.isEmpty()) {
            heightEditText.setText(savedHeight);
        }

        Log.d("Retrieved INFO", "weight: " + savedWeight + ", height: " + savedHeight);

        // Set the retrieved spinner positions
        weightSpinner.setSelection(savedWeightSpinnerPos);
        heightSpinner.setSelection(savedHeightSpinnerPos);
    }



    private void changeCardViewBackground(double bmi) {
            CardView[] cardViews = {cd15, cd16, cd18, cd25, cd30, cd35, cd40};
            CardView currentView = null;

            // Set background tint based on BMI result
            if (bmi >= 15 && bmi < 16) {
                bmiResultTxt.setText("Underweight");
                currentView = cd15;
            }
            else if (bmi >= 16 && bmi < 18.5) {
                bmiResultTxt.setText("Underweight");
                currentView = cd16;
            }
            else if (bmi >= 18.5 && bmi < 25) {
                bmiResultTxt.setText("Normal");
                currentView = cd18;
            }
            else if (bmi >= 25 && bmi < 30) {
                bmiResultTxt.setText("Overweight");
                currentView = cd25;
            }
            else if (bmi >= 30 && bmi < 35) {
                bmiResultTxt.setText("Obese Class 1");
                currentView = cd30;
            }
            else if (bmi >= 35 && bmi < 40) {
                bmiResultTxt.setText("Obese Class 2");
                currentView = cd35;
            }
            else if (bmi >= 40) {
                bmiResultTxt.setText("Obese Class 3");
                currentView = cd40;
            }
            else if (bmi == 00.00){
                bmiResultTxt.setText("BMI Category");
            }

            // Set the background using the drawable resources
            for (CardView cardView : cardViews) {
                int drawableRes = (cardView.equals(currentView)) ? R.drawable.bmichart_selected : R.drawable.bmichart_unselected;

                // Check if there is incomplete input or no output
                if (bmiResultTxt.getText().toString().equals("BMI Category")) {
                    drawableRes = R.drawable.bmichart_unselected;
                }

                cardView.setBackgroundResource(drawableRes);
            }
        }

}