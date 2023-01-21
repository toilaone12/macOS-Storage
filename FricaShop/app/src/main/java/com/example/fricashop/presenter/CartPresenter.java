package com.example.fricashop.presenter;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.HomePage;
import com.example.fricashop.face.CartInterface;
import com.example.fricashop.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CartPresenter {
    CartInterface cartInterface;
    public static final String URL_CART = "http://"+ UserPresenter.IP_NETWORK+"/fricashop/webservice/insertCart.php";
    public static final String URL_DETAIL_CART = "http://"+ UserPresenter.IP_NETWORK+"/fricashop/webservice/insertDetailCart.php";

    public CartPresenter(CartInterface cartInterface) {
        this.cartInterface = cartInterface;
    }

    public void paymentSuccess(String tenKH, String email, String diaChi, String sdt,String phuongThuc){
        StringRequest requestCart = new StringRequest(Request.Method.POST, URL_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AAAAAA","Dòng 39:"+response);
                if(response.equals("Lỗi")){
                    Toast.makeText(cartInterface.getCartContext(), "Có vấn đề về thông tin, yêu cầu quý khách kiểm tra thông tin lại 1 lần nữa!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(response) > 0){
                    Log.d("AAAAA",response);
                    StringRequest requestDetailCart = new StringRequest(Request.Method.POST, URL_DETAIL_CART, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Log.d("AAAAAA",response);
//                            if (response.equals("1")){
                                HomePage.cartList.clear();
                                cartInterface.paymentSuccess();
//                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            JSONArray array = new JSONArray();
                            for (int i = 0; i < HomePage.cartList.size(); i++){
                                try {
                                    JSONObject o = new JSONObject();
                                    o.put("id",response);
                                    o.put("image",HomePage.cartList.get(i).getImageCart());
                                    o.put("ten_san_pham",HomePage.cartList.get(i).getNameCart());
                                    o.put("so_luong",HomePage.cartList.get(i).getQuantityCart());
                                    o.put("gia_san_pham",HomePage.cartList.get(i).getPriceCart());
                                    array.put(o);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            params.put("json",array+"");
                            return params;
                        }
                    };
                    Volley.newRequestQueue(cartInterface.getCartContext()).add(requestDetailCart);
                }else{
                    Toast.makeText(cartInterface.getCartContext(), "Có vấn đề!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("ten_khach_hang",tenKH);
                map.put("email",email);
                map.put("sdt",sdt);
                map.put("dia_chi",diaChi);
                map.put("phuong_thuc_thanh_toan",phuongThuc);
                Log.d("AAAAAA",map+"");
                return map;
            }
        };
        Volley.newRequestQueue(cartInterface.getCartContext()).add(requestCart);
    }
}
