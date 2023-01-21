package com.example.mvpdemo.presenter;

import com.example.mvpdemo.face.RegisterInterface;
import com.example.mvpdemo.model.User;

public class RegisterPresenter {
    RegisterInterface registerInterface;

    public RegisterPresenter(RegisterInterface registerInterface) {
        this.registerInterface = registerInterface;
    }

    public boolean checkRegister(User u){
        if(u.getUserName().equalsIgnoreCase("admin") && u.getPassWord().equalsIgnoreCase("admin")){
            registerInterface.showMessage("Đăng ký thành công!");
            registerInterface.goLogin();
            return true;
        }else{
            registerInterface.showNotification();
            registerInterface.showMessage("Đăng ký thất bại!");

        }
        return false;
    }
}
