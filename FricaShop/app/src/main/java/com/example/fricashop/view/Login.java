package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.fricashop.HomePage;
import com.example.fricashop.R;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.face.VolleyCallBack;
import com.example.fricashop.model.Category;
import com.example.fricashop.model.Product;
import com.example.fricashop.model.Slider;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.UserPresenter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements LoginInterface, VolleyCallBack {
    Toolbar tbLogin;
    TextInputEditText edtUserName, edtPassword;
    CheckBox ckbRemember;
    Button btnLogin, btnRegister;
    User u;
    UserPresenter userPresenter;
    SharedPreferences sharedPreferences; // lưu trữ thông tin dưới dạng key_value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);

        userPresenter = new UserPresenter(this);
        userPresenter.getUserList(new VolleyCallBack() {
            @Override
            public void onSuccess(List<User> list) {

            }

            @Override
            public void takeSuccessProduct(List<Product> list) {

            }

            @Override
            public void takeAll(List<Category> list) {

            }

            @Override
            public void getSlider(List<Slider> list) {

            }
        });
        mapping();
        actionToolBar();
        addAction();
        edtUserName.setText(sharedPreferences.getString("taikhoan",""));
        edtPassword.setText(sharedPreferences.getString("matkhau",""));
        ckbRemember.setChecked(sharedPreferences.getBoolean("checked",false));
    }

    private void actionToolBar() {
        setSupportActionBar(tbLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Đăng nhập");
    }

    private void addAction() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u = new User(edtUserName.getText().toString(),edtPassword.getText().toString());
//                if (u.getPhoneNumber().equals(edtUserName.getText().toString()) && u.getPassword().equals(edtPassword.getText().toString())){
                userPresenter.checkLogin(u);
//                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataRegister = new Intent(Login.this,Register.class);
                startActivity(dataRegister);
            }
        });
    }

    private void mapping() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        ckbRemember = findViewById(R.id.ckbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tbLogin = findViewById(R.id.tbLogin);
    }

    @Override
    public void loginSuccess() {
        if(ckbRemember.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit(); // nơi lưu trữ thông tin dưới dạng key-value đc xậy dựng trong android
            editor.putString("taikhoan",u.getPhoneNumber());
            editor.putString("matkhau",u.getPassword());
            editor.putBoolean("checked",true);
            editor.commit();
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("taikhoan");
            editor.remove("matkhau");
            editor.remove("checked");
            editor.commit(); // thực thi việc lưu trữ
        }
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void goHomePage() {
        Intent data = new Intent(Login.this, HomePage.class);
        startActivity(data);
    }

    @Override
    public void showNotification() {
        Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu sai, yêu cầu nhập lại!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return Login.this;
    }

    @Override
    public void onSuccess(List<User> list) {

    }

    @Override
    public void takeSuccessProduct(List<Product> list) {

    }

    @Override
    public void takeAll(List<Category> list) {

    }

    @Override
    public void getSlider(List<Slider> list) {

    }
}