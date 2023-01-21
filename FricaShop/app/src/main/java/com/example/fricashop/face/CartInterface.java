package com.example.fricashop.face;

import android.content.Context;

import com.example.fricashop.model.Payment;

import java.util.List;

public interface CartInterface {
    void paymentSuccess();
    Context getCartContext();
    void onClickPaymentItems(Payment paList,int i);
}
