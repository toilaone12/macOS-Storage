package com.example.shopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.shopapp.adapter.RcyProductAdapter;
import com.example.shopapp.model.OnClickListener;
import com.example.shopapp.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaptopView extends AppCompatActivity {
    RecyclerView rcySanPham;
    List<Product> list = new ArrayList<>();
    Toolbar tbBack;
    DrawerLayout dlProduct;
    RcyProductAdapter rcyProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_view);
        mapping();
        // xử lý toolBar
        actionToolBar();
        // xử lý danh sách
        getProductToIdCate();
        // xử lý adapter
        rcyProductAdapter = new RcyProductAdapter(LaptopView.this, R.layout.activity_rcy_product_items, list, new OnClickListener() {
            @Override
            public void onClickProductListener(int i, Product p) {
                Intent dataDetailProduct = new Intent(LaptopView.this,DetailProduct.class);
                dataDetailProduct.putExtra("idProduct",p.getIdProduct());
                System.out.println(p.getIdProduct()+"");
                startActivity(dataDetailProduct);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(LaptopView.this,LinearLayoutManager.VERTICAL,false);
        rcySanPham.setLayoutManager(layoutManager);
        rcySanPham.setAdapter(rcyProductAdapter);
    }

    private void getProductToIdCate() {
        Intent dataPhone = getIntent();
        int id = dataPhone.getIntExtra("loaisp",0);
        list.add(new Product("Điện thoại Xiaomi Note 11 8GB RAM/128GB",9000000,R.drawable.smartphone,"Sản phẩm bán chạy nhất thị trường hiện tại"));
        RequestQueue queue = Volley.newRequestQueue(LaptopView.this);
        String url_product = "http://192.168.55.105/shopping/selectIdCart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray array = new JSONArray(response);
                        for(int i = 0; i < array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            int idProduct = object.getInt("id_product");
                            list.add(new Product(idProduct,object.getInt("id_cate"),object.getString("name"),object.getInt("price"), object.getString("image"), object.getString("desc")));
                            rcyProductAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LaptopView.this,"Error"+error,Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idCate",id+"");
                return map;
            }
        };
        queue.add(stringRequest);
    }

    private void actionToolBar() {
        setSupportActionBar(tbBack);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbBack.setNavigationIcon(R.drawable.arrow_back);
        tbBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(LaptopView.this,MainActivity.class);
                startActivity(data);
            }
        });
    }

    private void mapping() {
        rcySanPham = findViewById(R.id.rcySanPham);
        tbBack = findViewById(R.id.tbBack);
        dlProduct = findViewById(R.id.dlProduct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_cart,menu);
        MenuItem itShopping = menu.findItem(R.id.itShopping);
        itShopping.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent dataCart = new Intent(LaptopView.this,CartCheckOut.class);
                startActivity(dataCart);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}