package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class PatientLoginActivity extends AppCompatActivity {

    private static String TAG = "LoginActivity";
    private TextView registerTxt;
    private EditText emailEditTxt, passwordEditTxt;
    private Button loginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        // initialise views
        initViews();
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (PatientLoginActivity.this, PatientRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        emailEditTxt = findViewById(R.id.emailEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        loginBtn = findViewById(R.id.loginBtn);
        registerTxt = findViewById(R.id.registerTxt);
    }

    private String getEmail() {
        return emailEditTxt.getText().toString();
    }

    private String getPassword() {
        return passwordEditTxt.getText().toString();
    }

    private void signIn() {
        if (getEmail().isEmpty()) {
            emailEditTxt.setError("Email Is Required!");
            emailEditTxt.requestFocus();
        }
        if (getPassword().isEmpty()) {
            passwordEditTxt.setError("Password Is Required!");
            passwordEditTxt.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) { // Check is the email is real email
            emailEditTxt.setError("Please Enter A Valid Email");
            emailEditTxt.requestFocus();
        }
        mAuth.signInWithEmailAndPassword(getEmail(), getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(PatientLoginActivity.this, PatientActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(PatientLoginActivity.this, "Account Not Exists, Please Register First!", Toast.LENGTH_LONG ).show();
                }
            }
        });
    }
}