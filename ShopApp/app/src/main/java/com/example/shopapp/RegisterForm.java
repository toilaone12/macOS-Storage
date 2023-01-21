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

import java.util.HashMap;
import java.util.Map;

public class RegisterForm extends AppCompatActivity {
    EditText edtPasswordRegister,edtCheckPasswordRegister;
    Button btnAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        mapping();

        addAction();
    }

    private void addAction() {
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edtPasswordRegister.getText().toString();
                String rePassword = edtCheckPasswordRegister.getText().toString();
                if(rePassword.equals(password)){
                    RequestQueue queue = Volley.newRequestQueue(RegisterForm.this);
                    String url = "http://192.168.55.105/shopping/insertRegister.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("A",response);
                            if(response.equals("1")){
                                Toast.makeText(RegisterForm.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent dataLogin = new Intent(RegisterForm.this,Login.class);
                                startActivity(dataLogin);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterForm.this, "Error"+error, Toast.LENGTH_SHORT).show();
                            Log.d("Error",error+"");
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("sdt",phoneNumber);
                            map.put("matkhau",password);
                            return map;
                        }
                    };
                    queue.add(request);
                }else{
                    Toast.makeText(RegisterForm.this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mapping() {
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtCheckPasswordRegister = findViewById(R.id.edtCheckPasswordRegister);
        btnAccept = findViewById(R.id.btnAccept);
    }
}