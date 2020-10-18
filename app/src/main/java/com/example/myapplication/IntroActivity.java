package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    private Button getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        initViews();

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInOptions();
            }
        });
    }

    private void initViews() {
        getStartedBtn = findViewById(R.id.getStartedBtn);
    }

    private void signInOptions() {
        Intent intent = new Intent(IntroActivity.this, SignInOptionsActivity.class);
        startActivity(intent);
    }
}