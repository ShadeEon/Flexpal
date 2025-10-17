package com.example.flexpal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WorkoutStartActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer startsfx, restsfx;
    private static final long REST_COUNTDOWN_SECONDS = 15;
    private String[] cValues;
    private int[] cIcon;
    private String[] cTimeValues;
    private String cTitle, cid;
    private int currentIndex = 0;
    private String countdownText; // Move countdownText to be a member variable
    private Button nextButton, pauseButton;
    private TextView exerciseTitleTextView , exerciseid;
    private TextView exerciseNameTextView;
    private TextView exerciseTimeTextView, nextexercisein, countdw, ctimemaker, cestart, goarrow, standby;
    private ImageView exerciseicon;
    private TextView cNextTextView;
    private CountDownTimer countDownTimer;
    private boolean isRestTime = false;
    private int cRestTimeInSeconds = 20;
    private static final int INITIAL_COUNTDOWN_SECONDS = 10;
    private boolean INITIAL_COUNTDOWN_DONE = false;
    private boolean isPaused = false;
    private long remainingTimeSeconds = 0;
    TextToSpeech textToSpeech;
    private int forwk = 0;
    private float forkc = 0;
    private long formin = 0;

    private boolean isSoundAndSpeechPlayedEX = false;
    private boolean isSoundAndSpeechPlayedRS = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);
        //sfx
        startsfx = MediaPlayer.create(this, R.raw.startsound);
        restsfx = MediaPlayer.create(this, R.raw.restsound);

        goarrow = findViewById(R.id.goback);
        goarrow.setVisibility(View.GONE);
        standby = findViewById(R.id.startingstb);
        goarrow.setOnClickListener(this);

        // Retrieve values from the Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cTitle = extras.getString("cTitle");
            cid = extras.getString("cid");
            cValues = extras.getStringArray("cValues");
            cTimeValues = extras.getStringArray("cTimeValues");
            cIcon = getIntent().getIntArrayExtra("cIcon");
        }

        //textospeech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                    startInitialCountdown();
                }
            }
        });

        nextButton = findViewById(R.id.wsnextbtn);
        nextButton.setVisibility(View.GONE);
        pauseButton = findViewById(R.id.wspausebtn);
        pauseButton.setVisibility(View.GONE);
        exerciseTitleTextView = findViewById(R.id.ctitle);
        exerciseid = findViewById(R.id.wid);
        exerciseNameTextView = findViewById(R.id.ce);
        exerciseNameTextView.setVisibility(View.GONE);
        cestart = findViewById(R.id.cestart);
        cestart.setVisibility(View.VISIBLE);
        exerciseTimeTextView = findViewById(R.id.ctime);
        exerciseTimeTextView.setVisibility(View.GONE);
        ctimemaker = findViewById(R.id.ctimemaker);
        ctimemaker.setVisibility(View.GONE);
        nextexercisein = findViewById(R.id.cnext);
        nextexercisein.setVisibility(View.GONE);
        exerciseicon = findViewById(R.id.TopLLg);
        cNextTextView = findViewById(R.id.cenext);
        cNextTextView.setVisibility(View.GONE);
        countdw = findViewById(R.id.cticount);


        // Set up the initial

        // Set up the nextButton click listener
        nextButton.setOnClickListener(v -> {
            if (isPaused) {
                // Handle the case when nextButton is clicked while paused
                // You can choose to resume the countdown or perform any other action
                currentIndex++;
                isSoundAndSpeechPlayedEX = false;
                if (currentIndex < cValues.length) {
                    // If there are more values to display
                    resumeCountdown();
                    isRestTime = false;
                    displayValues();
                }
                else {
                    // All exercises have been shown, finish the activity
                    cancelCountdown();
                    startsfx.stop();
                    restsfx.stop();
                    stopTextToSpeech();
                    Intent intent = new Intent(WorkoutStartActivity.this, WorkoutScorePreview.class);

                    intent.putExtra("forexid", cTitle);
                    intent.putExtra("forwk", forwk);
                    intent.putExtra("forkc", forkc);
                    intent.putExtra("formin", formin);

                    startActivity(intent);
                    finish();
                }
            }
            else if (currentIndex == cValues.length - 1){
                cancelCountdown();
                startsfx.stop();
                restsfx.stop();
                stopTextToSpeech();
                Intent intent = new Intent(WorkoutStartActivity.this, WorkoutScorePreview.class);

                intent.putExtra("forexid", cTitle);
                intent.putExtra("forwk", forwk);
                intent.putExtra("forkc", forkc);
                intent.putExtra("formin", formin);

                startActivity(intent);
                finish();
            }
            else {
                // Move to the next pair of values
                currentIndex++;
                isSoundAndSpeechPlayedEX = false;
                if (currentIndex < cValues.length) {
                    // If there are more values to display
                    isRestTime = false;
                    displayValues();
                } else {
                    // All exercises have been shown, finish the activity
                    cancelCountdown();
                    startsfx.stop();
                    restsfx.stop();
                    stopTextToSpeech();
                    Intent intent = new Intent(WorkoutStartActivity.this, WorkoutScorePreview.class);

                    intent.putExtra("forexid", cTitle);
                    intent.putExtra("forwk", forwk);
                    intent.putExtra("forkc", forkc);
                    intent.putExtra("formin", formin);

                    startActivity(intent);
                    finish();
                }
            }
        });

