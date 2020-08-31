package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private EditText staffNumberEditTxt, emailEditTxt, firstLegalNameEditTxt, lastLegalNameEditTxt,
            passwordEditTxt, confirmPasswordEditTxt, ageEditTxt, specialtyEditTxt, phoneNumber;
    private RadioGroup genderRg;
    private Spinner clinicSpinner;
    private Button registerBtn;
    private TextView alreadyRegisterTxt;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        initViews();

        // initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // get an instance then a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("doctors");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(getStaffNumber(), getEmail(), getFirstLegalName(), getLastLegalName(), getPassword(), getConfirmPassword(), getSpecialty());
            }
        });

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
        emailEditTxt = findViewById(R.id.emailEditTxt);
        firstLegalNameEditTxt = findViewById(R.id.firstLegalNameEditTxt);
        lastLegalNameEditTxt = findViewById(R.id.lastLegalNameEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswordEditTxt);
        genderRg = findViewById(R.id.genderRg);
        ageEditTxt = findViewById(R.id.ageEditTxt);
        specialtyEditTxt = findViewById(R.id.specialtyEditTxt);
        clinicSpinner = findViewById(R.id.clinicSpinner);
        registerBtn = findViewById(R.id.registerBtn);
        phoneNumber = findViewById(R.id.phoneNumberEditTxt);
        alreadyRegisterTxt = findViewById(R.id.alreadyRegisterTxt);
    }

    public String getStaffNumber() {
        return staffNumberEditTxt.getText().toString();
    }

    public String getEmail() {
        return emailEditTxt.getText().toString();
    }

    public String getFirstLegalName() {
        return firstLegalNameEditTxt.getText().toString();
    }

    public String getLastLegalName() {
        return lastLegalNameEditTxt.getText().toString();
    }

    public String getPassword() {
        return passwordEditTxt.getText().toString();
    }

    public String getConfirmPassword() {
        return confirmPasswordEditTxt.getText().toString();
    }

    public String getGenderRg() {
        RadioButton genderRadioBtn = findViewById(genderRg.getCheckedRadioButtonId());
        return genderRadioBtn.getText().toString().trim();
    }

    public String getAgeEditTxt() {
        return ageEditTxt.getText().toString();
    }

    public String getSpecialty() {
        return specialtyEditTxt.getText().toString();
    }

    // Get clinic name here

    public String getPhoneNumber() {
        return phoneNumber.getText().toString();
    }


    // Do doctor registration function here

    private void createAccount(String staffNumber, String email, String firstLegalName, String lastLegalName, String password, String confirmPassword, String specialty){
        if (staffNumber.isEmpty()){
            staffNumberEditTxt.setError("Staff Number Is Required!");
            staffNumberEditTxt.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailEditTxt.setError("Valid Email Is Required");
            emailEditTxt.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Check is the email is real email
            emailEditTxt.setError("Please Enter A Valid Email");
            emailEditTxt.requestFocus();
            return;
        }
        if (firstLegalName.isEmpty()){
            firstLegalNameEditTxt.setError("First Legal Name Is Required");
            firstLegalNameEditTxt.requestFocus();
            return;
        }
        if (lastLegalName.isEmpty()){
            lastLegalNameEditTxt.setError("Last Legal Name Is Required");
            lastLegalNameEditTxt.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEditTxt.setError("Password Is Required");
            passwordEditTxt.requestFocus();
            return;
        }
        if (password.length() <= 7 ){
            passwordEditTxt.setError("Password Must Have At Least 8 Characters");
            passwordEditTxt.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)){
            confirmPasswordEditTxt.setError("Password Does Not Match!");
            confirmPasswordEditTxt.requestFocus();
            return;
        }
        if (specialty.isEmpty()){
            specialtyEditTxt.setError("Specialty Is Required");
            specialtyEditTxt.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert currentUser != null;
                    String uid = currentUser.getUid();
                    DatabaseReference childRef = mDatabase.child(uid);

                    HashMap userMap = new HashMap();
                    userMap.put( "staffNumber", getStaffNumber() );
                    userMap.put( "fistName", getFirstLegalName() );
                    userMap.put( "lastName", getLastLegalName() );
                    userMap.put( "gender", getGenderRg() );
                    userMap.put( "specialty", getSpecialty() );
                    // Add clinic to db as well

                    childRef.updateChildren(userMap).addOnCompleteListener( new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DoctorRegistrationActivity.this, "You've Successfully Registered!", Toast.LENGTH_SHORT ).show();
                                startActivity( new Intent( DoctorRegistrationActivity.this, DoctorActivity.class ) );
                                finish();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText( getApplicationContext(), "Oh no! Something went wrong!" + message, Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );
                } else if(task.getException() instanceof FirebaseAuthUserCollisionException) { // Check if account is already in used
                    Toast.makeText(DoctorRegistrationActivity.this, "Account Already Existed!", Toast.LENGTH_SHORT ).show();
                }
            }
        });

    };

}