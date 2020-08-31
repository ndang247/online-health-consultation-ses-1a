package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private EditText staffNumberEditTxt, specialtyEditTxt, firstLegalNameEditTxt,
            lastLegalNameEditTxt, passwordEditTxt, confirmPasswordEditTxt;

    private Button registerBtn;
    private TextView alreadyRegisterTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        initViews();

        alreadyRegisterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistrationActivity.this, DoctorLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        staffNumberEditTxt = findViewById(R.id.staffNumberEditTxt);
        specialtyEditTxt = findViewById(R.id.specialtyEditTxt);
        firstLegalNameEditTxt = findViewById(R.id.firstLegalNameEditTxt);
        lastLegalNameEditTxt = findViewById(R.id.lastLegalNameEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswordEditTxt);
        registerBtn = findViewById(R.id.registerBtn);
        alreadyRegisterTxt = findViewById(R.id.alreadyRegisterTxt);
    }

    public String getStaffNumberEditTxt() {
        return staffNumberEditTxt.getText().toString();
    }

    public String getSpecialtyEditTxt() {
        return specialtyEditTxt.getText().toString();
    }

    public String getFirstLegalNameEditTxt() {
        return firstLegalNameEditTxt.getText().toString();
    }

    public String getLastLegalNameEditTxt() {
        return lastLegalNameEditTxt.getText().toString();
    }

    public String getPasswordEditTxt() {
        return passwordEditTxt.getText().toString();
    }

    public String getConfirmPasswordEditTxt() {
        return confirmPasswordEditTxt.getText().toString();
    }

    // do doctor register function here
}