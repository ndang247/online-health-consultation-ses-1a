package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PatientActivity extends AppCompatActivity {

    private TextView fullName, firstName, lastName, gender, age, height, weight, bloodType, medicare;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        initViews();
        // initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // get an instance then a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("patients");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        final String uid = currentUser.getUid();
        DatabaseReference childRef = mDatabase.child(uid);
        childRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullName.setText(getString(R.string.patient_name_txt).concat(" " + Objects.requireNonNull(snapshot.child("firstName").getValue(String.class)) + " " + Objects.requireNonNull(snapshot.child("lastName").getValue(String.class))));
                firstName.setText(snapshot.child("firstName").getValue(String.class));
                lastName.setText(snapshot.child("lastName").getValue(String.class));
                gender.setText(snapshot.child("gender").getValue(String.class));
                age.setText(snapshot.child("age").getValue(String.class));
                height.setText(snapshot.child("height").getValue(String.class));
                weight.setText(snapshot.child("weight").getValue(String.class));
                bloodType.setText(snapshot.child("bloodType").getValue(String.class));
                medicare.setText(snapshot.child("medicare").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void initViews() {
        fullName = findViewById(R.id.fullName);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        bloodType = findViewById(R.id.bloodType);
        medicare = findViewById(R.id.medicare);
    }


}