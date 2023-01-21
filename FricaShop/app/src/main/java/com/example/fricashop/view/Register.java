package com.example.fricashop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fricashop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    Toolbar tbRegister;
    TextInputEditText edtPhoneNumberRegister;
    Button btnNext;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mapping();

        firebaseAuth = FirebaseAuth.getInstance();
        addAction();
        actionToolBar();
    }

    private void actionToolBar() {
        setSupportActionBar(tbRegister);
        getSupportActionBar().setTitle("Đăng ký");
        tbRegister.setNavigationIcon(R.drawable.arrow_back);
        tbRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addAction() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtPhoneNumber = edtPhoneNumberRegister.getText().toString();
                if(edtPhoneNumber.equals("")){
                    Toast.makeText(Register.this, "Không có dữ liệu, yêu cầu điền vào!", Toast.LENGTH_SHORT).show();
                }else{
                    sendSMS(edtPhoneNumber);
                }

            }
        });
    }

    private void sendSMS(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(Register.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Register.this, "Fail!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                goToEnterOTP(phoneNumber,verificationId); // nếu k nhận mã otp thực hiện logic gửi lại mã
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
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
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Register.this, "Invalid!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToEnterOTP(String phoneNumber, String verificationId) {
        Intent data = new Intent(Register.this,VerifyOTP.class);
        data.putExtra("sdt",phoneNumber);
        data.putExtra("verificationId",verificationId);
        startActivity(data);
    }

    private void goToVerifyOTP(String phone) {
        Toast.makeText(this, ""+phone, Toast.LENGTH_SHORT).show();
    }

    private void mapping() {
        tbRegister = findViewById(R.id.tbRegister);
        edtPhoneNumberRegister = findViewById(R.id.edtPhoneNumberRegister);
        btnNext = findViewById(R.id.btnNext);
    }
}