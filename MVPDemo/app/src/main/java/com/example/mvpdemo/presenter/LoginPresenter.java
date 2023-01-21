package com.example.mvpdemo.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mvpdemo.face.LoginInterface;
import com.example.mvpdemo.face.VolleyCallBack;
import com.example.mvpdemo.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginPresenter {
    LoginInterface loginInterface;
    VolleyCallBack volleyCallBack;
    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }


    public boolean checkLogin(User u){
        //kết nối db để check nó có tồn tại hay k
        if(u.getUserName().equalsIgnoreCase("admin") && u.getPassWord().equalsIgnoreCase("admin")){
            loginInterface.loginSuccess();
            loginInterface.openHomePage();
            return true;
        }else{
            loginInterface.loginFail();
            loginInterface.showNotification();
        }
        return false;
    }
    public void getUserList(final VolleyCallBack volleyCallBack){

        RequestQueue requestQueue = Volley.newRequestQueue(loginInterface.getContext());
        JsonObjectRequest arrayRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.55.101/khoahoc.json", null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> list;
                try {
                    list = new ArrayList<>();
                    String ten = response.getString("khoahoc");
                    String noiHoc = response.getString("noihoc");
                    String web = response.getString("website");
                    String fanpage = response.getString("fanpage");
                    String logo = response.getString("logo");
                    list.add(ten+" - "+noiHoc+" - "+web+" - "+fanpage+" - "+logo);
                    volleyCallBack.onSuccess(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error!"+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        requestQueue.add(arrayRequest);
    }

}
