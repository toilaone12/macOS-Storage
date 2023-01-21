package com.example.fricashop.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.R;
import com.example.fricashop.presenter.UserPresenter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ChangeEmail extends AppCompatActivity {
    TextInputEditText edtChangeEmail;
    Toolbar tbChangeEmail;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        mapping();
        actionToolBar();
        takeDataEmail();
    }

    private void takeDataEmail() {
        Intent dataEmail = getIntent();
        String email = dataEmail.getStringExtra("email");
        id = dataEmail.getIntExtra("id",0);
        edtChangeEmail.setText(email);
    }

    private void actionToolBar() {
        setSupportActionBar(tbChangeEmail);
        tbChangeEmail.setTitle("Thay đổi Email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbChangeEmail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        edtChangeEmail = findViewById(R.id.edtChangeEmail);
        tbChangeEmail = findViewById(R.id.tbChangeEmail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        MenuItem itSave = menu.findItem(R.id.itSave);
        itSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                RequestQueue queue = Volley.newRequestQueue(ChangeEmail.this);
                StringRequest request = new StringRequest(Request.Method.POST, "http://" + UserPresenter.IP_NETWORK + "/fricashop/webservice/changeEmail.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("aaaaa",response);
                        if(response.equals("1")){
                            Intent newData = new Intent();
                            String emailMoi = edtChangeEmail.getText().toString();
                            newData.putExtra("emailMoi",emailMoi);
                            setResult(Activity.RESULT_OK,newData);
                            finish();
                        }else{
                            Toast.makeText(ChangeEmail.this, "Thay đổi email không thành công!", Toast.LENGTH_SHORT).show();
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
                        Map<String, String> map = new HashMap<>();
                        map.put("email",edtChangeEmail.getText().toString());
                        map.put("id",id+"");
                        return map;
                    }
                };
                queue.add(request);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}