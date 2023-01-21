package com.example.shopapp;

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

import com.example.shopapp.adapter.RcyCartCheckOut;
import com.example.shopapp.model.Cart;
import com.example.shopapp.model.Product;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartCheckOut extends AppCompatActivity {
    static List<Product> list ;
    RcyCartCheckOut rcyCartCheckOut;
    Button btnThanhToan,btnTiepTuc;
    static TextView txtTongSanPham;
    RecyclerView rcyGioHang;
    Toolbar tbTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcy_cart_checkout);
        //ánh xạ
        mapping();
        //đổ dữ liệu cho list
        getAllCartItems();
        //xử lý tool bar
        actionToolBar();
        rcyCartCheckOut = new RcyCartCheckOut(CartCheckOut.this,R.layout.activity_cart_items,MainActivity.cartList);
        LinearLayoutManager manager = new LinearLayoutManager(CartCheckOut.this, LinearLayoutManager.VERTICAL,false);
        rcyGioHang.setLayoutManager(manager);
        rcyGioHang.setAdapter(rcyCartCheckOut);
        //xử lý tổng tiền sp
        takeAllMoney();
        //xử lý sự kiện
        addAction();
    }

    private void addAction() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.cartList.size() > 0){
                    Intent dataPerson = new Intent(getApplicationContext(),InfoPersonal.class);
//                    dataPerson.putExtra("gioHang", (Serializable) MainActivity.cartList);
                    startActivity(dataPerson);
                }else{
                    Toast.makeText(getApplicationContext(), "Chưa có sản phẩm trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent turnBackHomePage = new Intent(CartCheckOut.this,MainActivity.class);
                startActivity(turnBackHomePage);
            }
        });
    }

    public static void takeAllMoney() {
        int tongTien = 0;
        int giaSP = 0;
//        System.out.println(list);
        for(int i = 0; i < MainActivity.cartList.size(); i++){
            giaSP = MainActivity.cartList.get(i).getPriceCart();
//            System.out.println(list.get(i).getQuantity());
            tongTien += giaSP;
//            System.out.println(tongTien);
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        txtTongSanPham.setText("Tổng tiền: "+currency.format(tongTien));
    }

    private void actionToolBar() {
        setSupportActionBar(tbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getAllCartItems() {
        list = new ArrayList<>();
        list.add(new Product("XiaoMi",19000000,2,""));
        list.add(new Product("XiaoMi",19000000,2,""));
        list.add(new Product("XiaoMi",19000000,3,""));
    }

    private void mapping() {
        rcyGioHang = findViewById(R.id.rcyGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        txtTongSanPham = findViewById(R.id.txtTongSanPham);
        tbTitle = findViewById(R.id.tbTitle);
    }
}