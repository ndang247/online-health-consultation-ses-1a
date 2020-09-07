package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.models.Patient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView fullName, firstName, lastName, gender, age, height, weight, bloodType, medicare;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView navIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        initViews();

        navigationDrawer();

        // Initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get an instance then a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("patients");
        FirebaseUser currentUser = mAuth.getCurrentUser();

        assert currentUser != null;
        final String uid = currentUser.getUid();
        DatabaseReference childRef = mDatabase.child(uid);

        childRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Patient patient = snapshot.getValue(Patient.class);

                assert patient != null;
                fullName.setText(getString(R.string.patient_name_txt).concat(" " + patient.getFirstLegalName() + " " + patient.getLastLegalName()));
                firstName.setText(patient.getFirstLegalName());
                lastName.setText(patient.getLastLegalName());
                gender.setText(patient.getGender());
                age.setText(patient.getAge());
                height.setText(patient.getHeight());
                weight.setText(patient.getWeight());
                bloodType.setText(patient.getBloodType());
                medicare.setText(patient.getMedicareNumber());

                /*fullName.setText(getString(R.string.patient_name_txt).concat(" " + Objects.requireNonNull(snapshot.child("firstName").getValue(String.class)) + " " + Objects.requireNonNull(snapshot.child("lastName").getValue(String.class))));
                firstName.setText(snapshot.child("firstName").getValue(String.class));
                lastName.setText(snapshot.child("lastName").getValue(String.class));
                gender.setText(snapshot.child("gender").getValue(String.class));
                age.setText(snapshot.child("age").getValue(String.class));
                height.setText(snapshot.child("height").getValue(String.class));
                weight.setText(snapshot.child("weight").getValue(String.class));
                bloodType.setText(snapshot.child("bloodType").getValue(String.class));
                medicare.setText(snapshot.child("medicareNumber").getValue(String.class));*/
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
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        navIcon = findViewById(R.id.navIcon);
    }

    // Navigation drawer functions
    private void navigationDrawer() {
        // Navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_edit_profile:
                break;
            case R.id.nav_messages:
                Intent msgIntent = new Intent(PatientActivity.this, PatientChatActivity.class);
                startActivity(msgIntent);
                break;
            case R.id.nav_contacts:
                break;
            case R.id.nav_search:
                break;
            case R.id.nav_doctor_location:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Intent loginIntent = new Intent(PatientActivity.this, PatientLoginActivity.class);
                startActivity(loginIntent);
                finish();
                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}