// Set up the pauseButton click listener
        pauseButton.setOnClickListener(v -> {
            if (isRestTime) {
                if (isPaused) {
                    // If paused, resume the countdown
                    resumeCountdown();
                }
                else {
                    // If not paused, pause the countdown
                    pauseCountdown();
                }
            }
            else {
                if (isPaused) {
                    // If paused, resume the countdown
                    resumeCountdown();
                }
                else {
                    // If not paused, pause the countdown
                    pauseCountdown();
                }
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

    private void startInitialCountdown() {
        // Start the countdown timer
        restsfx.start();
        String message = "Get Ready!";
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        new CountDownTimer(INITIAL_COUNTDOWN_SECONDS * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update the countdown timer in a TextView
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                // Ensure seconds are not negative
                seconds = Math.max(0, seconds);

                countdownText = seconds + "s";
                countdw.setText(countdownText);
            }

            public void onFinish() {
                // Initial countdown finished, now initialize and display values
                goarrow.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                exerciseTimeTextView.setVisibility(View.VISIBLE);
                nextexercisein.setVisibility(View.VISIBLE);
                ctimemaker.setVisibility(View.VISIBLE);
                exerciseNameTextView.setVisibility(View.VISIBLE);
                cestart.setVisibility(View.GONE);
                countdw.setVisibility(View.GONE);
                standby.setVisibility(View.GONE);
                cNextTextView.setVisibility(View.VISIBLE);
                displayValues();
                String nextText = cValues[currentIndex];
                String times = cTimeValues[currentIndex];
                String[] timeParts = times.split(":");
                int seconds = Integer.parseInt(timeParts[1]);
                String message = "Ready to go, the next " + seconds + " seconds." + nextText;
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            }
        }.start();
    }


    // Method to display values
    private void displayValues() {
        // Update your UI elements with the values
        exerciseNameTextView.setText(cValues[currentIndex]);
        exerciseTimeTextView.setText(cTimeValues[currentIndex]);
        exerciseicon.setImageResource(cIcon[currentIndex]);
        exerciseTitleTextView.setText(cTitle);
        exerciseid.setText(cid);

        // Check if it's rest time
        if (isRestTime) {
            // Display the next cValue on cNextTextView during rest time
            if (currentIndex + 1 < cValues.length) {
                cNextTextView.setText(cValues[currentIndex + 1]);
                if (cIcon[currentIndex] == cIcon[currentIndex]) {
                    // Load and play the GIF using Glide
                    Glide.with(this)
                            .asGif()
                            .load(cIcon[currentIndex+1])
                            .into(exerciseicon);
                }
                else {
                    // Load static image using Glide
                    Glide.with(this)
                            .load(cIcon[currentIndex+1])
                            .into(exerciseicon);
                }
            }
            else {
                // If there are no more exercises, hide the cNextTextView or handle as needed
                cNextTextView.setText("");
            }
            updateRestCountdown(cRestTimeInSeconds);
        }
        else {
            // Display the next cValue on cNextTextView
            if (currentIndex + 1 < cValues.length) {
                cNextTextView.setText(cValues[currentIndex + 1]);
                if (cIcon[currentIndex] == cIcon[currentIndex]) {
                    // Load and play the GIF using Glide
                    Glide.with(this)
                            .asGif()
                            .load(cIcon[currentIndex])
                            .into(exerciseicon);
                }
                else {
                    // Load static image using Glide
                    Glide.with(this)
                            .load(cIcon[currentIndex])
                            .into(exerciseicon);
                }
            }
            else {
                // If there are no more exercises, hide the cNextTextView or handle as needed
                cNextTextView.setText("");
            }

            // Call updateCountdown with the time string
            updateCountdown(cTimeValues[currentIndex]);
        }
    }

    // Method to update countdown
    private void updateCountdown(String timeValue) {
        // Cancel the previous timer, if any
        cancelCountdown();
        long durationInSeconds;

        // Check if it's the initial countdown
        // If it's the initial countdown, use the initial countdown duration
        if (timeValue.isEmpty()) {
            Log.d("ReportActivity", "empty ba?" + timeValue);
            return;
        } else {
            // Otherwise, use the regular exercise duration
            durationInSeconds = parseTimeStringToSeconds(timeValue);
        }

        if (!isSoundAndSpeechPlayedEX) {
            startsfx.start();
            String nextText = exerciseNameTextView.getText().toString();
            String times = cTimeValues[currentIndex];
            String[] timeParts = times.split(":");
            int wseconds = Integer.parseInt(timeParts[1]);
            String message = "Start, " + wseconds + " seconds." + nextText;
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);

            // Set the flag to true to indicate that sound and speech have been played
            isSoundAndSpeechPlayedEX = true;
        }

        // Otherwise, use the regular exercise duration
        countDownTimer = new CountDownTimer(durationInSeconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (isPaused) {
                    // If paused, do nothing
                    return;
                }

                // Update the countdown timer in a TextView
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                // Ensure seconds are not negative
                seconds = Math.max(0, seconds);

                // Check if it's the initial countdown

                countdownText = String.format(Locale.getDefault(), "%02d", seconds);

                exerciseTimeTextView.setText(countdownText);
            }

            public void onFinish() {
                // Move to the rest countdown when the exercise countdown finishes
                isRestTime = true;
                    countDownTimer.cancel();
                    SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);
                    String savedWeight = preferences.getString("weight", "");
                    int currentValue = preferences.getInt("addworkout", 0);
                    long totalDuration = preferences.getLong("totalTime", 0);
                    float totalcal = preferences.getFloat("totalcal", 0);
                    float weight = Float.parseFloat(savedWeight);
                    float calburn = 0;

                    if (exerciseid.getText().equals("1")) {
                        calburn = (float) (3.5 * weight * 3.5 / 200);
                    }
                    else if (exerciseid.getText().equals("2")) {
                        calburn = (float) (8 * weight * 3.5 / 200);
                    }

                    int totalworkout = currentValue + 1;
                    totalDuration += durationInSeconds;
                    totalcal += calburn / 2;

                    forwk += 1;
                    forkc += calburn / 2;
                    formin += durationInSeconds;

                Log.d("ReportActivity", "forwk: " + forwk +
                        ", forkc: " + forkc + ", formin: " + formin);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("addworkout", totalworkout);
                    editor.putFloat("totalcal", (float) totalcal);
                    editor.putLong("totalTime", totalDuration);
                    editor.apply();
                    Log.d("ReportActivity", "addworkout: " + totalworkout +
                            ", totalTime: " + totalDuration + ", calburned: " + totalcal+ ", " +
                            "weight" + savedWeight + ", weight" + exerciseid.getText());
                    isSoundAndSpeechPlayedEX = false;
                    displayValues();

                if(currentIndex == cValues.length - 1){
                    countDownTimer.cancel();
                    SharedPreferences preferencess = getSharedPreferences("bestworkout", MODE_PRIVATE);
                    Set<String> workoutDates = preferencess.getStringSet("doneworkout", new HashSet<>());

                    String currentDate = LocalDate.now().toString();
                    workoutDates.add(currentDate);

                    SharedPreferences.Editor editors = preferencess.edit();
                    editors.putStringSet("doneworkout", workoutDates);
                    editors.apply();
                    Log.d("ReportActivity", "Workout Date: " + workoutDates.toString());

                    Intent intent = new Intent(WorkoutStartActivity.this, WorkoutScorePreview.class);

                    intent.putExtra("forexid", cTitle);
                    intent.putExtra("forwk", forwk);
                    intent.putExtra("forkc", forkc);
                    intent.putExtra("formin", formin);

                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }


    private void updateRestCountdown(long restDurationInSeconds) {
        // Cancel the previous timer, if any
        cancelCountdown();

        String nextText = cNextTextView.getText().toString();

        // Add a check to ensure currentIndex+1 is within bounds
        if (currentIndex + 1 < cTimeValues.length) {
            if (!isSoundAndSpeechPlayedRS) {
                restsfx.start();
                String times = cTimeValues[currentIndex + 1];
                String[] timeParts = times.split(":");
                int seconds = Integer.parseInt(timeParts[1]);
                String message = "Ready to go, the next " + seconds + " seconds." + nextText;
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);

                // Set the flag to true to indicate that sound and speech for rest have been played
                isSoundAndSpeechPlayedRS = true;
            }
            countDownTimer = new CountDownTimer(restDurationInSeconds * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    // Check if paused during rest time
                    if (isPaused) {
                        cancelCountdown();
                        return;
                    }

                    // Update the countdown timer in a TextView
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                    // Ensure seconds are not negative
                    seconds = Math.max(0, seconds);

                    if (currentIndex == cValues.length - 1) {
                        // All exercises have been shown, finish the activity
                        cNextTextView.setText("Well Done");
                        cancelCountdown();
                        new Handler().postDelayed(() -> finish(), 5000);
                    } else {
                        String countdownText = String.format(Locale.getDefault(), "%02d", seconds);
                        exerciseNameTextView.setText("Resting");
                        exerciseTimeTextView.setText(countdownText);
                        Log.d("resttime", "" + exerciseTimeTextView.getText());
                    }
                }

                public void onFinish() {
                    countDownTimer.cancel();
                    // Move to the next exercise after the rest countdown finishes
                    isRestTime = false;

                    if (!isPaused && currentIndex < cValues.length - 1) {
                        // If not paused and there are more exercises, display the next exercise
                        currentIndex++;
                        isSoundAndSpeechPlayedRS = false;
                        displayValues();
                    } else if (currentIndex == cValues.length - 1) {
                        // All exercises have been shown, finish the activity
                        cNextTextView.setText("Well Done");
                        cancelCountdown();
                        new Handler().postDelayed(() -> finish(), 5000);
                    }
                }
            }.start();
        } else {
            // Handle the case where currentIndex+1 is out of bounds
            Log.e("WorkoutStartActivity", "Index out of bounds: " + (currentIndex + 1));
            // You might want to set a default duration or handle it accordingly
        }
    }


    // Method to pause the countdown
    private void pauseCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            pauseButton.setText("RESUME");
            pauseButton.setBackgroundResource(R.drawable.btn_background_selected);
            isPaused = true;

            // Store the remaining time in seconds
            remainingTimeSeconds = parseTimeStringToSeconds(exerciseTimeTextView.getText().toString());
        }
    }

    // Method to resume the countdown
    private void resumeCountdown() {
        isPaused = false;
        pauseButton.setText("PAUSE");
        pauseButton.setBackgroundResource(R.drawable.btn_background);
        // Restart countdown with remaining time
        String remainingTimeString = exerciseTimeTextView.getText().toString();
        Log.d("WorkoutStartActivity", "TIMEVALUE" + exerciseTimeTextView.getText());
        if (!remainingTimeString.isEmpty()) {
            try {
                long remainingSeconds = Long.parseLong(remainingTimeString);
                Log.d("WorkoutStartActivity", "Resuming countdown with remaining seconds: " + remainingSeconds);
                updateCountdown(String.valueOf(remainingSeconds));
            } catch (NumberFormatException e) {
                // Handle the case where the string is not a valid number
                Log.e("WorkoutStartActivity", "Invalid remaining time string: " + remainingTimeString);
                // You might want to show a message to the user or take appropriate action
            }
        } else {
            // Handle the case where the string is empty
            Log.e("WorkoutStartActivity", "Invalid remaining time string: " + remainingTimeString);
            // You might want to show a message to the user or take appropriate action
        }
    }

    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private long parseTimeStringToSeconds(String timeValue) {
        try {
            if (timeValue.contains(":")) {
                String[] parts = timeValue.split(":");
                if (parts.length >= 2) {
                    int minutes = Integer.parseInt(parts[0]);
                    String secondsString = parts[1].trim();  // Trim to handle leading/trailing spaces
                    if (!secondsString.isEmpty()) {
                        int seconds = Integer.parseInt(secondsString);

                        // Calculate the duration in seconds
                        return minutes * 60 + seconds;
                    } else {
                        throw new IllegalArgumentException("Invalid time format: " + timeValue);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid time format: " + timeValue);
                }
            } else if (!timeValue.isEmpty()) {
                // If no colon (":") separator, assume it's seconds directly
                return Long.parseLong(timeValue);
            } else {
                throw new IllegalArgumentException("Invalid time format: " + timeValue);
            }
        } catch (NumberFormatException e) {
            // Handle the case where parsing minutes or seconds fails
            throw new IllegalArgumentException("Invalid time format: " + timeValue);
        }
    }

    private void stopTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                pauseCountdown();
                showQuitConfirmationDialog();
                break;
        }
    }

    private void showQuitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Yes, execute the quit code
                        cancelCountdown();
                        startsfx.stop();
                        restsfx.stop();
                        stopTextToSpeech();
                        Intent intent = new Intent(WorkoutStartActivity.this, WorkoutScorePreview.class);

                        intent.putExtra("forexid", cTitle);
                        intent.putExtra("forwk", forwk);
                        intent.putExtra("forkc", forkc);
                        intent.putExtra("formin", formin);

                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resumeCountdown();
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}