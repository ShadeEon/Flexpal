package com.example.flexpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;

public class WarmupW extends AppCompatActivity {

    TextView title, id,
            c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17,
            c1time, c2time, c3time, c4time, c5time, c6time, c7time, c8time, c9time, c10time, c11time, c12time, c13time, c14time, c15time, c16time, c17time;
    ImageView c1con, c2con, c3con, c4con, c5con, c6con, c7con, c8con, c9con, c10con, c11con, c12con, c13con, c14con, c15con, c16con, c17con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warmup_w);

        int[] cIcon = {R.drawable.warmupbent, R.drawable.warmupcrisscross,
                R.drawable.warmupside,R.drawable.warmuphip,R.drawable.warmupcross,
                R.drawable.warmuprun,R.drawable.warmuprope,R.drawable.warmuplegl,
                R.drawable.warmuplegr,R.drawable.warmuplunge,R.drawable.warmupgm,
                R.drawable.warmupchest,R.drawable.warmuparmcw,R.drawable.warmuparmccw,
                R.drawable.warmupjumpjack};

        for (int i = 0; i < cIcon.length && i < 15; i++) {
            int resourceId = cIcon[i];
            String imageViewId = "c" + (i + 1) + "con"; // Assuming IDs are like c1con, c2con, ..., c17con
            int imageViewResId = getResources().getIdentifier(imageViewId, "id", getPackageName());

            ImageView imageView = findViewById(imageViewResId);
            if (imageView != null) {
                try {
                    Glide.with(this)
                            .asGif()
                            .load(resourceId)
                            .into(imageView);
                } catch (Exception e) {
                    try {
                        Glide.with(this)
                                .asBitmap()
                                .load(resourceId)
                                .transition(BitmapTransitionOptions.withCrossFade())
                                .into(imageView);
                    } catch (Exception ex) {
                        // Handle exception for both GIF and PNG cases
                        ex.printStackTrace();
                    }
                }
            }
        }

        // Start workout
        // Inside your button click listener in AbsBeginnerActivity
        Button startButton = findViewById(R.id.abstartbtn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the startWorkout method when the button is clicked
                startWorkout();
            }
        });
    }
        public void startWorkout(){
            c1 = findViewById(R.id.c1);
            c2 = findViewById(R.id.c2);
            c3 = findViewById(R.id.c3);
            c4 = findViewById(R.id.c4);
            c5 = findViewById(R.id.c5);
            c6 = findViewById(R.id.c6);
            c7 = findViewById(R.id.c7);
            c8 = findViewById(R.id.c8);
            c9 = findViewById(R.id.c9);
            c10 = findViewById(R.id.c10);
            c11 = findViewById(R.id.c11);
            c12 = findViewById(R.id.c12);
            c13 = findViewById(R.id.c13);
            c14 = findViewById(R.id.c14);
            c15 = findViewById(R.id.c15);
            c16 = findViewById(R.id.c16);
            c17 = findViewById(R.id.c17);

            c1con = findViewById(R.id.c1con);
            c2con = findViewById(R.id.c2con);
            c3con = findViewById(R.id.c3con);
            c4con = findViewById(R.id.c4con);
            c5con = findViewById(R.id.c5con);
            c6con = findViewById(R.id.c6con);
            c7con = findViewById(R.id.c7con);
            c8con = findViewById(R.id.c8con);
            c9con = findViewById(R.id.c9con);
            c10con = findViewById(R.id.c10con);
            c11con = findViewById(R.id.c11con);
            c12con = findViewById(R.id.c12con);
            c13con = findViewById(R.id.c13con);
            c14con = findViewById(R.id.c14con);
            c15con = findViewById(R.id.c15con);
            c16con = findViewById(R.id.c16con);
            c17con = findViewById(R.id.c17con);

            title = findViewById(R.id.extitle);

            c1time = findViewById(R.id.c1time);
            c2time = findViewById(R.id.c2time);
            c3time = findViewById(R.id.c3time);
            c4time = findViewById(R.id.c4time);
            c5time = findViewById(R.id.c5time);
            c6time = findViewById(R.id.c6time);
            c7time = findViewById(R.id.c7time);
            c8time = findViewById(R.id.c8time);
            c9time = findViewById(R.id.c9time);
            c10time = findViewById(R.id.c10time);
            c11time = findViewById(R.id.c11time);
            c12time = findViewById(R.id.c12time);
            c13time = findViewById(R.id.c13time);
            c14time = findViewById(R.id.c14time);
            c15time = findViewById(R.id.c15time);
            c16time = findViewById(R.id.c16time);
            c17time = findViewById(R.id.c17time);

            id = findViewById(R.id.wid);

            String cTitle = title.getText().toString();

            String cid = id.getText().toString();

        String[] cValues = {c1.getText().toString(), c2.getText().toString(), c3.getText().toString(), c4.getText().toString(),
                c5.getText().toString(), c6.getText().toString(), c7.getText().toString(), c8.getText().toString(),
                c9.getText().toString(), c10.getText().toString(), c11.getText().toString(), c12.getText().toString(),
                c13.getText().toString(), c14.getText().toString(), c15.getText().toString()};

        String[] cTimeValues = {c1time.getText().toString(), c2time.getText().toString(), c3time.getText().toString(),
                c4time.getText().toString(), c5time.getText().toString(), c6time.getText().toString(),
                c7time.getText().toString(), c8time.getText().toString(), c9time.getText().toString(),
                c10time.getText().toString(), c11time.getText().toString(), c12time.getText().toString(),
                c13time.getText().toString(), c14time.getText().toString(), c15time.getText().toString()};

        int[] cIcon = {R.drawable.warmupbent, R.drawable.warmupcrisscross,
                R.drawable.warmupside,R.drawable.warmuphip,R.drawable.warmupcross,
                R.drawable.warmuprun,R.drawable.warmuprope,R.drawable.warmuplegl,
                R.drawable.warmuplegr,R.drawable.warmuplunge,R.drawable.warmupgm,
                R.drawable.warmupchest,R.drawable.warmuparmcw,R.drawable.warmuparmccw,
                R.drawable.warmupjumpjack};

            Intent intent = new Intent(WarmupW.this, WorkoutStartActivity.class);

            intent.putExtra("cTitle", cTitle);
            intent.putExtra("cValues", cValues);
            intent.putExtra("cIcon", cIcon);
            intent.putExtra("cid", cid);
            intent.putExtra("cTimeValues", cTimeValues);

            startActivity(intent);
    }
}