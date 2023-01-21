package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapp.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edtUserName, edtPassword;
    Button btnLogin, btnRegister;
    CheckBox ckbRemember;
    public static List<Person> personList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ánh xạ
        mapping();
        //xử lý sự kiện
        addAction();

    }

    private void addAction() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenTK = edtUserName.getText().toString();
                String MK = edtPassword.getText().toString();
                if(tenTK.equals("") || MK.equals("")){
                    Toast.makeText(Login.this, "Don't forgot to write username or password!", Toast.LENGTH_SHORT).show();
                }else{
                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.55.105/shopping/selectPerson.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("AA",response);
                            if(ckbRemember.isChecked()){
                                if(response.equals("True")){
                                    Intent data = new Intent(Login.this,MainActivity.class);
                                    startActivity(data);
                                }
                            }else{
                                if(response.equals("True")){
                                    Intent data = new Intent(Login.this,MainActivity.class);
                                    startActivity(data);
                                    edtUserName.setText("");
                                    edtPassword.setText("");
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this, "Error "+error, Toast.LENGTH_SHORT).show();
                            Log.d("Error",error+"");
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("sdt",tenTK);
                            map.put("matkhau",MK);
                            Log.d("AA",map+"");
                            return map;
                        }
                    };
                    queue.add(request);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(Login.this,Register.class);
                startActivity(register);
            }
        });
    }

    private void mapping() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        ckbRemember = findViewById(R.id.ckbRemember);
    }
}
