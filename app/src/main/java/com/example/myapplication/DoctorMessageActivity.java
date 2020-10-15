package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapters.MessageAdapter;
import com.example.myapplication.models.Chat;
import com.example.myapplication.models.Doctor;
import com.example.myapplication.models.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorMessageActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView username;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private Intent intent;

    private ImageButton btnSend;
    private EditText txtSend;

    private MessageAdapter messageAdapter;
    private List<Chat> mChats;

    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorMessageActivity.this, DoctorChatActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profileImage = findViewById(R.id.profileImage);
        username = findViewById(R.id.username);
        btnSend = findViewById(R.id.btnSend);
        txtSend = findViewById(R.id.txtSend);

        intent = getIntent();
        final String patientID = intent.getStringExtra("patientID");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = txtSend.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(firebaseUser.getUid(), patientID, msg);
                } else {
                    Toast.makeText(DoctorMessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                txtSend.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("patients").child(patientID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Patient patient = snapshot.getValue(Patient.class);
                assert patient != null;
                username.setText(patient.getFirstLegalName().concat(" " + patient.getLastLegalName()));
                // Set profile image here
                profileImage.setImageResource(R.mipmap.ic_launcher);
                //

                readMessages(firebaseUser.getUid(), patientID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("chats").push().setValue(hashMap);
    }

    private void readMessages(final String myID, final String patientID/*, final String imageURL*/) {
        mChats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChats.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myID) && chat.getSender().equals(patientID) ||
                            chat.getReceiver().equals(patientID) && chat.getSender().equals(myID)) {
                        mChats.add(chat);
                    }

                    messageAdapter = new MessageAdapter(DoctorMessageActivity.this, mChats/*, imageUR*/);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}