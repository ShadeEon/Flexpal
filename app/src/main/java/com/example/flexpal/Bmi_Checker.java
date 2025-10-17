package com.example.flexpal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Bmi_Checker extends AppCompatActivity {

    private EditText weightEditText;
    private EditText heightEditText;
    private Spinner weightSpinner;
    private Spinner heightSpinner;

    private TextView bmiResultTextView, bmiResultTxt;

    private CardView cd15, cd16, cd18, cd25, cd30, cd35, cd40;

    private static final double KG_TO_LB_CONVERSION = 2.20462;
    private static final double CM_TO_M_CONVERSION = 0.01;
    private static final double INCH_TO_M_CONVERSION = 0.0254;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_TIME_KEY = "isFirstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_checker);

        // Initialize EditText and Spinner objects
        weightEditText = findViewById(R.id.Rbmiweight);
        heightEditText = findViewById(R.id.Rbmiheight);
        weightSpinner = findViewById(R.id.Rbmifwg);
        heightSpinner = findViewById(R.id.Rbmifhg);
        bmiResultTextView = findViewById(R.id.Rbmiresult);
        bmiResultTxt = findViewById(R.id.Rbmiresulttxt);

        // Initialize CardViews
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

        // Set up the button
        Button startButton = findViewById(R.id.recordbmi);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmbmi();
            }
        });

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
        });;

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
    }

    // TextWatcher to monitor changes in weightEditText and heightEditText
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Check if both weight and height fields have values
            boolean isWeightNotEmpty = !weightEditText.getText().toString().isEmpty();
            boolean isHeightNotEmpty = !heightEditText.getText().toString().isEmpty();
            // Enable or disable the button based on the conditions
            findViewById(R.id.recordbmi).setEnabled(isWeightNotEmpty && isHeightNotEmpty);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    private void calculateBMI() {
        // Retrieve weight and height values from EditText fields
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            // Convert weight to kilograms
            double weight = 0;
            if (weightSpinner.getSelectedItem().equals("lb")) {
                weight = Double.parseDouble(weightStr) / KG_TO_LB_CONVERSION; // Convert pounds to kg
            } else if (weightSpinner.getSelectedItem().equals("kg")) {
                weight = Double.parseDouble(weightStr); // Weight is already in kg
            }

            // Convert height to centimeters (change the conversion factor)
            double height = 0;
            if (heightSpinner.getSelectedItem().equals("in")) {
                height = Double.parseDouble(heightStr) * INCH_TO_M_CONVERSION; // Convert feet to cm
            } else if (heightSpinner.getSelectedItem().equals("cm")) {
                height = Double.parseDouble(heightStr) * CM_TO_M_CONVERSION; // Height is already in cm
            }
            // Calculate BMI
            double bmi = weight / (height * height);

            // Display BMI result
            bmiResultTextView.setText(String.format("BMI: %.2f", bmi));

            // Change the background tint of CardViews based on BMI result
            changeCardViewBackground(bmi);
        } else {
            // Clear BMI result if either weight or height is empty
            bmiResultTextView.setText("00.00");
            bmiResultTxt.setText("BMI Category");
            changeCardViewBackground(0.0);
        }
    }

    private void changeCardViewBackground(double bmi) {
        CardView[] cardViews = {cd15, cd16, cd18, cd25, cd30, cd35, cd40};
        CardView currentView = null;

        // Set background tint based on BMI result
        if (bmi >= 15 && bmi < 16) {
            bmiResultTxt.setText("Underweight");
            currentView = cd15;
        } else if (bmi >= 16 && bmi < 18.5) {
            bmiResultTxt.setText("Underweight");
            currentView = cd16;
        } else if (bmi >= 18.5 && bmi < 25) {
            bmiResultTxt.setText("Normal");
            currentView = cd18;
        } else if (bmi >= 25 && bmi < 30) {
            bmiResultTxt.setText("Overweight");
            currentView = cd25;
        } else if (bmi >= 30 && bmi < 35) {
            bmiResultTxt.setText("Obese Class 1");
            currentView = cd30;
        } else if (bmi >= 35 && bmi < 40) {
            bmiResultTxt.setText("Obese Class 2");
            currentView = cd35;
        } else if (bmi >= 40) {
            bmiResultTxt.setText("Obese Class 3");
            currentView = cd40;
        } else if (bmi == 00.00) {
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

    private void confirmbmi() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            // Show an error dialog if any of the fields is empty
            showErrorDialog();
        }
        else {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(FIRST_TIME_KEY, false);
            editor.apply();

            boolean updatedValue = settings.getBoolean(FIRST_TIME_KEY, true);
            Log.d("SharedPreferences", "After change - Value of FIRST_TIME_KEY: " + updatedValue);

            savePreferences(weightStr, heightStr, weightSpinner.getSelectedItemPosition(), heightSpinner.getSelectedItemPosition());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Empty Fields")
                .setMessage("Please, Fill up all empty fields!")
                .setPositiveButton("I UNDERSTAND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void savePreferences(String weight, String height, int weightSpinnerPos, int heightSpinnerPos) {
        SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("weight", weight);
        editor.putString("height", height);
        editor.putInt("weightSpinnerPos", weightSpinnerPos);
        editor.putInt("heightSpinnerPos", heightSpinnerPos);

        editor.apply();
    }
}