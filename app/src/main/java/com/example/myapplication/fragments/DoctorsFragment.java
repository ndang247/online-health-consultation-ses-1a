package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapters.DoctorAdapter;
import com.example.myapplication.models.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> mDoctors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDoctors = new ArrayList<>();

        readDoctors();

        return view;
    }

    private void readDoctors() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("doctors");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDoctors.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    mDoctors.add(doctor);
                }

                doctorAdapter = new DoctorAdapter(getContext(), mDoctors, false);
                recyclerView.setAdapter(doctorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}