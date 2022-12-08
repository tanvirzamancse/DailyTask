package com.codescafe.dailytask.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codescafe.dailytask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    ImageView back;
    ProgressBar progressBar;
    TextInputEditText email;
    AppCompatButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar = findViewById(R.id.pw_progressbar);
        send = findViewById(R.id.send);
        email = findViewById(R.id.recoveryEmail);

        progressBar.setVisibility(View.GONE);
        send.setVisibility(View.VISIBLE);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recoveryEmail = email.getText().toString().trim();

                if (recoveryEmail.isEmpty()) {
                    email.setError("Enter a valid email");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);

                    FirebaseAuth.getInstance().sendPasswordResetEmail(recoveryEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotActivity.this, "A password reset link has been sent to your email address", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ForgotActivity.this, SignInActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    send.setVisibility(View.VISIBLE);
                                }
                            });
                }
            }
        });

    }
}