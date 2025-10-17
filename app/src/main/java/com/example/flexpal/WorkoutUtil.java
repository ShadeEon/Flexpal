package com.example.flexpal;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

public class WorkoutUtil {
    public static void startWorkout(Activity activity, List<String> titles, List<String> names, List<String> times) {
        Intent intent = new Intent(activity, WorkoutStartActivity.class);

        for (int i = 0; i < titles.size(); i++) {
            intent.putExtra("title" + (i + 1), titles.get(i));
            intent.putExtra("name" + (i + 1), names.get(i));
            intent.putExtra("time" + (i + 1), times.get(i));
        }
        activity.startActivity(intent);
    }
}