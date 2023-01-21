package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fricashop.R;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.UserPresenter;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePassword extends AppCompatActivity implements LoginInterface {
    TextInputEditText edtTakeUN, edtTakePassword, edtTakeRePassword;
    Toolbar tbChangePassword;
    Button btnOK;
    UserPresenter presenter = new UserPresenter(this);
    String tenTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mapping();

        Intent data = getIntent();
        tenTK = data.getStringExtra("tenTK");
        edtTakeUN.setText(tenTK);

        setSupportActionBar(tbChangePassword);
        tbChangePassword.setTitle("Thay đổi mật khẩu");
        tbChangePassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addAction();
    }

    private void addAction() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int tenTK = Integer.parseInt(edtTakeUN.getText().toString());
                String matKhauCu = edtTakePassword.getText().toString();
                String matKhauMoi = edtTakeRePassword.getText().toString();
                if(matKhauMoi.equals(matKhauCu)){
                    User u = new User(tenTK,matKhauMoi);
                    presenter.changePassword(u);

                }
            }
        });
    }

    private void mapping() {
        edtTakeUN = findViewById(R.id.edtTakeUN);
        edtTakePassword = findViewById(R.id.edtTakePassword);
        edtTakeRePassword = findViewById(R.id.edtTakeRePassword);
        tbChangePassword = findViewById(R.id.tbChangePassword);
        btnOK = findViewById(R.id.btnOK);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(ChangePassword.this,"Thay đổi mật khẩu thành công!",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void goHomePage() {

    }

    @Override
    public void showNotification() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}