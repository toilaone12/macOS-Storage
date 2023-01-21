package com.example.mvpdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvpdemo.R;
import com.example.mvpdemo.face.RegisterInterface;
import com.example.mvpdemo.model.User;
import com.example.mvpdemo.presenter.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface {
    EditText edtUserName, edtPass, edtName, edtAge,edtRePass;
    Button btnRegister, btnExit;
    TextView txtThongBao;
    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenter(this);
        mapping();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtName.getText().toString();
                String tk = edtUserName.getText().toString();
                String mk = edtPass.getText().toString();
                String tuoi = edtAge.getText().toString();
                String ktmk = edtRePass.getText().toString();
                if(mk.equals(ktmk)){
                    registerPresenter.checkRegister(new User(tk,ten,tuoi,mk));
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPass = findViewById(R.id.edtPass);
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtRePass = findViewById(R.id.edtRePass);
        btnRegister = findViewById(R.id.btnRegister);
        btnExit = findViewById(R.id.btnExit);
        txtThongBao = findViewById(R.id.txtThongBao);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(RegisterActivity.this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goLogin() {
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
    }

    @Override
    public void showNotification() {
        txtThongBao.setText("Có vấn đề!");
        txtThongBao.setVisibility(View.VISIBLE);
    }
}