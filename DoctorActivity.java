package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.models.Doctor;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView fullName, staffNumber, firstName, lastName, gender, age, specialty, clinicName, phoneNumber;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView navIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        initViews();

        navigationDrawer();

        // Initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get an instance then a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference("doctors");
        FirebaseUser currentUser = mAuth.getCurrentUser();

        assert currentUser != null;
        final String uid = currentUser.getUid();
        DatabaseReference childRef = mDatabase.child(uid);

        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                assert doctor != null;
                fullName.setText(getString(R.string.doctor_name_txt).concat(" " + doctor.getFirstLegalName() + " " + doctor.getLastLegalName()));
                staffNumber.setText(doctor.getStaffNumber());
                firstName.setText(doctor.getFirstLegalName());
                lastName.setText(doctor.getLastLegalName());
                gender.setText(doctor.getGender());
                age.setText(doctor.getAge());
                specialty.setText(doctor.getSpecialty());
                clinicName.setText(doctor.getClinicName());
                phoneNumber.setText(doctor.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {
        fullName = findViewById(R.id.fullName);
        staffNumber = findViewById(R.id.staffNumber);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        specialty = findViewById(R.id.specialty);
        clinicName = findViewById(R.id.clinicName);
        phoneNumber = findViewById(R.id.phoneNumber);
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
                Intent msgIntent = new Intent(DoctorActivity.this, DoctorChatActivity.class);
                startActivity(msgIntent);
                break;
            case R.id.nav_contacts:
                break;
            case R.id.nav_search:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Intent loginIntent = new Intent(DoctorActivity.this, DoctorLoginActivity.class);
                startActivity(loginIntent);
                finish();
                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}