package com.example.flexpal;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

    public class AddWorkoutActivity extends AppCompatActivity {

    private EditText timeEditText, createlabel;
    private Calendar selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        createlabel = findViewById(R.id.createlabel);
        timeEditText = findViewById(R.id.editTextTime);

        // Set the initial time to 12:30 AM
        selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.HOUR_OF_DAY, 0);
        selectedTime.set(Calendar.MINUTE, 30);

        //save button
        Button add = (Button) findViewById(R.id.addwcreatedsched);
        add.setOnClickListener(v -> openAddyourworkoutActivity());

        // Format and display the initial time in the EditText
        updateEditText();

        // Set click listener to show TimePickerDialog
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    private void showTimePickerDialog() {
        int hour = selectedTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedTime.get(Calendar.MINUTE);

        // Create a TimePickerDialog with a listener for when the time is set
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DatePickerTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update selectedTime with the chosen time
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minute);

                        // Format and update the EditText with the selected time
                        updateEditText();
                    }
                }, hour, minute, false); // false means 12-hour format

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    private void updateEditText() {
        // Format the selected time in 12-hour format
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedTime = timeFormat.format(selectedTime.getTime());

        // Set the formatted time to the EditText
        timeEditText.setText(formattedTime);
    }

    public void openAddyourworkoutActivity(){
        Intent intent = new Intent(this, AddyouworkoutActivity.class);
        startActivity(intent);
    }
}
