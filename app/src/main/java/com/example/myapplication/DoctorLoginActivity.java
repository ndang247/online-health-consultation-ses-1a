package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText staffIDEditTxt, emailEditTxt, passwordEditTxt;
    private Button loginAsDoctorBtn;
    private TextView createNewAccTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        initViews();

        createNewAccTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorLoginActivity.this, DoctorRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        staffIDEditTxt = findViewById(R.id.staffNumberEditTxt);
        emailEditTxt = findViewById(R.id.emailEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        loginAsDoctorBtn = findViewById(R.id.loginAsDoctorBtn);
        createNewAccTxt = findViewById(R.id.createNewAccTxt);
    }

    public String getStaffIDEditTxt() {
        return staffIDEditTxt.getText().toString();
    }

    public String getEmailEditTxt() {
        return emailEditTxt.getText().toString();
    }

    public String getPasswordEditTxt() {
        return passwordEditTxt.getText().toString();
    }

    // do doctor login function here
}