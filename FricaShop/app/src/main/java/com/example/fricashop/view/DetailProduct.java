package com.example.fricashop.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.fricashop.HomePage;
import com.example.fricashop.R;
import com.example.fricashop.face.DetailCallBack;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.face.VolleyCallBack;
import com.example.fricashop.model.Cart;
import com.example.fricashop.model.Category;
import com.example.fricashop.model.Product;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.ProductPresenter;
import com.example.fricashop.presenter.UserPresenter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailProduct extends AppCompatActivity implements LoginInterface, YouTubePlayer.OnInitializedListener {
    ImageView imgDetailProduct;
    TextView txtNameDetail,txtPriceDetail,txtDescDetail;
    Button btnAddCart;
    EditText edtQuantityDetail;
//    YouTubePlayerView ytbDetailProduct;
    YouTubePlayerFragment frag;
    Toolbar tbDetailProduct;
    public static Product p;
    ProductPresenter productPresenter;
    public static final String API_KEY = "AIzaSyCLrmoFXiqWnuHGCXQE23CV-CYhjUZMRdU";
    List<Product> pList = new ArrayList<>();
    Intent takeDetailProductItems;

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
        takeDetailProductItems = getIntent();
        int id = takeDetailProductItems.getIntExtra("id",0);

        productPresenter = new ProductPresenter(this);
        productPresenter.getDetailProduct(new DetailCallBack() {
            @Override
            public void takeDetailProduct(List<Product> productList) {
                for (Product pDetail : productList) {
                    pList.add(pDetail);
//                    Log.d("AAA",pList+"");
                }
                for (int i = 0; i < pList.size(); i++){
                    p = pList.get(i);
                    txtNameDetail.setText(p.getName());
                    Locale locale = new Locale("vi","VN");
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
                    String giaTien = numberFormat.format(p.getPrice());
                    txtPriceDetail.setText("Giá: "+giaTien+"đ");
                    txtDescDetail.setText(p.getDesc());
                    Picasso.get().load(p.getImage()).into(imgDetailProduct);
                    edtQuantityDetail.setText(1+"");
                    frag.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.loadVideo(p.getLink_ytb());
                            youTubePlayer.play();
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            if(youTubeInitializationResult.isUserRecoverableError()){
                                frag.initialize(API_KEY,this);
                            }else{
                                Toast.makeText(DetailProduct.this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }, id);
    }

    private void actionToolBar() {
        takeDetailProductItems = getIntent();
        String ten = takeDetailProductItems.getStringExtra("tenSP");
        setSupportActionBar(tbDetailProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        tbDetailProduct.setNavigationIcon(R.drawable.arrow_back);
        getSupportActionBar().setTitle(ten);
        tbDetailProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent dataReturn = new Intent(DetailProduct.this, HomePage.class);
//                dataReturn.putExtra("loaisp",p.getCate_id());
//                startActivity(dataReturn);
                finish();
            }
        });
    }

    private void addAction() {
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomePage.cartList.size() > 0){
                    int newQuantityCart = Integer.parseInt(edtQuantityDetail.getText().toString());
                    boolean exist = false;
                    for (int i = 0; i < HomePage.cartList.size(); i++) {
                        if(HomePage.cartList.get(i).getIdCart() == p.getId()){
                            if((newQuantityCart + HomePage.cartList.get(i).getQuantityCart()) <= p.getQuantity()){
                                HomePage.cartList.get(i).setQuantityCart(newQuantityCart + HomePage.cartList.get(i).getQuantityCart());
                                HomePage.cartList.get(i).setPriceCart(p.getPrice() * HomePage.cartList.get(i).getQuantityCart());
                                Intent dataCart = new Intent(DetailProduct.this,CartCheckOut.class);
                                startActivity(dataCart);
                            }else{
                                Toast.makeText(DetailProduct.this, "Chỉ còn tồn "+p.getQuantity()+" sản phẩm!", Toast.LENGTH_SHORT).show();
                            }
                            exist = true;
                        }

                    }
                    if(exist == false){
                        int idCart = p.getId();
                        String nameCart = p.getName();
                        String imageCart = p.getImage();
                        int priceCart = p.getPrice();
                        int quantityCart = Integer.parseInt(edtQuantityDetail.getText().toString());
                        int newPriceCart = priceCart * quantityCart;
                        if(quantityCart <= p.getQuantity()){
                            HomePage.cartList.add(new Cart(idCart,nameCart,newPriceCart,imageCart,quantityCart));
                            Intent dataCart = new Intent(DetailProduct.this,CartCheckOut.class);
                            startActivity(dataCart);
                        }else{
                            Toast.makeText(DetailProduct.this, "Chỉ còn tồn "+p.getQuantity()+" sản phẩm!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else{
                    int idCart = p.getId();
                    String nameCart = p.getName();
                    String imageCart = p.getImage();
                    int priceCart = p.getPrice();
                    int quantityCart = Integer.parseInt(edtQuantityDetail.getText().toString());

                    int newPriceCart = priceCart * quantityCart;
                    if(quantityCart <= p.getQuantity()){
                        HomePage.cartList.add(new Cart(idCart,nameCart,newPriceCart,imageCart,quantityCart));
                        Intent dataCart = new Intent(DetailProduct.this,CartCheckOut.class);
                        startActivity(dataCart);
                    }else{
                        Toast.makeText(DetailProduct.this, "Chỉ còn tồn "+p.getQuantity()+" sản phẩm!", Toast.LENGTH_SHORT).show();
                    }
                }
//                Intent dataCart = new Intent(DetailProduct.this,CartCheckOut.class);
//                startActivity(dataCart);
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
        frag =  (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public void goHomePage() {

    }

    @Override
    public void showNotification() {

    }

    @Override
    public Context getContext() {
        return DetailProduct.this;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}