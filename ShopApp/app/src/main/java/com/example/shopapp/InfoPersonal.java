package com.example.shopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapp.model.Cart;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoPersonal extends AppCompatActivity {
    TextInputEditText edtNamePerson,edtPhonePerson,edtEmailPerson,edtAddressPerson;
    Button btnChapNhan, btnTroVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_personal);

        mapping();

        addAction();
    }

    private void addAction() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnChapNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = getIntent();
//                Cart c = (Cart) data.getSerializableExtra("gioHang");
                String tenKH = edtNamePerson.getText().toString();
                String sdt = edtPhonePerson.getText().toString();
                String email = edtEmailPerson.getText().toString();
                String diaChi = edtAddressPerson.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(InfoPersonal.this);
                String url = "http://192.168.55.105/shopping/insertPersonal.php";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("donhang","response là: "+response);
                        if(Integer.parseInt(response) > 0){
                            String url = "http://192.168.55.105/shopping/insertDetailCart.php";
                            RequestQueue queue1 = Volley.newRequestQueue(InfoPersonal.this);
                            StringRequest request1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    MainActivity.cartList.clear();
                                    Toast.makeText(getApplicationContext(), "Xử lý đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                                    Intent data = new Intent(InfoPersonal.this,MainActivity.class);
                                    startActivity(data);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray array = new JSONArray();
                                    for (int i = 0; i < MainActivity.cartList.size(); i++){
                                        JSONObject object = new JSONObject();
                                        try {
                                            object.put("tensanpham",MainActivity.cartList.get(i).getNameCart());
                                            object.put("giasp",MainActivity.cartList.get(i).getPriceCart());
                                            object.put("soluongsp",MainActivity.cartList.get(i).getQuantityCart());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        array.put(object);
                                    }
                                    Map<String,String> map = new HashMap<>();
                                    map.put("json",array.toString());
                                    return map;
                                }
                            };
                            queue1.add(request1);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InfoPersonal.this,"Error"+error,Toast.LENGTH_SHORT).show();
                        Log.d("Error",error+"");
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("tenKhachHang",tenKH);
                        map.put("sdt",sdt);
                        map.put("email",email);
                        map.put("diaChi",diaChi);
                        return map;
                    }
                };
                queue.add(request);
            }
        });
    }

    private void mapping() {
        edtNamePerson = findViewById(R.id.edtNamePerson);
        edtPhonePerson = findViewById(R.id.edtPhonePerson);
        edtEmailPerson = findViewById(R.id.edtEmailPerson);
        edtAddressPerson = findViewById(R.id.edtAddressPerson);
        btnChapNhan = findViewById(R.id.btnChapNhan);
        btnTroVe = findViewById(R.id.btnTroVe);
    }
}