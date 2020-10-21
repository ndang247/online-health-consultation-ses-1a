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

public class PatientLoginActivity extends AppCompatActivity {

    private static String TAG = "LoginActivity";
    private TextView registerTxt;
    private EditText medicareNumberEditTxt, emailEditTxt, passwordEditTxt;
    private Button loginBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String medicareNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        // initialise views
        initViews();
        // initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // get an instance then a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("patients");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(getEmail(), getPassword());
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
        medicareNumberEditTxt = findViewById(R.id.medicareNumberEditTxt);
        emailEditTxt = findViewById(R.id.emailEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        loginBtn = findViewById(R.id.loginBtn);
        registerTxt = findViewById(R.id.alreadyRegisterTxt);
    }

    public String getMedicareNumber() {
        return medicareNumberEditTxt.getText().toString();
    }

    private String getEmail() {
        return emailEditTxt.getText().toString().trim();
    }

    private String getPassword() {
        return passwordEditTxt.getText().toString();
    }

    private void signIn(String email, String password) {
        if (getMedicareNumber().isEmpty()) {
            medicareNumberEditTxt.setError("Medicare Number Is Required!");
            medicareNumberEditTxt.requestFocus();
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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                            medicareNumber = snapshot.child("medicareNumber").getValue(String.class);
                            assert medicareNumber != null;
                            if (medicareNumber.equals(getMedicareNumber())) {
                                Intent intent = new Intent(PatientLoginActivity.this, PatientActivity.class);
                                startActivity(intent);
                            }
                            else {
                                medicareNumberEditTxt.setError("Incorrect Medicare Number!");
                                medicareNumberEditTxt.requestFocus();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else {
                    Toast.makeText(PatientLoginActivity.this, "Account Does Not Exist, Please Register First!", Toast.LENGTH_LONG ).show();
                }
            }
        });
    }
}