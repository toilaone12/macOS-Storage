package com.example.fricashop.view;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.R;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.UserPresenter;
import com.example.fricashop.volley.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Personal extends AppCompatActivity implements LoginInterface {
    ImageView imgPerson;
    LinearLayout llNamePerson, llUserName, llEmail, llDoiMatKhau;
    Button btnDangXuat;
    TextView txtNamePerson,txtUserName,txtEmail;
    UserPresenter us;
    public static final int CODE_CAMERA = 1;
    public static final int REQUEST_OPEN_CAMERA = 2;
    Bitmap bitmap;
    User u;
    Toolbar tbPersonal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        mapping();

        if(UserPresenter.userList.size() > 0){
            for (int i = 0; i < UserPresenter.userList.size(); i++){
                u = UserPresenter.userList.get(i);
                if(u.getImage() == null){
                    imgPerson.setImageResource(R.drawable.card);
                }else if(!u.getImage().equals("")){
                    Picasso.get().load(u.getImage()).resize(200,200).centerCrop().into(imgPerson);
                }
                txtEmail.setText(u.getEmail());
                txtNamePerson.setText(u.getName());
                txtUserName.setText(u.getPhoneNumber());
            }

        }

        actionToolBar();

        addAction();
    }

    private void actionToolBar() {
        setSupportActionBar(tbPersonal);
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tbPersonal.setNavigationIcon(R.drawable.arrow_back);
        tbPersonal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addAction() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(Personal.this, Login.class);
                data.putExtra("phone",u.getPhoneNumber());
                data.putExtra("pass",u.getPassword());
                startActivity(data);
//                UserPresenter.userList.clear();
            }
        });
        llDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(Personal.this,ChangePassword.class);
                data.putExtra("tenTK",u.getPhoneNumber());
                startActivity(data);
//                Toast.makeText(Personal.this, "Click!", Toast.LENGTH_SHORT).show();
            }
        });
        llNamePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data1 = new Intent(Personal.this,ChangeFullName.class);
                data1.putExtra("id",u.getId());
                data1.putExtra("hoTen",u.getName());
//                System.out.println("Tên: "+u.getName());
//                Log.d("hoten",u.getName());
                startActivityForResult(data1,123);
//                                Toast.makeText(Personal.this, "Click!", Toast.LENGTH_SHORT).show();

            }
        });
        llEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataEmail = new Intent(Personal.this,ChangeEmail.class);
                dataEmail.putExtra("id",u.getId());
                dataEmail.putExtra("email",u.getEmail());
                startActivityForResult(dataEmail,333);
            }
        });
        imgPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},CODE_CAMERA);

            }
        });
    }

    private void showImage(Bitmap bm) {
        us = new UserPresenter(this);
        us.imagePersonal(bm,UserPresenter.userList.get(0).getId());
    }

    private void mapping() {
        imgPerson = findViewById(R.id.imgPerson);
        llNamePerson = findViewById(R.id.llNamePerson);
        llUserName = findViewById(R.id.llUserName);
        llEmail = findViewById(R.id.llEmail);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        txtNamePerson = findViewById(R.id.txtNamePerson);
        txtUserName = findViewById(R.id.txtUserNamePerson);
        txtEmail = findViewById(R.id.txtEmailPerson);
        llDoiMatKhau = findViewById(R.id.llDoiMatKhau);
        tbPersonal = findViewById(R.id.tbPersonal);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(Personal.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(Personal.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(Personal.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_OPEN_CAMERA);
            }
        } else {
            Log.e("Else", "Else");
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_OPEN_CAMERA);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OPEN_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(picUri);
                    bitmap = BitmapFactory.decodeStream(is);
                    imgPerson.setImageBitmap(bitmap);
                    showImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }else if(requestCode == 123){
            if(resultCode == Activity.RESULT_OK){
                String hoTenMoi = data.getStringExtra("hoTenMoi");
                u.setName(hoTenMoi);
                txtNamePerson.setText(hoTenMoi);
                Toast.makeText(this, "Thay đổi tên thành công!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Thay đổi tên không thành công!", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 333){
            if (resultCode == RESULT_OK){
                String emailMoi = data.getStringExtra("emailMoi");
                u.setEmail(emailMoi);
                txtEmail.setText(emailMoi);
                Toast.makeText(this, "Thay đổi email thành công!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Thay đổi email không thành công!", Toast.LENGTH_SHORT).show();

            }
        }

    }


    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public void goHomePage() {
        finish();
    }

    @Override
    public void showNotification() {

    }

    @Override
    public Context getContext() {
        return Personal.this;
    }
}