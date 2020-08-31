package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText staffIDEditTxt, emailEditTxt, passwordEditTxt;
    private Button loginAsDoctorBtn;
    private TextView createNewAccTxt;
    private FirebaseAuth mAuth;

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

    public String getStaffID() {
        return staffIDEditTxt.getText().toString();
    }

    public String getEmail() {
        return emailEditTxt.getText().toString();
    }

    public String getPassword() {
        return passwordEditTxt.getText().toString();
    }

    private void signIn(){
        if (getStaffID().isEmpty()){
            staffIDEditTxt.setError("Staff ID Is Required!");
            staffIDEditTxt.requestFocus();
            return;
        }
        if (getEmail().isEmpty()) {
            emailEditTxt.setError("Email Is Required!");
        }
        if (getPassword().isEmpty()) {
            passwordEditTxt.setError("Password Is Required!");
            passwordEditTxt.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) { // Check is the email is real email
            emailEditTxt.setError("Please Enter A Valid Email");
            emailEditTxt.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(getEmail(), getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(DoctorLoginActivity.this, DoctorActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(DoctorLoginActivity.this, "Account Does Not Exist, Please Register First!", Toast.LENGTH_LONG ).show();
                }
            }
        });
    }
}