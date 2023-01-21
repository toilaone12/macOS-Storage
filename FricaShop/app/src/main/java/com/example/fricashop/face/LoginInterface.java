package com.example.fricashop.face;

import android.content.Context;

public interface LoginInterface {
    void loginSuccess();
    void loginFail();
    void goHomePage();
    void showNotification();
    Context getContext();
}
