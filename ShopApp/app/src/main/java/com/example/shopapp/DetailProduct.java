package com.example.shopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapp.model.Cart;
import com.example.shopapp.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailProduct extends AppCompatActivity {
    ImageView imgDetailProduct;
    TextView txtNameDetail,txtPriceDetail,txtDescDetail;
    Button btnAddCart;
    EditText edtQuantityDetail;
    Toolbar tbDetailProduct;
    Product p;
    List<Product> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        //ánh xạ
        mapping();
        //xử lý tác vụ
        addAction();
        //xử lý toolbar
        actionToolBar();
        //đổ dữ liệu ra context
        getAllDetailProduct();
    }

    private void getAllDetailProduct() {
        Intent dataIdProduct = getIntent();
        int idProduct = dataIdProduct.getIntExtra("idProduct",0);
        RequestQueue queue = Volley.newRequestQueue(DetailProduct.this);
        String url = "http://192.168.55.105/shopping/selectIdProduct.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        p = new Product(object.getInt("id_product"), object.getInt("id_cate"), object.getString("name"),
                                object.getInt("price"),object.getInt("quantity"), object.getString("image"), object.getString("desc"));
                        list.add(p);
                        Picasso.get().load(object.getString("image")).into(imgDetailProduct);
                        txtNameDetail.setText(object.getString("name"));
                        Locale locale = new Locale("vi","VN");
                        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
                        txtPriceDetail.setText("Giá: "+currency.format(object.getInt("price")));
                        txtDescDetail.setText(object.getString("desc"));
                        edtQuantityDetail.setText(object.getInt("quantity")+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailProduct.this,"Error"+error,Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idProduct",idProduct+"");
                return map;
            }
        };
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart,menu);
        MenuItem itMenu = menu.findItem(R.id.itShopping);
        itMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent dataCart = new Intent(DetailProduct.this,CartCheckOut.class);
                startActivity(dataCart);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void actionToolBar() {
        setSupportActionBar(tbDetailProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbDetailProduct.setNavigationIcon(R.drawable.arrow_back);
        tbDetailProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataReturn = new Intent(DetailProduct.this,ProductView.class);
                dataReturn.putExtra("loaisp",p.getIdCate());
                startActivity(dataReturn);
            }
        });
    }

    private void addAction() {
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.cartList.size() > 0){
                    int soLuongMoi = Integer.parseInt(edtQuantityDetail.getText().toString());
                    boolean exist = false;
                    for(int i = 0; i < MainActivity.cartList.size(); i++){
                        if(MainActivity.cartList.get(i).getIdCart() == p.getIdProduct()){
                            MainActivity.cartList.get(i).setQuantityCart(soLuongMoi + MainActivity.cartList.get(i).getQuantityCart());
                            MainActivity.cartList.get(i).setPriceCart(p.getPrice() * MainActivity.cartList.get(i).getQuantityCart());
                            exist = true;
                        }

                    }
                    if(exist == false){
                        int id = p.getIdProduct();
                        String tenSanPham = p.getName();
                        String hinhAnhSanPham = p.getImage();
                        int giaSanPham = p.getPrice();
                        int soluong = Integer.parseInt(edtQuantityDetail.getText().toString());
                        int giaMoi = soluong * giaSanPham;
                        MainActivity.cartList.add(new Cart(id,tenSanPham,giaMoi,hinhAnhSanPham,soluong));
                    }
                }else{
                    int id = p.getIdProduct();
                    String tenSanPham = p.getName();
                    String hinhAnhSanPham = p.getImage();
                    int giaSanPham = p.getPrice();
                    int soluong = Integer.parseInt(edtQuantityDetail.getText().toString());
                    int giaMoi = soluong * giaSanPham;
                    MainActivity.cartList.add(new Cart(id,tenSanPham,giaMoi,hinhAnhSanPham,soluong));
                }
                Intent intent = new Intent(DetailProduct.this,CartCheckOut.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        imgDetailProduct = findViewById(R.id.imgDetailProduct);
        txtNameDetail = findViewById(R.id.txtNameDetail);
        txtPriceDetail = findViewById(R.id.txtPriceDetail);
        txtDescDetail = findViewById(R.id.txtDescDetail);
        edtQuantityDetail = findViewById(R.id.edtQuantityDetail);
        tbDetailProduct = findViewById(R.id.tbDetailProduct);
        btnAddCart = findViewById(R.id.btnAddCart);
    }
}