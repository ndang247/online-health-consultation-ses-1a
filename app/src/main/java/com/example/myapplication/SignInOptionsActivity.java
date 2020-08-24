package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private Button loginAsPatientBtn;
    private Button loginAsDoctorBtn;
    private SignInButton googleSignInBtn;
    private GoogleSignInClient mSignInClient;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_options);

        initViews();

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        // Set click listeners
        googleSignInBtn.setOnClickListener(this);
        loginAsPatientBtn.setOnClickListener(this);
        loginAsDoctorBtn.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initViews() {
        loginAsPatientBtn = findViewById(R.id.loginAsPatientBtn);
        loginAsDoctorBtn = findViewById(R.id.loginAsDoctorBtn);
        googleSignInBtn = findViewById(R.id.googleSignInBtn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.googleSignInBtn:
                signInWithGoogle();
                break;
            case R.id.loginAsPatientBtn:
                loginAsPatient();
                break;
            case R.id.loginAsDoctorBtn:
                // do something
                break;
        }
    }

    private void loginAsPatient() {
        Intent intent = new Intent(SignInOptionsActivity.this, PatientLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInWithGoogle() {
        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent in signIn()
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Intent intent = new Intent(SignInOptionsActivity.this, SignInOptionsActivity.class);
                startActivity(intent);
                Log.w(TAG, "Google Sign In Failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignInOptionsActivity.this, PatientActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.w(TAG, "signInWithCredential", task.getException());
                    Toast.makeText(SignInOptionsActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}