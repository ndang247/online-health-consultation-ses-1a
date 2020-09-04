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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText staffIDEditTxt, emailEditTxt, passwordEditTxt;
    private Button loginAsDoctorBtn;
    private TextView createNewAccTxt;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String staffID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        initViews();

        // Initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get an instance then a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("doctors");

        loginAsDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(getEmail(), getPassword());
            }
        });

        createNewAccTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorLoginActivity.this, DoctorRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        staffIDEditTxt = findViewById(R.id.staffIDEditTxt);
        emailEditTxt = findViewById(R.id.emailEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        loginAsDoctorBtn = findViewById(R.id.loginAsDoctorBtn);
        createNewAccTxt = findViewById(R.id.createNewAccTxt);
    }

    public String getStaffID() { return staffIDEditTxt.getText().toString(); }

    public String getEmail() {
        return emailEditTxt.getText().toString();
    }

    public String getPassword() {
        return passwordEditTxt.getText().toString();
    }

    private void signIn(String email, String password){
        if (getStaffID().isEmpty()) {
            staffIDEditTxt.setError("ID Is Required!");
            staffIDEditTxt.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailEditTxt.setError("Email Is Required!");
            emailEditTxt.requestFocus();
            return;
        }
        if (password.isEmpty()) {
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
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert currentUser != null;
                    String uid = currentUser.getUid();
                    DatabaseReference childRef = mDatabase.child(uid);

                    childRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            staffID = snapshot.child("staffNumber").getValue(String.class);
                            assert staffID != null;
                            if (staffID.equals(getStaffID())) {
                                Intent intent = new Intent(DoctorLoginActivity.this, DoctorActivity.class);
                                startActivity(intent);
                            }
                            else {
                                staffIDEditTxt.setError("Incorrect ID!");
                                staffIDEditTxt.requestFocus();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else {
                    Toast.makeText(DoctorLoginActivity.this, "Account Does Not Exist, Please Register First!", Toast.LENGTH_LONG ).show();
                }
            }
        });
    }
}