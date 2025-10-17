package com.example.flexpal;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class fingerprintmm extends AppCompatActivity {

    Button usebio, skipbio;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_USE_BIO = "use_bio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprintmm);

        usebio = findViewById(R.id.startbio);
        skipbio = findViewById(R.id.skip);

        checkBioMetricSupported();

        usebio.setOnClickListener(v -> showConfirmationDialog());

        skipbio.setOnClickListener(v -> skipBiometricAuthentication());
    }

    private void checkBioMetricSupported() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Toast.makeText(this, "Biometric is supported", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "No biometric features available on this device.", Toast.LENGTH_SHORT).show();
                usebio.setEnabled(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Biometric features are currently unavailable.", Toast.LENGTH_SHORT).show();
                usebio.setEnabled(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "The user hasn't associated any biometric credentials with their account.", Toast.LENGTH_SHORT).show();
                usebio.setEnabled(false);
                break;
        }
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Use Fingerprint")
                .setMessage("Are you sure you want to use fingerprint authentication?")
                .setPositiveButton("Yes", (dialog, which) -> authenticateUser())
                .setNegativeButton("No", null)
                .show();
    }

    private void authenticateUser() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(fingerprintmm.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                saveUserPreference(true);
                startNewActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for MoneyMinder")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void skipBiometricAuthentication() {
        Toast.makeText(getApplicationContext(), "Skipping biometric authentication", Toast.LENGTH_SHORT).show();
        saveUserPreference(false);
        startNewActivity();
    }

    private void saveUserPreference(boolean useBio) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USE_BIO, useBio);
        editor.apply();
    }

    private void startNewActivity() {
        Intent intent = new Intent(fingerprintmm.this, Bmi_Checker.class); // Replace Bmi_Checker.class with your target activity
        startActivity(intent);
        finish();
    }
}