package com.example.fricashop.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.UserPresenter;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ChangeFullName extends AppCompatActivity {
    TextInputEditText edtChangeUN;
    Toolbar tbChangeFullName;
    String hoTen;
    int id;
//    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_full_name);

        edtChangeUN = findViewById(R.id.edtChangUN);
        tbChangeFullName = findViewById(R.id.tbChangeFullName);

        Intent data = getIntent();
        String ten = data.getStringExtra("hoTen");
//        Log.d("ho")
        id = data.getIntExtra("id",0);
        edtChangeUN.setText(ten);
        setSupportActionBar(tbChangeFullName);
        tbChangeFullName.setTitle("Thay đổi thông tin");
//        tbChangeFullName.setOnMenuItemClickListener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        MenuItem itSave = menu.findItem(R.id.itSave);
        itSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                RequestQueue queue = Volley.newRequestQueue(ChangeFullName.this);
                StringRequest request = new StringRequest(Request.Method.POST, "http://" + UserPresenter.IP_NETWORK + "/fricashop/webservice/changeName.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("aaaaa",response);
                        if(response.equals("1")){
                            Intent newData = new Intent();
                            String hoTenMoi = edtChangeUN.getText().toString();
                            newData.putExtra("hoTenMoi",hoTenMoi);
                            setResult(Activity.RESULT_OK,newData);
                            finish();
                        }else{
                            Toast.makeText(ChangeFullName.this, "Thay đổi tên không thành công!", Toast.LENGTH_SHORT).show();
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
                        map.put("hoTen",edtChangeUN.getText().toString());
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