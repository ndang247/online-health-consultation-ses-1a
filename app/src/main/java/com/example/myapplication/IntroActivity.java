package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        initViews();
    }

    private void initViews() {
        getStartedBtn = findViewById(R.id.getStartedBtn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getStartedBtn:
                signInOptions();
                break;
        }
    }

    private void signInOptions() {
        Intent intent = new Intent(IntroActivity.this, SignInOptionsActivity.class);
        startActivity(intent);
        finish();
    }
}