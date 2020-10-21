package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private Button loginAsPatientBtn;
    private Button loginAsDoctorBtn;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_options);

        initViews();

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        // Set click listeners
        loginAsPatientBtn.setOnClickListener(this);
        loginAsDoctorBtn.setOnClickListener(this);
    }

    private void initViews() {
        loginAsPatientBtn = findViewById(R.id.loginAsPatientBtn);
        loginAsDoctorBtn = findViewById(R.id.loginAsDoctorBtn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginAsPatientBtn:
                loginAsPatient();
                break;
            case R.id.loginAsDoctorBtn:
                loginAsDoctor();
                break;
        }
    }

    private void loginAsPatient() {
        Intent intent = new Intent(SignInOptionsActivity.this, PatientLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginAsDoctor() {
        Intent intent = new Intent(SignInOptionsActivity.this, DoctorLoginActivity.class);
        startActivity(intent);
        finish();
    }
}