package com.example.flexpal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText lusername, lpassword;
    private String username, password;
    private static final String PREF_NAME = "UserPreferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //login creds
        lusername = findViewById(R.id.usernameflg);
        lpassword = findViewById(R.id.passwordflg);
        username = password = "";

        if (isLoggedIn()) {
            startMainActivity();
            finish(); // Finish LoginActivity to prevent going back
        }
        else {
            setContentView(R.layout.activity_login);
        }

        //buttons
        TextView signup = (TextView) findViewById(R.id.signupbnt);
        signup.setOnClickListener(this);

        //show-hide password
        EditText passwlg = findViewById(R.id.passwordflg);
        ImageView hidepass = findViewById(R.id.hidepasslg);
        hidepass.setImageResource(R.drawable.hidepasslg_xaml);
        hidepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwlg.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //hide if visible
                    passwlg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //icon change
                    hidepass.setImageResource(R.drawable.hidepasslg_xaml);

                }
                else {
                    //Show if not visible
                    passwlg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //icon change
                    hidepass.setImageResource(R.drawable.showpasslg_xaml);
                }
            }
        });



    }
    //login code
    public void login(View view) {
        username = lusername.getText().toString().trim();
        password = lpassword.getText().toString().trim();

        if (!username.equals("") && !password.equals("")) {
            // Retrieve user data from the file
            if (checkCredentials(username, password)) {
                // Start new intent (MainActivity) if username and password match
                startMainActivity();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean("isLoggedIn", false);
    }

    private void saveLoggedInState() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        // Add other user-related data if needed
        editor.apply();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity if needed
    }

    private boolean checkCredentials(String enteredUsername, String enteredPassword) {
        // File storage logic to check credentials
        String filename = "user_data.txt";

        try (FileInputStream fis = openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bufferedReader = new BufferedReader(isr)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line to get stored username and password
                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String storedPassword = parts[1];

                // Check if entered credentials match stored credentials
                if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
                    return true; // Credentials match
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Credentials do not match or an error occurred
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signupbnt:
                startActivity(new Intent(this, SignupActivity.class));
                break;
        }
    }
}