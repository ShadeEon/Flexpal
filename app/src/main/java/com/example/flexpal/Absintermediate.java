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

public class Absintermediate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absintermediate);

        int[] cIcon = {R.drawable.jumping_jacks,R.drawable.heel_touch, R.drawable.crossover_crunch, R.drawable.mountain_climber,
                R.drawable.butt_bridge, R.drawable.v_up, R.drawable.heel_touch,
                R.drawable.abdominal_crunches, R.drawable.plank,  R.drawable.crossover_crunch,
                R.drawable.leg_raises,  R.drawable.bicycle_crunches,
                R.drawable.side_plank,  R.drawable.cobra_stretch};

        for (int i = 0; i < cIcon.length && i < 14; i++) {
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
        TextView c1 = findViewById(R.id.c1);
        TextView c2 = findViewById(R.id.c2);
        TextView c3 = findViewById(R.id.c3);
        TextView c4 = findViewById(R.id.c4);
        TextView c5 = findViewById(R.id.c5);
        TextView c6 = findViewById(R.id.c6);
        TextView c7 = findViewById(R.id.c7);
        TextView c8 = findViewById(R.id.c8);
        TextView c9 = findViewById(R.id.c9);
        TextView c10 = findViewById(R.id.c10);
        TextView c11 = findViewById(R.id.c11);
        TextView c12 = findViewById(R.id.c12);
        TextView c13 = findViewById(R.id.c13);
        TextView c14 = findViewById(R.id.c14);


        TextView title = findViewById(R.id.extitle);

        TextView c1time = findViewById(R.id.c1time);
        TextView c2time = findViewById(R.id.c2time);
        TextView c3time = findViewById(R.id.c3time);
        TextView c4time = findViewById(R.id.c4time);
        TextView c5time = findViewById(R.id.c5time);
        TextView c6time = findViewById(R.id.c6time);
        TextView c7time = findViewById(R.id.c7time);
        TextView c8time = findViewById(R.id.c8time);
        TextView c9time = findViewById(R.id.c9time);
        TextView c10time = findViewById(R.id.c10time);
        TextView c11time = findViewById(R.id.c11time);
        TextView c12time = findViewById(R.id.c12time);
        TextView c13time = findViewById(R.id.c13time);
        TextView c14time = findViewById(R.id.c14time);

        TextView id = findViewById(R.id.wid);

        String cTitle = title.getText().toString();

        String cid = id.getText().toString();

        String[] cValues = {c1.getText().toString(), c2.getText().toString(), c3.getText().toString(), c4.getText().toString(),
                c5.getText().toString(), c6.getText().toString(), c7.getText().toString(), c8.getText().toString(),
                c9.getText().toString(), c10.getText().toString(), c11.getText().toString(), c12.getText().toString(),
                c13.getText().toString(), c14.getText().toString()};

        String[] cTimeValues = {c1time.getText().toString(), c2time.getText().toString(), c3time.getText().toString(),
                c4time.getText().toString(), c5time.getText().toString(), c6time.getText().toString(),
                c7time.getText().toString(), c8time.getText().toString(), c9time.getText().toString(),
                c10time.getText().toString(), c11time.getText().toString(), c12time.getText().toString(),
                c13time.getText().toString(), c14time.getText().toString()};

        int[] cIcon = {R.drawable.jumping_jacks,R.drawable.heel_touch, R.drawable.crossover_crunch, R.drawable.mountain_climber,
                R.drawable.butt_bridge, R.drawable.v_up, R.drawable.heel_touch,
                R.drawable.abdominal_crunches, R.drawable.plank,  R.drawable.crossover_crunch,
                R.drawable.leg_raises,  R.drawable.bicycle_crunches,
                R.drawable.side_plank,  R.drawable.cobra_stretch};

        Intent intent = new Intent(Absintermediate.this, WorkoutStartActivity.class);

        intent.putExtra("cTitle", cTitle);
        intent.putExtra("cValues", cValues);
        intent.putExtra("cIcon", cIcon);
        intent.putExtra("cid", cid);
        intent.putExtra("cTimeValues", cTimeValues);

        startActivity(intent);
    }
}