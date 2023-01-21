package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.R;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.presenter.UserPresenter;

public class RegisterForm extends AppCompatActivity implements LoginInterface {
    Toolbar tbRegisterForm;
    EditText edtPasswordRegister,edtCheckPasswordRegister;
    TextView txtNotificationRegister;
    Button btnAccept;
    UserPresenter userPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        mapping();

        addAction();

        actionToolBar();
    }

    private void actionToolBar() {
        setSupportActionBar(tbRegisterForm);
        getSupportActionBar().setTitle("Đăng ký");
        tbRegisterForm.setNavigationIcon(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tbRegisterForm.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addAction() {

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerSuccess();

            }
        });


//        RequestQueue queueRegister = Volley.newRequestQueue()
    }

    private void registerSuccess() {
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String password = edtPasswordRegister.getText().toString();
        String rePassword = edtCheckPasswordRegister.getText().toString();
        if(rePassword.equals(password)){
            userPresenter = new UserPresenter(this);
            userPresenter.registerSuccess(phoneNumber,password);
        }else{
            txtNotificationRegister.setText("Mật khẩu không trùng khớp, yêu cầu nhập lại!");
            txtNotificationRegister.setVisibility(View.INVISIBLE);
        }
    }

    private void mapping() {
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtCheckPasswordRegister = findViewById(R.id.edtCheckPasswordRegister);
        txtNotificationRegister = findViewById(R.id.txtNotificationRegister);
        tbRegisterForm = findViewById(R.id.tbRegisterForm);
        btnAccept = findViewById(R.id.btnAccept);
    }

    @Override
    public void loginSuccess() {
        Intent data = new Intent(RegisterForm.this,Login.class);
        startActivity(data);
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void goHomePage() {

    }

    @Override
    public void showNotification() {
        txtNotificationRegister.setText("Tài khoản đã được đăng ký!");
        txtNotificationRegister.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getContext() {
        return RegisterForm.this;
    }
}