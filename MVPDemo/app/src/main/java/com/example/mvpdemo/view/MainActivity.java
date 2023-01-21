package com.example.mvpdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvpdemo.R;
import com.example.mvpdemo.face.LoginInterface;
import com.example.mvpdemo.face.VolleyCallBack;
import com.example.mvpdemo.model.User;
import com.example.mvpdemo.presenter.LoginPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginInterface,VolleyCallBack{
    EditText edtName,edtPass;
    Button btnLogin, btnExit;
    CheckBox ckbCheck;
    TextView txtThongBao;
    LoginPresenter loginPresenter;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.getUserList(new VolleyCallBack() {
            @Override
            public void onSuccess(List<String> stringList) {
                for (String s: stringList) {
                    Log.d("AAA",s.toString());
                }
            }
        });
        mapping();
        addAction();
    }

    private void addAction() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtName.getText().toString().trim();
                String mk = edtPass.getText().toString().trim();
                u = new User(0,ten,mk);
                loginPresenter.checkLogin(u);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }

    private void mapping() {
        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnExit = findViewById(R.id.btnExit);
        ckbCheck = findViewById(R.id.ckLuu);
        txtThongBao = findViewById(R.id.txtThongBao);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail() {
        Toast.makeText(MainActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openHomePage() {
//        startActivity(new Intent());
    }

    @Override
    public void showNotification() {
        txtThongBao.setVisibility(View.VISIBLE);
        txtThongBao.setText("Tài khoản hoặc mật khẩu không đúng!");
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public void onSuccess(List<String> stringList) {
        for (String s: stringList) {
            Log.d("BBB",s.toString());
        }
    }
}