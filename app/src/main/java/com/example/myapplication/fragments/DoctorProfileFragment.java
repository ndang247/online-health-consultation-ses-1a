package com.example.myapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Doctor;
import com.example.myapplication.models.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileFragment extends Fragment {

    CircleImageView profileImage;
    TextView profileUsername;

    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        profileImage = view.findViewById(R.id.profileImage);
        profileUsername = view.findViewById(R.id.profileUsername);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("doctors").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                assert doctor != null;
                profileUsername.setText("Dr".concat(" " + doctor.getFirstLegalName() + " " + doctor.getLastLegalName()));
                if (doctor.getImageURL().equals("default")) {
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(Objects.requireNonNull(getContext())).load(doctor.getImageURL()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}