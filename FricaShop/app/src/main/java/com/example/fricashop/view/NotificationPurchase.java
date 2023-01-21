package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fricashop.HomePage;
import com.example.fricashop.R;

public class NotificationPurchase extends AppCompatActivity {
    Toolbar tbNotification;
    Button btnHomePage, btnReturnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_purchase);
        mapping();
        addAction();
    }

    private void addAction() {
        setSupportActionBar(tbNotification);
        getSupportActionBar().setTitle("Thông báo đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tbNotification.setNavigationIcon(R.drawable.arrow_back);
        tbNotification.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataHP = new Intent(NotificationPurchase.this, HomePage.class);
                startActivity(dataHP);
            }
        });
        btnReturnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnCart = new Intent(NotificationPurchase.this,CartCheckOut.class);
                startActivity(returnCart);
            }
        });
    }

    private void mapping() {
        tbNotification = findViewById(R.id.tbNotification);
        btnHomePage = findViewById(R.id.btnHomePage);
        btnReturnCart = findViewById(R.id.btnReturnCart);
    }
}