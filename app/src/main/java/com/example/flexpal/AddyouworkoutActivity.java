package com.example.flexpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AddyouworkoutActivity extends AppCompatActivity implements View.OnClickListener {

    String receivedInputText, receivedInputTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addyouworkout);

        CardView toabsegin = (CardView) findViewById(R.id.bgabs);
        toabsegin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.bgabs:
                intent = new Intent(this, Absbeginner.class);
                startActivity(intent);
                break;

            case R.id.bgchest:
                intent = new Intent(this, Absintermediate.class);
                startActivity(intent);
                break;
        }
    }
}