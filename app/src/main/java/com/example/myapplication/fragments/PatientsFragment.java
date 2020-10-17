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
import com.example.myapplication.adapters.PatientAdapter;
import com.example.myapplication.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter patientAdapter;
    private List<Patient> mPatients;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patients, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPatients = new ArrayList<>();

        readPatients();

        return view;
    }

    private void readPatients() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("patients");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPatients.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Patient patient = dataSnapshot.getValue(Patient.class);
                    mPatients.add(patient);
                }

                patientAdapter = new PatientAdapter(getContext(), mPatients, false);
                recyclerView.setAdapter(patientAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}