package com.example.flexpal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText semail, sname, ssurname, sbirthday, susername, scpassword, spassword;
    private RadioGroup sgender;
    private TextView tvstatus;
    private String email, name, surname, gender, birthday, username, cpassword, password;
    private Button btnregister;
    private static final String PREF_NAME = "UserPreferences";

    EditText datebd;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //signup creds
        semail = findViewById(R.id.emailsg);
        sname = findViewById(R.id.namesg);
        ssurname = findViewById(R.id.lnamesg);
        sgender = findViewById(R.id.gendersg);
        sbirthday = findViewById(R.id.birthdaysg);
        susername = findViewById(R.id.usernamesg);
        scpassword = findViewById(R.id.cpasssg);
        spassword = findViewById(R.id.passsg);
        btnregister = findViewById(R.id.cregbtn);
        email = name = surname = gender = birthday = username = password = "";
        tvstatus = findViewById(R.id.tvStatus);

        //login return
        TextView signin = (TextView) findViewById(R.id.signinbnt);
        signin.setOnClickListener((View.OnClickListener) this);

        //show-hide password
        EditText passwsg = findViewById(R.id.passsg);
        ImageView hidepasssg = findViewById(R.id.hidepasssg);
        hidepasssg.setImageResource(R.drawable.hidepasslg_xaml);
        hidepasssg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwsg.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //hide if visible
                    passwsg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //icon change
                    hidepasssg.setImageResource(R.drawable.hidepasslg_xaml);

                }
                else {
                    //Show if not visible
                    passwsg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //icon change
                    hidepasssg.setImageResource(R.drawable.showpasslg_xaml);
                }
            }
        });

        //confirmpassw
        EditText cpasswsg = findViewById(R.id.cpasssg);
        ImageView hidecpasssg = findViewById(R.id.hidecpasssg);
        hidecpasssg.setImageResource(R.drawable.hidepasslg_xaml);
        hidecpasssg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cpasswsg.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //hide if visible
                    cpasswsg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //icon change
                    hidecpasssg.setImageResource(R.drawable.hidepasslg_xaml);

                }
                else {
                    //Show if not visible
                    cpasswsg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //icon change
                    hidecpasssg.setImageResource(R.drawable.showpasslg_xaml);
                }
            }
        });

        //birthday
        datebd = findViewById(R.id.birthdaysg);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar() {
                String format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                datebd.setText(sdf.format(calendar.getTime()));
            }
        };

// Get the existing date from datebd
        String currentDate = datebd.getText().toString();
        Date existingDate;

        try {
            existingDate = new SimpleDateFormat("MM/dd/yy", Locale.US).parse(currentDate);
            calendar.setTime(existingDate);
        }
        catch (ParseException e) {

        }

        datebd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        SignupActivity.this,
                        R.style.DatePickerTheme,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signinbnt:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    public void register(View view){
        email = semail.getText().toString().trim();
        name = sname.getText().toString().trim();
        surname = ssurname.getText().toString().trim();
        int selectedRadioButtonId = sgender.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            gender = selectedRadioButton.getText().toString().trim();
        }
        birthday = datebd.getText().toString().trim();
        username = susername.getText().toString().trim();
        cpassword = scpassword.getText().toString().trim();
        password = spassword.getText().toString().trim();

        if (!password.equals(cpassword)){
            Toast.makeText(this, "Incorrect Password, try again!", Toast.LENGTH_SHORT).show();
        }
        else if (!email.equals("") && !name.equals("") && !surname.equals("") && !gender.equals("") && !birthday.equals("") && !username.equals("") && !password.equals("") && !cpassword.equals("")) {
            saveUserDataToFile();
            startLoginActivity();
        }
        else if (!email.equals("") || !name.equals("") || !surname.equals("") || !gender.equals("") || !birthday.equals("") || !username.equals("") || !password.equals("") || !cpassword.equals("")) {
            Toast.makeText(this, "Fill up all empty fields!", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveUserDataToFile() {
        // File storage logic
        String filename = "user_data.txt";

        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_APPEND)) {
            // Concatenate user information and write to the file
            String userData = username + "," + password + "\n";
            fos.write(userData.getBytes());
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving user data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLoginActivity() {
        // Your code to start the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity if needed
    }

}