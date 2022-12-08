package com.codescafe.dailytask.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codescafe.dailytask.Model.UserProfile;
import com.codescafe.dailytask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity implements View.OnClickListener{
    private TextView gotosignup, forgotpassword;
    private EditText editTextEmail, editTextPassword;
    private Button btnlogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        gotosignup = (TextView) findViewById(R.id.gotosignup);
        gotosignup.setOnClickListener(this);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotosignup:
                startActivity(new Intent(this,SignUp.class));
                break;

            case R.id.btnlogin:
                userlogin();
                break;

            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }

    }
    private void userlogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("This field cannot be empty");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("This field cannot be empty");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        startActivity(new Intent(LogIn.this, UserProfile.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(LogIn.this, "Please check your email for validation", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LogIn.this, "log In failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

