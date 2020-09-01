package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.PathEffect;
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
import java.util.Objects;

public class PatientRegistrationActivity extends AppCompatActivity {

    private EditText firstLegalNameEditTxt, lastLegalNameEditTxt, passwordEditTxt, confirmPasswordEditTxt,
            ageEditTxt, heightEditTxt, weightEditTxt, bloodTypeEditTxt, medicareNumberTxt, emailEditTxt;

    private RadioGroup genderRg;
    private TextView alreadyRegisteredTxt;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        // Initialise views
        initViews();
        // Initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Get an instance then a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("patients");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(getFirstLegalName(), getLastLegalName(), getEmail(), getPassword(), getConfirmPassword(), getGenderRg(), getBloodType(), getMedicareNumber());
            }
        });

        alreadyRegisteredTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientRegistrationActivity.this, PatientLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        firstLegalNameEditTxt = findViewById(R.id.firstLegalNameEditTxt);
        lastLegalNameEditTxt = findViewById(R.id.lastLegalNameEditTxt);
        emailEditTxt = findViewById(R.id.emailEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswordEditTxt);
        genderRg = findViewById(R.id.genderRg);
        ageEditTxt = findViewById(R.id.ageEditTxt);
        heightEditTxt = findViewById(R.id.heightEditTxt);
        weightEditTxt = findViewById(R.id.weightEditTxt);
        bloodTypeEditTxt = findViewById(R.id.bloodTypeEditTxt);
        medicareNumberTxt = findViewById(R.id.medicareNumberTxt);
        registerBtn = findViewById(R.id.registerBtn);
        alreadyRegisteredTxt = findViewById(R.id.alreadyRegisteredTxt);
    }

    private String getFirstLegalName() {
        return firstLegalNameEditTxt.getText().toString().trim();
    }

    private String getLastLegalName() {
        return lastLegalNameEditTxt.getText().toString().trim();
    }

    private String getEmail() {
        return emailEditTxt.getText().toString().trim();
    }

    private String getPassword() {
        return passwordEditTxt.getText().toString();
    }

    private String getConfirmPassword() {
        return confirmPasswordEditTxt.getText().toString();
    }

    public String getMedicareNumber() {
        return medicareNumberTxt.getText().toString();
    }

    public String getBloodType() {
        return bloodTypeEditTxt.getText().toString();
    }

    public String getHeight() {
        return heightEditTxt.getText().toString();
    }

    public String getWeight() {
        return weightEditTxt.getText().toString();
    }

    public String getAge() {
        return ageEditTxt.getText().toString();
    }

    public String getGenderRg() {
        RadioButton genderRadioBtn = findViewById(genderRg.getCheckedRadioButtonId());
        return genderRadioBtn.getText().toString().trim();
    }

    private void createAccount (String firstLegalName, String lastLegalName, String email, String password, String confirmPassword,
                                String gender, String bloodType, String medicareNumber) {
        if(firstLegalName.isEmpty()) {
            firstLegalNameEditTxt.setError("First Legal Name Is Required!");
            firstLegalNameEditTxt.requestFocus();
            return;
        }
        if(lastLegalName.isEmpty()) {
            lastLegalNameEditTxt.setError("Last Legal Name Is Required!");
            lastLegalNameEditTxt.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            emailEditTxt.setError("Please Enter Your Email!");
            emailEditTxt.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Check is the email is real email
            emailEditTxt.setError("Please Enter A Valid Email");
            emailEditTxt.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            passwordEditTxt.setError("Please Enter Your Password!");
            passwordEditTxt.requestFocus();
            return;
        }
        if(password.length() <= 7) {
            passwordEditTxt.setError("Password Need To Be At Least 8!");
            passwordEditTxt.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword)) {
            confirmPasswordEditTxt.setError( "Password Does Not Match!" );
            confirmPasswordEditTxt.requestFocus();
            return;
        }
        if (bloodType.isEmpty()) {
            bloodTypeEditTxt.setError("Blood Type Is Required");
            bloodTypeEditTxt.requestFocus();
            return;
        }
        if (medicareNumber.isEmpty()) {
            medicareNumberTxt.setError("Medicare Number Is Required");
            medicareNumberTxt.requestFocus();
            return;
        }
        // Check if medicare is 12 number here
        if (medicareNumber.length() != 12) {
            medicareNumberTxt.setError("Medicare should be 12 digit!");
            medicareNumberTxt.requestFocus();
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
                    userMap.put( "firstName", getFirstLegalName() );
                    userMap.put( "lastName", getLastLegalName() );
                    userMap.put( "password", getPassword() );
                    userMap.put( "gender", getGenderRg() );
                    userMap.put( "age", getAge() );
                    userMap.put( "height", getHeight() );
                    userMap.put( "weight", getWeight() );
                    userMap.put( "bloodType", getBloodType() );
                    userMap.put( "medicareNumber", getMedicareNumber());

                    childRef.updateChildren(userMap).addOnCompleteListener( new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PatientRegistrationActivity.this, "You've Successfully Registered!", Toast.LENGTH_SHORT ).show();
                                startActivity( new Intent( PatientRegistrationActivity.this, PatientActivity.class ) );
                                finish();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText( getApplicationContext(), "Oh no! Something went wrong!" + message, Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );

                } else if(task.getException() instanceof FirebaseAuthUserCollisionException) { // Check if account is already in used
                    Toast.makeText(PatientRegistrationActivity.this, "Account Already Existed!", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}