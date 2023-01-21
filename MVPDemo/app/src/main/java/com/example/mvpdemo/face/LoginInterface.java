package com.example.mvpdemo.face;

import android.content.Context;

public interface LoginInterface {
    void loginSuccess();
    void loginFail();
    void openHomePage();
    void showNotification();
    Context getContext();
}
