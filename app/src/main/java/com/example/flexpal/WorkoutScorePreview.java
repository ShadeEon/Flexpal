package com.example.flexpal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class WorkoutScorePreview extends AppCompatActivity implements View.OnClickListener{

    private TextView forwktxt, forkctxt, formintxt;
    TextToSpeech textToSpeech;
    MediaPlayer startsfx, restsfx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_score_preview);

        startsfx = MediaPlayer.create(this, R.raw.startsound);
        restsfx = MediaPlayer.create(this, R.raw.restsound);

        TextView randtips = findViewById(R.id.Rtipstxt);

        String[] postWorkoutTips = {
                "Rehydrate with water to replenish fluids lost during your workout.",
                "Stay committed to your fitness routine for long-term success.",
                "Embrace challenges; they are opportunities for improvement.",
                "Nourish your body with positive thoughts, wholesome food, and dedicated effort.",
                "Trust in your abilities; your body is capable of incredible feats.",
                "Acknowledge and celebrate every small achievement on your fitness journey.",
                "Your main competition is the person you were yesterday; strive for progress.",
                "Prioritize quality in your workouts over sheer quantity.",
                "Pay attention to your body's signals; it will guide your post-workout recovery.",
        };

        int randomIndex = new Random().nextInt(postWorkoutTips.length);
        randtips.setText(postWorkoutTips[randomIndex]);

        //textospeech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                    congrats();
                }
            }
        });

        forwktxt = findViewById(R.id.forwktxt);
        forkctxt = findViewById(R.id.forkctxt);
        formintxt = findViewById(R.id.formintxt);

        ImageView iforwk = findViewById(R.id.forwk);
        iforwk.setVisibility(View.GONE);
        ImageView iforkc = findViewById(R.id.forkc);
        iforkc.setVisibility(View.GONE);
        ImageView iformin = findViewById(R.id.formin);
        iformin.setVisibility(View.GONE);

        Button nextbtn = (Button) findViewById(R.id.nextReportbtn);
        nextbtn.setOnClickListener(this);

        ImageView goarrow = (ImageView) findViewById(R.id.goarrow);
        goarrow.setOnClickListener(this);

        // Retrieving the stored value
        SharedPreferences preferences = getSharedPreferences("bestworkout", MODE_PRIVATE);
        int retrievedbworkout = preferences.getInt("addworkout", 0);

        long retrievedbtime = preferences.getLong("totalTime", 0);
        double minutes = retrievedbtime / 60.0; // Convert seconds to minutes
        String formattedTime = String.format("%.1f", minutes);

        float retrievedbcal = preferences.getFloat("totalcal", 0);


        Log.d("ReportActivity", "addworkout: " + retrievedbworkout + ", totalTime: " + retrievedbtime + ", calories" + retrievedbcal);

        //progressbar
        ProgressBar pbarWorkouts = findViewById(R.id.Workoutbest);
        ProgressBar goalWorkouts = findViewById(R.id.Workoutgoal);
        ProgressBar pbbarCalories = findViewById(R.id.Kcalbest);
        ProgressBar goalCalories = findViewById(R.id.Kcalgoal);
        ProgressBar pbMinutes = findViewById(R.id.Minbest);
        ProgressBar goalMinutes = findViewById(R.id.Mingoal);

        int maxGoalProgress = 100;

        goalWorkouts.setMax(maxGoalProgress);
        goalWorkouts.setProgress(maxGoalProgress);

        goalCalories.setMax(maxGoalProgress);
        goalCalories.setProgress(maxGoalProgress);

        goalMinutes.setMax(maxGoalProgress);
        goalMinutes.setProgress(maxGoalProgress);

        // Retrieve values from the Intent
        String forexid = getIntent().getStringExtra("forexid");
        int forwk = getIntent().getIntExtra("forwk", 0);
        float forkc = getIntent().getFloatExtra("forkc", 0);
        long formin = getIntent().getLongExtra("formin", 0);

        Log.d("ReportActivity", "forwk: " + forwk +
                ", forkc: " + forkc + ", formin: " + formin);

        // Find TextViews in your layout
        TextView forexidTextView = findViewById(R.id.cetitle);
        TextView forwkTextView = findViewById(R.id.fillworkouts);
        TextView forkcTextView = findViewById(R.id.fillcals);
        TextView forminTextView = findViewById(R.id.filltimes);

        // Format values and set them in TextViews
        forexidTextView.setText(forexid);
        forwkTextView.setText(String.format(Locale.getDefault(), "%d", forwk));
        forkcTextView.setText(String.format(Locale.getDefault(), "%.2f", forkc));
        forminTextView.setText(String.format(Locale.getDefault(), "%d:%02d",
                formin / 60, formin % 60));

        int progressCal = Math.round(retrievedbcal);
        int progressMinutes = (int) Math.round(minutes);

        int maxProgress1 = 10;

        if (retrievedbworkout >= 10 && retrievedbworkout < 50) {
            maxProgress1 = 50;
        } else if (retrievedbworkout >= 50 && retrievedbworkout < 100) {
            maxProgress1 = 100;
        } else if (retrievedbworkout >= 100 && retrievedbworkout < 150) {
            maxProgress1 = 150;
        } else if (retrievedbworkout >= 150 && retrievedbworkout < 250) {
            maxProgress1 = 250;
        } else if (retrievedbworkout >= 250 && retrievedbworkout <= 300) {
            maxProgress1 = 300;
        }

        pbarWorkouts.setMax(maxProgress1);
        pbarWorkouts.setProgress(retrievedbworkout);

        int maxProgress2 = 10;

        if (progressCal >= 10 && progressCal < 50) {
            maxProgress2 = 100;
        } else if (progressCal >= 50 && progressCal < 100) {
            maxProgress2 = 250;
        } else if (progressCal >= 100 && progressCal < 250) {
            maxProgress2 = 450;
        } else if (progressCal >= 250 && progressCal < 450) {
            maxProgress2 = 600;
        } else if (progressCal >= 450 && progressCal <= 850) {
            maxProgress2 = 850;
        }

        pbbarCalories.setMax(maxProgress2);
        pbbarCalories.setProgress(progressCal);

        int maxProgress3 = 10;

        if (progressMinutes >= 10 && progressMinutes < 50) {
            maxProgress3 = 60;
        } else if (progressMinutes >= 50 && progressMinutes < 60) {
            maxProgress3 = 120;
        } else if (progressMinutes >= 60 && progressMinutes < 120) {
            maxProgress3 = 180;
        } else if (progressMinutes >= 120 && progressMinutes < 180) {
            maxProgress3 = 320;
        } else if (progressMinutes >= 180 && progressMinutes <= 320) {
            maxProgress3 = 320;
        }

        pbMinutes.setMax(maxProgress3);
        pbMinutes.setProgress(progressMinutes);

        forkctxt.setText(String.format(Locale.getDefault(), "%.2f", forkc));

        if (forwk > 0) {
            iforwk.setVisibility(View.VISIBLE);
            forwktxt.setText("+"+forwk);
        } else {
            iforwk.setVisibility(View.GONE);
            forwktxt.setText("+0");
        }

        if (forkc > 0) {
            iforkc.setVisibility(View.VISIBLE);
            forkctxt.setText("+"+ String.format(Locale.getDefault(), "%.2f", forkc));
        } else {
            iforkc.setVisibility(View.GONE);
            forkctxt.setText("+0");
        }

        if (formin > 0) {
            iformin.setVisibility(View.VISIBLE);
            formintxt.setText("+"+formin);
        } else {
            iformin.setVisibility(View.GONE);
            formintxt.setText("+0");
        }

    }

    private void congrats() {
        restsfx.start();
        String[] additionalPhrases = {
                "You crushed it!",
                "Keep up the awesome work!",
                "Impressive effort!",
                "You're unstoppable!",
                "Fantastic job!",
                "Your dedication is inspiring!",
        };

        int randomIndexs = new Random().nextInt(additionalPhrases.length);
        String message = "Great job! " + additionalPhrases[randomIndexs] + " Ready for the next challenge.";
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextReportbtn:
                startActivity(new Intent(this, ReportsActivity.class));
                break;
            case R.id.goarrow:
                startActivity(new Intent(this, ReportsActivity.class));
                break;
        }
    }

    private void stopTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }
}