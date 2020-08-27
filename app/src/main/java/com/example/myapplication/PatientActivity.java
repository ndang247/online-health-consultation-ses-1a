package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PatientActivity extends AppCompatActivity {

    private Button logoutBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        // initialise views
        initViews();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
                Intent intent = new Intent(PatientActivity.this, PatientLoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initViews() {
        logoutBtn = findViewById(R.id.logoutBtn);
    }

    private void logOut() {
        mAuth.signOut();
    }
}