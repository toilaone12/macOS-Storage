package com.example.fricashop.face;

import android.content.Context;

import com.example.fricashop.model.Category;
import com.example.fricashop.model.Product;
import com.example.fricashop.model.Slider;
import com.example.fricashop.model.User;

import java.util.List;

public interface VolleyCallBack {
    void onSuccess(List<User> list);
    void takeSuccessProduct(List<Product> list);
    void takeAll(List<Category> list);
    void getSlider(List<Slider> list);
}
