package com.example.shopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOTP extends AppCompatActivity {
    EditText edtOTP;
    Button btnNext;

    private String phoneNumber;
    private String verifyId;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        firebaseAuth = FirebaseAuth.getInstance();

        mapping();

        getData();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = edtOTP.getText().toString();
                onClickSendOTP(otp);
            }
        });
    }

    private void onClickSendOTP(String otp) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verifyId,otp);
        signInWithPhoneAuthCredential(phoneAuthCredential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToVerifyOTP(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG1", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyOTP.this, "Invalid!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToVerifyOTP(String phoneNumber) {
        Intent data = new Intent(VerifyOTP.this,RegisterForm.class);
        data.putExtra("phoneNumber",phoneNumber);
        startActivity(data);
    }

    private void getData() {
        Intent dataOTP = getIntent();
        phoneNumber = dataOTP.getStringExtra("sdt");
        verifyId = dataOTP.getStringExtra("verificationId");
    }

    private void mapping() {
        edtOTP = findViewById(R.id.edtOTP);
        btnNext = findViewById(R.id.btnNext);
    }
}