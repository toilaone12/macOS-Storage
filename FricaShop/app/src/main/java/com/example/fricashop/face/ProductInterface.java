package com.example.fricashop.face;

import android.content.Context;

import com.example.fricashop.model.Product;

import java.util.List;

public interface ProductInterface {
    void takeOnSuccess(List<Product> p);
    Context getContext();
}
