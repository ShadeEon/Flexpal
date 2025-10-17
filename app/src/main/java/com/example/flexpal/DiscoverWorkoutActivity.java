package com.example.flexpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DiscoverWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_workout);

        CardView towarm = (CardView) findViewById(R.id.dwWarmup);
        towarm.setOnClickListener(this);

        CardView tocooldown = (CardView) findViewById(R.id.dwcooldown);
        tocooldown.setOnClickListener(this);

        CardView tocardio = (CardView) findViewById(R.id.dwcardio);
        tocardio.setOnClickListener(this);

        CardView tostret = (CardView) findViewById(R.id.dwstretch);
        tostret.setOnClickListener(this);

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
        bottomNavigationView.setSelectedItemId(R.id.discover);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.discover:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dwWarmup:
                startActivity(new Intent(this, WarmupW.class));
                break;
            case R.id.dwcooldown:
                startActivity(new Intent(this, CooldownW.class));
                break;
            case R.id.dwcardio:
                startActivity(new Intent(this, CardioW.class));
                break;
            case R.id.dwstretch:
                startActivity(new Intent(this, Streching.class));
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