package com.example.fricashop.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fricashop.R;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.presenter.UserPresenter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class CheckInfoPerson extends AppCompatActivity {
    TextInputEditText edtNamePerson,edtPhonePerson,edtEmailPerson,edtAddressPerson;
    Button btnChapNhan, btnTroVe;
    Toolbar tbCheckPerson;
    LinearLayout llPayment;
    TextView txtPayment;
    String tenPT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info_person);

        mapping();

        actionToolbar();

        addAction();
    }

    private void addAction() {

        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payment = new Intent(CheckInfoPerson.this,TypePayment.class);
                startActivityForResult(payment,300);
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnChapNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKH = edtNamePerson.getText().toString().trim();
                String emailKH = edtEmailPerson.getText().toString().trim();
                String sdtKH = edtPhonePerson.getText().toString().trim();
                String diaChiKH = edtAddressPerson.getText().toString().trim();
                String phuongThucThanhToan = txtPayment.getText().toString().trim();
                if(tenKH.equals("") && emailKH.equals("") && sdtKH.equals("") && diaChiKH.equals("") && phuongThucThanhToan.equals("")){
                    Toast.makeText(CheckInfoPerson.this, "Chưa điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    if(sdtKH.length() > 9){
                        if(Patterns.EMAIL_ADDRESS.matcher(emailKH).matches() && emailKH.length() > 0){ //Patterns: Mẫu Email_address: biểu thức chính quy matches: so sánh chuỗi ban đầu, matcher: nối chuỗi chính quy 
                            Intent dataKH = new Intent(CheckInfoPerson.this,CheckPurchaseInfo.class);
                            dataKH.putExtra("tenKH",tenKH);
                            dataKH.putExtra("emailKH",emailKH);
                            dataKH.putExtra("sdtKH",sdtKH);
                            dataKH.putExtra("diaChiKH",diaChiKH);
                            dataKH.putExtra("phuongThucThanhToan",phuongThucThanhToan);
                            startActivity(dataKH);
                        }else{
                            Toast.makeText(CheckInfoPerson.this, "Sai cú pháp email, yêu cầu nhập lại", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CheckInfoPerson.this, "Chưa điền đúng thứ tự số điện thoại!", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 300){
            if(resultCode == RESULT_OK){
                tenPT = data.getStringExtra("tenPT");
                txtPayment.setText(tenPT);
            }
        }
    }

    private void actionToolbar() {
        setSupportActionBar(tbCheckPerson);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Thông tin mua hàng");
        tbCheckPerson.setNavigationIcon(R.drawable.arrow_back);
        tbCheckPerson.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        edtNamePerson = findViewById(R.id.edtNamePerson);
        edtPhonePerson = findViewById(R.id.edtPhonePerson);
        edtEmailPerson = findViewById(R.id.edtEmailPerson);
        edtAddressPerson = findViewById(R.id.edtAddressPerson);
        btnChapNhan = findViewById(R.id.btnChapNhan);
        btnTroVe = findViewById(R.id.btnTroVe);
        txtPayment = findViewById(R.id.txtPayment);
        llPayment = findViewById(R.id.llPayment);
        tbCheckPerson = findViewById(R.id.tbCheckPerson);

    }
}