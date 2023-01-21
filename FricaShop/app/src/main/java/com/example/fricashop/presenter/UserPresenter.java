package com.example.fricashop.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.HomePage;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.face.VolleyCallBack;
import com.example.fricashop.model.User;
import com.example.fricashop.view.CheckInfoPerson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserPresenter {
    LoginInterface loginInterface;
    VolleyCallBack volleyCallBack;
    Pattern p;
    Matcher m;
    public static List<User> userList;
    public final static String IP_NETWORK = "192.168.43.42";
    public final static String IP_WIFI = "192.168.55.102";

    public UserPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    public void getUserList(final VolleyCallBack volleyCallBack){
        RequestQueue queue = Volley.newRequestQueue(loginInterface.getContext());
        String urlGetList = "http://"+IP_NETWORK+"/fricashop/webservice/selectUser.php";
        JsonArrayRequest object = new JsonArrayRequest(Request.Method.GET, urlGetList, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<User> userList1;
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject object1 = response.getJSONObject(i);
                        userList1 = new ArrayList<>();
                        User u = new User(object1.getInt("id"),object1.getString("image"),object1.getString("name"),
                                object1.getInt("age"),object1.getString("phoneNumber"), object1.getString("email"), object1.getString("address"),object1.getString("password"));
                        userList1.add(u);
                        volleyCallBack.onSuccess(userList1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queue.add(object);
    }
    public void checkLogin(User user){
        RequestQueue queue = Volley.newRequestQueue(loginInterface.getContext());
        String urlLogin = "http://"+IP_NETWORK+"/fricashop/webservice/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0; i < array.length(); i++){
                        userList = new ArrayList<>();
                        JSONObject object = array.getJSONObject(i);
                        User u = new User(object.getInt("id"),object.getString("image"),object.getString("name"),
                                object.getInt("age"),object.getString("phoneNumber"), object.getString("email"), object.getString("address"),object.getString("password"));
                        userList.add(u);
//                        volleyCallBack.onSuccess(userList);
                        Log.d("CCC",userList+"");
                        if(userList.equals("")){
                            loginInterface.loginFail();
                            loginInterface.showNotification();
                        }else{
                            loginInterface.loginSuccess();
                            loginInterface.goHomePage();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("sdt", user.getPhoneNumber());
                map.put("matKhau", user.getPassword());
                return map;
            }
        };
        queue.add(request);
    }

    public void changePassword(User user){
        RequestQueue queueUpdate = Volley.newRequestQueue(loginInterface.getContext());
        String urlUpdate = "http://"+IP_NETWORK+"/fricashop/webservice/changePass.php";
        StringRequest requestUpdate = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    loginInterface.loginSuccess();
                }else{
                    loginInterface.loginFail();
                    loginInterface.showNotification();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("tenTK",user.getPhoneNumber());
                map.put("matKhau",user.getPassword());
                return map;
            }
        };
        queueUpdate.add(requestUpdate);
    }
    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] image = baos.toByteArray();
        String encodeImage = Base64.encodeToString(image,Base64.DEFAULT);
        return encodeImage;
    }
    public void imagePersonal(Bitmap bm, int idPerson){
        String POST_IMAGE = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/updateImage.php";
        StringRequest request = new StringRequest(Request.Method.POST, POST_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject();
                    if(object.getString("status").equals("OK")){
                        loginInterface.goHomePage();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("er",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("image",imageToString(bm));
                params.put("id",idPerson+"");
                return params;
            }
        };
        Volley.newRequestQueue(loginInterface.getContext()).add(request);
    }

    public void registerSuccess(String phoneNumber, String password){
        RequestQueue queueRegister = Volley.newRequestQueue(loginInterface.getContext());
        String urlRegister = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/register.php";
        StringRequest requestRegister = new StringRequest(Request.Method.POST, urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("S")){
                    loginInterface.loginSuccess();
                }else if(response.equals("F")){
                    loginInterface.loginFail();
                    loginInterface.showNotification();
                }
//                Toast.makeText(loginInterface.getContext(), ""+response, Toast.LENGTH_SHORT).show();
//                Log.d("response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("er",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("taikhoan",phoneNumber);
                params.put("matkhau",password);
                return params;
            }
        };
        queueRegister.add(requestRegister);
    }
}
