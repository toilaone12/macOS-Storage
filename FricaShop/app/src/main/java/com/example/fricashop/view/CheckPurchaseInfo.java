package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fricashop.HomePage;
import com.example.fricashop.R;
import com.example.fricashop.adapter.PurchaseAdapter;
import com.example.fricashop.face.CartInterface;
import com.example.fricashop.model.Payment;
import com.example.fricashop.presenter.CartPresenter;

public class CheckPurchaseInfo extends AppCompatActivity implements CartInterface {
    Toolbar tbPurchase;
    RecyclerView rcyPurchase;
    PurchaseAdapter pAdapter;
    TextView txtNamePersonPurchase,txtPhonePersonPurchase,txtEmailPersonPurchase,txtCreditPersonPurchase, txtAddressPersonPurcharse;
    Button btnAcceptPurchase;
    CartPresenter cPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_purchase_info);
        mapping();

        actionToolBar();

        showPurchase();
    }

    private void showPurchase() {
        if(HomePage.cartList.size() > 0){
            pAdapter = new PurchaseAdapter(CheckPurchaseInfo.this,R.layout.activity_purchase_items,HomePage.cartList);
            LinearLayoutManager mPurchase = new LinearLayoutManager(CheckPurchaseInfo.this,RecyclerView.VERTICAL,false);
            rcyPurchase.setLayoutManager(mPurchase);
            rcyPurchase.setAdapter(pAdapter);
            Intent dataPurchase = getIntent();
            String tenKH = dataPurchase.getStringExtra("tenKH");
            String emailKH = dataPurchase.getStringExtra("emailKH");
            String sdtKH = dataPurchase.getStringExtra("sdtKH");
            String diachiKH = dataPurchase.getStringExtra("diaChiKH");
            String phuongThucThanhToan = dataPurchase.getStringExtra("phuongThucThanhToan");
            txtNamePersonPurchase.setText("Tên khách hàng: "+tenKH);
            txtPhonePersonPurchase.setText("Số điện thoại khách hàng: "+sdtKH);
            txtEmailPersonPurchase.setText("Email khách hàng: "+emailKH);
            txtAddressPersonPurcharse.setText("Địa chỉ khách hàng: "+diachiKH);
            txtCreditPersonPurchase.setText("Phương thức thanh toán: "+phuongThucThanhToan);

            btnAcceptPurchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    paymentCart(tenKH,emailKH,sdtKH,diachiKH,phuongThucThanhToan);
                }
            });
        }
    }

    private void paymentCart(String tenKH, String emailKH, String sdtKH, String diachiKH, String phuongThucThanhToan) {
        cPresenter = new CartPresenter(this);
        cPresenter.paymentSuccess(tenKH,emailKH,diachiKH,sdtKH,phuongThucThanhToan);
    }

    private void actionToolBar() {
        setSupportActionBar(tbPurchase);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Thông tin mua hàng");
        tbPurchase.setNavigationIcon(R.drawable.arrow_back);
        tbPurchase.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        tbPurchase = findViewById(R.id.tbPurchase);
        rcyPurchase = findViewById(R.id.rcyPurchase);
        txtNamePersonPurchase = findViewById(R.id.txtNamePersonPurchase);
        txtAddressPersonPurcharse = findViewById(R.id.txtAddressPersonPurchase);
        txtCreditPersonPurchase = findViewById(R.id.txtCreditPersonPurchase);
        txtEmailPersonPurchase = findViewById(R.id.txtEmailPersonPurchase);
        txtPhonePersonPurchase = findViewById(R.id.txtPhonePersonPurchase);
        btnAcceptPurchase = findViewById(R.id.btnAcceptPurchase);

    }

    @Override
    public void paymentSuccess() {
        Intent data = new Intent(CheckPurchaseInfo.this,NotificationPurchase.class);
        startActivity(data);
    }

    @Override
    public Context getCartContext() {
        return CheckPurchaseInfo.this;
    }

    @Override
    public void onClickPaymentItems(Payment paList, int i) {

    }
}