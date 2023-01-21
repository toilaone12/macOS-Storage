package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fricashop.R;
import com.example.fricashop.adapter.PaymentAdapter;
import com.example.fricashop.face.CartInterface;
import com.example.fricashop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class TypePayment extends AppCompatActivity {
//    TextView txtBuyForCart,txtCreditCart;
    Toolbar tbPayment;
    RecyclerView rcyPayment;
    List<Payment> paList;
    PaymentAdapter paymentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_payment);

//        txtBuyForCart = findViewById(R.id.txtBuyForCart);
//        txtCreditCart = findViewById(R.id.txtCreditCart);
        rcyPayment = findViewById(R.id.rcyTypeOfPayment);
        tbPayment = findViewById(R.id.tbPayment);

        setSupportActionBar(tbPayment);
        getSupportActionBar().setTitle("Phương thức thanh toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tbPayment.setNavigationIcon(R.drawable.arrow_back);
        tbPayment.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        paList = new ArrayList<>();

        paList.add(new Payment(R.drawable.buy_for_cart,"Thanh toán khi nhận hàng","Miễn phí vận chuyển trong bán kính 3km" +
                ".Sau 3km, tính thêm 5.000đ/km."));
        paList.add(new Payment(R.drawable.credit_cart,"Thanh toán bằng thẻ ngân hàng","Chiết khẩu 1.5%/lần."));

        paymentAdapter = new PaymentAdapter(paList, TypePayment.this, R.layout.activity_type_of_payment, new CartInterface() {
            @Override
            public void paymentSuccess() {

            }

            @Override
            public Context getCartContext() {
                return null;
            }

            @Override
            public void onClickPaymentItems(Payment paList, int i) {
                Intent data = new Intent(TypePayment.this,CheckInfoPerson.class);
                data.putExtra("tenPT",paList.getName());
                setResult(RESULT_OK,data);
                finish();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(TypePayment.this,LinearLayoutManager.VERTICAL,false);
        rcyPayment.setLayoutManager(manager);
        rcyPayment.setAdapter(paymentAdapter);
    }
}