package com.gero.yummzyrecipe.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.gero.yummzyrecipe.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.name) EditText names;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.email) EditText email;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.password) EditText password;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.confirm_password) EditText confirm_password;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_up) Button signUp;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingIndicatorView;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        loadingIndicatorView.hide();


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase.getInstance().getReference();

        signUp.setOnClickListener(this);

    }

    public boolean isValid()
    {
        boolean valid = false;

        if (names.getText().toString().trim().isEmpty())
        {
            names.setError("Please enter your name");
        }
        else if(email.getText().toString().trim().isEmpty())
        {
            email.setError("Please enter your email");
        }
        else if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()))
        {
            email.setError("Please enter a valid email");
        }
        else if(password.getText().toString().trim().isEmpty())
        {
            password.setError("Please enter a password");
        }
        else if(confirm_password.getText().toString().trim().isEmpty())
        {
            confirm_password.setError("Please enter a password");
        }
        else if(!(confirm_password.getText().toString().trim().equals(password.getText().toString().trim())))
        {
            confirm_password.setError("Password doesn't match");
        }

        else {
            valid = true;
        }
        return valid;
    }

    @Override
    public void onClick(View v) {

        if (v==signUp)
        {
            if(isValid())
            {
                loadingIndicatorView.show();
                signUp.setEnabled(false);
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                loadingIndicatorView.hide();
                                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                //avoids using the back btn to go back to the signup activity after successful login
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                setDisplayName(Objects.requireNonNull(task.getResult().getUser()));
                                startActivity(intent);
                            } else {
                                loadingIndicatorView.hide();
                                Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                signUp.setEnabled(true);
                            }
                        });
            }

        }
    }

    public void setDisplayName(final FirebaseUser firebaseUser)
    {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(names.getText().toString().trim())
                .build();
        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated.");
                    }
                });
    }
}
