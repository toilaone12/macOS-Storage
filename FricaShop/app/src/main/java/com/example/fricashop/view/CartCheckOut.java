package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fricashop.HomePage;
import com.example.fricashop.R;
import com.example.fricashop.adapter.CartAdapter;
import com.example.fricashop.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartCheckOut extends AppCompatActivity {
    static List<Product> pList;
    CartAdapter cartAdapter;
    Button btnThanhToan,btnTiepTuc;
    static TextView txtTongSanPham;
    RecyclerView rcyGioHang;
    Toolbar tbTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_check_out);

        mapping();

        actionToolBar();

        getAllCart();

        takeAllMoney();

        addAction();

    }

    private void addAction() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnHomePage = new Intent(CartCheckOut.this,HomePage.class);
                startActivity(returnHomePage);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HomePage.cartList.size() > 0){
                    Intent data = new Intent(CartCheckOut.this,CheckInfoPerson.class);
                    startActivity(data);
                }else{
                    Toast.makeText(CartCheckOut.this, "Chưa có sản phẩm trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void actionToolBar() {
        setSupportActionBar(tbTitle);
        getSupportActionBar().setTitle("Giỏ hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getAllCart() {
        cartAdapter = new CartAdapter(CartCheckOut.this,R.layout.activity_cart_items,HomePage.cartList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CartCheckOut.this,LinearLayoutManager.VERTICAL,false);
        rcyGioHang.setLayoutManager(layoutManager);
        rcyGioHang.setAdapter(cartAdapter);
    }

    private void mapping() {
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        rcyGioHang = findViewById(R.id.rcyGioHang);
        txtTongSanPham = findViewById(R.id.txtTongSanPham);
        tbTitle = findViewById(R.id.tbTitle);
    }

    public static void takeAllMoney() {
        int giaSP = 0;
        int tongTienSP = 0;
        for (int i = 0; i < HomePage.cartList.size(); i++) {
            giaSP = HomePage.cartList.get(i).getPriceCart();
            tongTienSP += giaSP;
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        String tongTien = numberFormat.format(tongTienSP);
        txtTongSanPham.setText("Tổng tiền: "+tongTien+"đ");
    }
}