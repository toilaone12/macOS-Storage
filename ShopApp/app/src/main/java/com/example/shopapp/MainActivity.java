package com.example.shopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapp.adapter.MenuAdapter;
import com.example.shopapp.adapter.RcyHomePageAdapter;
import com.example.shopapp.model.Cart;
import com.example.shopapp.model.OnClickListener;
import com.example.shopapp.model.Product;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//
public class MainActivity extends AppCompatActivity {
    ViewFlipper vfSlider;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView rcyDanhSachSanPham;
    RcyHomePageAdapter productAdapter;
    MenuAdapter menuAdapter;
    ListView lvNavigation;
    List<com.example.shopapp.model.Menu> menus = new ArrayList<>();
    List<Product> list = new ArrayList<>();
    public static List<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
        mapping();
        //xử lý toolBar
        actionToolBar();
        //xử lý navigation View
        //xử lý view Flipper
        vfSlider.startFlipping();
        vfSlider.setFlipInterval(2000);
//
        getAllProduct();
        getAllMenu();
        getAllCart();
        // xử lý adapter

        productAdapter = new RcyHomePageAdapter(MainActivity.this, R.layout.activity_rcy_home_page, list, new OnClickListener() {
            @Override
            public void onClickProductListener(int i, Product p) {
                Intent dataProduct = new Intent(MainActivity.this,DetailProduct.class);
                dataProduct.putExtra("idProduct",p.getIdProduct());
                startActivity(dataProduct);
            }
        });
        LinearLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
        rcyDanhSachSanPham.setLayoutManager(manager);
        rcyDanhSachSanPham.setAdapter(productAdapter);
    }

    private void getAllCart() {
        if(cartList != null){

        }else{
            cartList = new ArrayList<>();
        }
    }

    private void getAllMenu() {

        String url = "http://192.168.55.105/shopping/selectCate.php";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject objectMenu = response.getJSONObject(i);
//                        com.example.shopapp.model.Menu mContact = null, mDetail = null;
//                        if(objectMenu.getInt("id") == 4){
//                            mContact = new com.example.shopapp.model.Menu(objectMenu.getInt("id"),objectMenu.getString("name"), objectMenu.getString("image"));
//                        }else if(objectMenu.getInt("id") == 5){
//                            mDetail = new com.example.shopapp.model.Menu(objectMenu.getInt("id"),objectMenu.getString("name"), objectMenu.getString("image"));
//                        }else{
                             menus.add(new com.example.shopapp.model.Menu(objectMenu.getInt("id"),objectMenu.getString("name"), objectMenu.getString("image")));
//                        }
//                        menus.add(mContact);
//                        menus.add(mDetail);
                        menuAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        RequestQueue queueMenu = Volley.newRequestQueue(MainActivity.this);
        queueMenu.add(arrayRequest);
//        menus.add(new com.example.shopapp.model.Menu(R.drawable.homepage,"Trang chủ"));
//        menus.add(new com.example.shopapp.model.Menu(R.drawable.smartphone,"Điện thoại"));
//        menus.add(new com.example.shopapp.model.Menu(R.drawable.laptop1,"Laptop"));
//        menus.add(new com.example.shopapp.model.Menu(R.drawable.contact,"Liên hệ"));
//        menus.add(new com.example.shopapp.model.Menu(R.drawable.detail,"Thông tin"));
//        menus.add(new com.example.shopapp.model.Menu(R.drawable.logout,"Đăng xuất"));

        menuAdapter = new MenuAdapter(menus,MainActivity.this,R.layout.activity_menu_adapter);
        lvNavigation.setAdapter(menuAdapter);

        lvNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intentPhone = new Intent(MainActivity.this,ProductView.class);
                        intentPhone.putExtra("loaisp",menus.get(i-1).getId());
                        startActivity(intentPhone);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intentLaptop = new Intent(MainActivity.this,LaptopView.class);
                        intentLaptop.putExtra("loaisp",menus.get(i-1).getId());
                        startActivity(intentLaptop);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
//                        Intent logOut = new Intent(MainActivity.this,Login.class);
//                        startActivity(logOut);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_cart,menu);
        MenuItem itShopping = menu.findItem(R.id.itShopping);
        itShopping.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent dataCart = new Intent(MainActivity.this,CartCheckOut.class);
                startActivity(dataCart);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void mapping() {
        vfSlider = findViewById(R.id.vfSlider);
        rcyDanhSachSanPham = findViewById(R.id.rcyDanhSachSanPham);
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        lvNavigation = findViewById(R.id.lvNavigation);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getAllProduct() {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://192.168.55.105/shopping/select.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Success",response+"");
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject object = response.getJSONObject(i);
                        list.add(new Product(object.getInt("id_product"),object.getInt("id_cate"),object.getString("name"),object.getInt("price"), object.getString("image"), object.getString("desc")));
                        productAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queue.add(request);
    }
}