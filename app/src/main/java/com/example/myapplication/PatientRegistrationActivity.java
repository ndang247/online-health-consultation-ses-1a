package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class PatientRegistrationActivity extends AppCompatActivity {

    private EditText firstLegalNameEditTxt, middleLegalEditTxt, lastLegalNameEditTxt, emailEditTxt, passwordEditTxt, confirmPasswordEditTxt;
    private TextView already_have_an_account_txt;
    private Button registerBtn;
    private FirebaseAuth mAuth;

    // patient profile:
    // medicare number, username
    // blood type, height, weight

    // for doctor login/registration:
    // clinics - dropdown menu or text field
    // specialty - text field
    // verification/certificate numbers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        // initialise views
        initViews();
        // Initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(getEmail(), getPassword(), getConfirmPassword());
            }
        });

        already_have_an_account_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientRegistrationActivity.this, PatientLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        firstLegalNameEditTxt = findViewById(R.id.firstLegalNameEditTxt);
        middleLegalEditTxt = findViewById(R.id.middleNameEditTxt);
        lastLegalNameEditTxt = findViewById(R.id.lastLegalNameEditTxt);
        emailEditTxt = findViewById(R.id.emailEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswordEditTxt);
        registerBtn = findViewById(R.id.registerBtn);
        already_have_an_account_txt = findViewById(R.id.textView3);
    }

    private String getFirstLegalName() { return firstLegalNameEditTxt.getText().toString(); }

    private String getMiddleName() { return middleLegalEditTxt.getText().toString(); }

    private String getLastLegalName() { return lastLegalNameEditTxt.getText().toString(); }

    private String getEmail() {
        return emailEditTxt.getText().toString().trim();
    }

    private String getPassword() {
        return passwordEditTxt.getText().toString();
    }

    private String getConfirmPassword() {
        return confirmPasswordEditTxt.getText().toString();
    }

    private void createAccount (String email, final String password, String confirmPassword) {
        if(getFirstLegalName().isEmpty()) {
            firstLegalNameEditTxt.setError("First Legal Name Is Required!");
            firstLegalNameEditTxt.requestFocus();
        }

        if(getLastLegalName().isEmpty()) {
            lastLegalNameEditTxt.setError("Last Legal Name Is Required!");
            lastLegalNameEditTxt.requestFocus();
        }

        if(email.isEmpty()) {
            emailEditTxt.setError("Please Enter Your Email!");
            emailEditTxt.requestFocus();
        }
        if(password.isEmpty()) {
            passwordEditTxt.setError("Please Enter Your Password!");
            passwordEditTxt.requestFocus();
        }
        if(password.length() <= 7) {
            passwordEditTxt.setError("Password Need To Be At Least 8!");
            passwordEditTxt.requestFocus();
        }
        if(!password.equals(confirmPassword)) {
            confirmPasswordEditTxt.setError( "Password Does Not Match!" );
            confirmPasswordEditTxt.requestFocus();
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(PatientRegistrationActivity.this, "You've Successfully Registered!", Toast.LENGTH_SHORT ).show();
                    Intent intent =new Intent(PatientRegistrationActivity.this, PatientActivity.class);
                    startActivity(intent);
                } else if(task.getException() instanceof FirebaseAuthUserCollisionException) { // Check if account is already in used
                    Toast.makeText(PatientRegistrationActivity.this, "Account Already Existed!", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}