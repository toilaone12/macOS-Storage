package com.example.fricashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.fricashop.adapter.CategoryAdapter;
import com.example.fricashop.adapter.ProductAdapter;
import com.example.fricashop.face.CateInterface;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.face.OnClickProductItems;
import com.example.fricashop.face.PhoneCallBack;
import com.example.fricashop.face.VolleyCallBack;
import com.example.fricashop.face.onClickCateItems;
//import com.example.fricashop.face.onClickItems;
import com.example.fricashop.fragment.FeedBackFragment;
import com.example.fricashop.fragment.HomePageFragment;
import com.example.fricashop.fragment.NotificationFragment;
import com.example.fricashop.fragment.PersonalFragment;
import com.example.fricashop.model.Cart;
import com.example.fricashop.model.Category;
import com.example.fricashop.model.Product;
import com.example.fricashop.model.Slider;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.CatePresenter;
import com.example.fricashop.presenter.ProductPresenter;
import com.example.fricashop.presenter.UserPresenter;
import com.example.fricashop.view.AddressFrica;
import com.example.fricashop.view.CartCheckOut;
import com.example.fricashop.view.ComputerProduct;
import com.example.fricashop.view.ContactInformation;
import com.example.fricashop.view.DetailProduct;
import com.example.fricashop.view.FeedBack;
import com.example.fricashop.view.Login;
import com.example.fricashop.view.Personal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements LoginInterface, CateInterface, VolleyCallBack {
    ViewFlipper vfSlider;
    DrawerLayout drawerLayout;
    Toolbar tbHomePage;
    ActionBar toolbar;
    BottomNavigationView bnvBar;
    ProductAdapter productAdapter;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    Product p;
    public static List<Cart> cartList;
    RecyclerView rcyHomePage,rcyCategory,rcyPhoneCate,rcyLowCourses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();

        getAllCate();

        getAllProduct();

        getAverageCourses();

        getLowCourses();

        getAllCart();

        actionViewFlipper();

        actionToolbar();

        actionBottomNavigationView();

//        actionNavigationView();

        loadFragment(new HomePageFragment());
    }

    private void getLowCourses() {
        ProductPresenter productPresenter = new ProductPresenter(this);
        productPresenter.getLowCourses(new PhoneCallBack() {
            @Override
            public void takeSuccessPhone(List<Product> list) {
                List<Product> pList = new ArrayList<>();
                for (Product p : list) {
                    pList.add(p);
                }
                productAdapter = new ProductAdapter(HomePage.this, R.layout.activity_all_product, pList, new OnClickProductItems() {
                    @Override
                    public void setOnClickProductItemsListener(Product p, int id) {
                        Intent detailPhoneItems = new Intent(HomePage.this, DetailProduct.class);
                        p = pList.get(id);
                        detailPhoneItems.putExtra("id",p.getId());
                        detailPhoneItems.putExtra("tenSP",p.getName());
//                        Toast.makeText(HomePage.this, ""+product.getId(), Toast.LENGTH_SHORT).show();
                        startActivity(detailPhoneItems);
                    }
                });
                LinearLayoutManager layoutManager = new GridLayoutManager(HomePage.this,2,LinearLayoutManager.VERTICAL,false);
                rcyLowCourses.setLayoutManager(layoutManager);
                rcyLowCourses.setAdapter(productAdapter);
                rcyLowCourses.setHasFixedSize(false);
//                rcyPhoneCate.setNestedScrollingEnabled(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopping_cart,menu);
        MenuItem itShopping = menu.findItem(R.id.itShopping);
        itShopping.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent dataShopping = new Intent(HomePage.this, CartCheckOut.class);
                startActivity(dataShopping);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getAllCart() {
        if(cartList != null){

        }else{
            cartList = new ArrayList<>();
        }
    }

    private void getAverageCourses() {
        ProductPresenter productPresenter = new ProductPresenter(this);
        productPresenter.getPhoneForHomePage(new PhoneCallBack() {
            @Override
            public void takeSuccessPhone(List<Product> list) {
                List<Product> pList = new ArrayList<>();
                for (Product p : list) {
                    pList.add(p);
                }
                productAdapter = new ProductAdapter(HomePage.this, R.layout.activity_all_product, pList, new OnClickProductItems() {
                    @Override
                    public void setOnClickProductItemsListener(Product p, int id) {
                        Intent detailPhoneItems = new Intent(HomePage.this, DetailProduct.class);
                        p = pList.get(id);
                        detailPhoneItems.putExtra("id",p.getId());
                        detailPhoneItems.putExtra("tenSP",p.getName());
//                        Toast.makeText(HomePage.this, ""+product.getId(), Toast.LENGTH_SHORT).show();
                        startActivity(detailPhoneItems);
                    }
                });
                LinearLayoutManager layoutManager = new GridLayoutManager(HomePage.this,2,LinearLayoutManager.VERTICAL,false);
                rcyPhoneCate.setLayoutManager(layoutManager);
                rcyPhoneCate.setAdapter(productAdapter);
                rcyPhoneCate.setHasFixedSize(false);
//                rcyPhoneCate.setNestedScrollingEnabled(false);
            }
        });
    }

    private void getAllCate() {
        CatePresenter catePresenter = new CatePresenter(this);
        catePresenter.getAllCategory(new VolleyCallBack() {
            @Override
            public void onSuccess(List<User> list) {

            }

            @Override
            public void takeSuccessProduct(List<Product> list) {

            }

            @Override
            public void takeAll(List<Category> list) {
                categoryList = new ArrayList<>();
                for (Category cate: list) {
                    categoryList.add(cate);
                }
                categoryAdapter = new CategoryAdapter(HomePage.this, R.layout.activity_cate_items, categoryList, new onClickCateItems() {
                    @Override
                    public void setOnClickCate(Category cate, int i) {
                        Intent data = new Intent(HomePage.this,ComputerProduct.class);
                        data.putExtra("id",cate.getIdCate());
                        data.putExtra("name",cate.getName());
                        startActivity(data);
                    }

                });
                LinearLayoutManager manager = new GridLayoutManager(HomePage.this,1,LinearLayoutManager.HORIZONTAL,false);
                rcyCategory.setLayoutManager(manager);
                rcyCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void getSlider(List<Slider> list) {

            }
        });
    }

//    private void actionNavigationView() {
//        setSupportActionBar(tbHomePage);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        tbHomePage.setNavigationIcon(R.drawable.menu);
//        tbHomePage.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//    }

    private void getAllProduct() {
        ProductPresenter productPresenter = new ProductPresenter(this);
        productPresenter.getAllProduct(new VolleyCallBack() {
            @Override
            public void onSuccess(List<User> list) {

            }

            @Override
            public void takeSuccessProduct(List<Product> list) {
                List<Product> productList = new ArrayList<>();
                for (Product pr: list){
                    productList.add(pr);

                }
//                Toast.makeText(HomePage.this, ""+productList, Toast.LENGTH_SHORT).show();
                productAdapter = new ProductAdapter(HomePage.this, R.layout.activity_all_product, productList, new OnClickProductItems() {
                    @Override
                    public void setOnClickProductItemsListener(Product product,int id) {
                        Intent detailPhoneItems = new Intent(HomePage.this, DetailProduct.class);
                        product = productList.get(id);
                        detailPhoneItems.putExtra("id",product.getId());
                        detailPhoneItems.putExtra("tenSP",product.getName());
//                        Toast.makeText(HomePage.this, ""+product.getId(), Toast.LENGTH_SHORT).show();
                        startActivity(detailPhoneItems);
                    }
                });
                LinearLayoutManager layoutManager = new GridLayoutManager(HomePage.this,2,LinearLayoutManager.VERTICAL,false);
                rcyHomePage.setLayoutManager(layoutManager);
                rcyHomePage.setAdapter(productAdapter);
                rcyHomePage.setHasFixedSize(false);
//                rcyHomePage.setNestedScrollingEnabled(false);
            }

            @Override
            public void takeAll(List<Category> list) {

            }

            @Override
            public void getSlider(List<Slider> list) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void actionViewFlipper() {
        ProductPresenter p = new ProductPresenter(this);
        p.getSlider(new VolleyCallBack() {
            @Override
            public void onSuccess(List<User> list) {

            }

            @Override
            public void takeSuccessProduct(List<Product> list) {

            }

            @Override
            public void takeAll(List<Category> list) {

            }

            @Override
            public void getSlider(List<Slider> list) {
                for (int i = 0; i < list.size(); i++) {
                    Slider s = list.get(i);
                    ImageView imageView = new ImageView(HomePage.this);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.get().load(s.getImage()).into(imageView);
                    vfSlider.addView(imageView);
                }
            }
        });
        vfSlider.setAutoStart(true);
        vfSlider.startFlipping();
        vfSlider.setFlipInterval(2000);
    }

    private void actionBottomNavigationView() {
        toolbar = getSupportActionBar();
        bnvBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.nvHome:
                        toolbar.setTitle("Trang chủ");
                        fragment = new HomePageFragment();
                        Intent dataHomePage = new Intent(HomePage.this,HomePage.class);
                        startActivity(dataHomePage);
//                        Toast.makeText(HomePage.this, "Click vào Home!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nvFeedBack:
                        if(UserPresenter.userList == null){
                            Intent dataLogin = new Intent(HomePage.this, Login.class);
                            startActivity(dataLogin);
                        }else if(UserPresenter.userList.size() > 0){
                            toolbar.setTitle("FricaFeedBack");
                            fragment = new FeedBackFragment();
                            Intent dataFeedBack = new Intent(HomePage.this, FeedBack.class);
                            startActivity(dataFeedBack);
                        }

//                        Toast.makeText(HomePage.this, "Click vào FeedBack!", Toast.LENGTH_SHORT).show();
                        return true;
//                        break;
                    case R.id.nvNotification:
                        toolbar.setTitle("Thông tin liên hệ");
                        fragment = new NotificationFragment();
                        Intent dataNotification = new Intent(HomePage.this, ContactInformation.class);
                        startActivity(dataNotification);
//                        Toast.makeText(HomePage.this, "Click vào Notification!", Toast.LENGTH_SHORT).show();
                        return true;
//                        break;
                    case R.id.nvInformation:
                        Intent dataInfomation = new Intent(HomePage.this, AddressFrica.class);
                        startActivity(dataInfomation);
                        return true;
                    case R.id.nvPerson:
                        if(UserPresenter.userList == null){
                            Intent dataLogin = new Intent(HomePage.this, Login.class);
                            startActivity(dataLogin);
                        }else if(UserPresenter.userList.size() > 0){
                            toolbar.setTitle("Thông tin cá nhân");
                            fragment = new PersonalFragment();
                            Intent data = new Intent(HomePage.this, Personal.class);
                            startActivity(data);
                        }

//                        Toast.makeText(HomePage.this, "Click vào Personal!", Toast.LENGTH_SHORT).show();
                        return true;
//                        break;
                    default:
                        Toast.makeText(HomePage.this, "Không có gì!", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void actionToolbar() {
        setSupportActionBar(tbHomePage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbHomePage.setNavigationIcon(R.drawable.frica2);
        tbHomePage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Chào mừng bạn tới FricaShop", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mapping() {
        vfSlider = findViewById(R.id.vfSlider);
        drawerLayout = findViewById(R.id.drawerLayout);
        tbHomePage = findViewById(R.id.tbHomePage);
        bnvBar = findViewById(R.id.bnvBar);
        rcyHomePage = findViewById(R.id.rcyHomePage);
        rcyCategory = findViewById(R.id.rcyCategory);
        rcyPhoneCate = findViewById(R.id.rcyPhoneCate);
        rcyLowCourses = findViewById(R.id.rcyLowCourses);
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
        return HomePage.this;
    }

    @Override
    public void clickSuccess() {

    }

    @Override
    public void goProductByCategoryId() {

    }

    @Override
    public Context getContextCate() {
        return HomePage.this;
    }

    @Override
    public void onSuccess(List<User> list) {

    }

    @Override
    public void takeSuccessProduct(List<Product> list) {

    }

    @Override
    public void takeAll(List<Category> list) {

    }

    @Override
    public void getSlider(List<Slider> list) {

    }
}