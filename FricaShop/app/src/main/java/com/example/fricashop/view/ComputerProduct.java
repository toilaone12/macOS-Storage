package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fricashop.HomePage;
import com.example.fricashop.R;
import com.example.fricashop.adapter.ComputerAdapter;
import com.example.fricashop.adapter.ProductAdapter;
import com.example.fricashop.face.ComputerCallBack;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.face.OnClickProductItems;
import com.example.fricashop.face.ProductInterface;
import com.example.fricashop.face.VolleyCallBack;
//import com.example.fricashop.face.onClickItems;
import com.example.fricashop.model.Category;
import com.example.fricashop.model.Product;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.ProductPresenter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ComputerProduct extends AppCompatActivity implements ProductInterface {
    RecyclerView rcyComputer;
    Toolbar tbComputer;
    ComputerAdapter computerAdapter;
    List<Product> pList;
    DrawerLayout dlProduct;
    NavigationView nvProduct;
    EditText edtGiaMin, edtGiaMax, edtSoLuongMax,edtSoLuongMin;
    TextView txtLow,txtAverage,txtHigh,txtAsc,txtDesc,txtFromAtoZ,txtSoLuongTang,txtSoLuongGiam;
    Button btnReset, btnOK;
    ProductPresenter productPresenter = new ProductPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_product);

        mapping();

        getToolBar();

        addAction();

        getAllComputerProduct();

    }

    private void addAction() {
        txtLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtGiaMin.setText(0+"");
                edtGiaMax.setText(2000000+"");
            }
        });
        txtAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtGiaMin.setText(2000000+"");
                edtGiaMax.setText(5000000+"");
            }
        });
        txtHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtGiaMin.setText(5000000+"");
                edtGiaMax.setText(10000000+"");
            }
        });
        txtAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtGiaMin.getText().toString().equals("") && edtGiaMax.getText().toString().equals("")){
                    productPresenter.getPriceByRequest(0, 0, "ASC", new ProductInterface() {
                        @Override
                        public void takeOnSuccess(List<Product> p) {
                            pList = new ArrayList<>();
                            for (Product product: p) {
                                pList.add(product);
                            }
                            computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                @Override
                                public void setOnClickProductItemsListener(Product p,int i) {
                                    Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                    dataDetailProduct.putExtra("id",p.getId());
                                    dataDetailProduct.putExtra("tenSP",p.getName());
                                    startActivity(dataDetailProduct);
                                }
                            });
                            LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                            rcyComputer.setLayoutManager(manager);
                            rcyComputer.setAdapter(computerAdapter);
                        }

                        @Override
                        public Context getContext() {
                            return ComputerProduct.this;
                        }
                    });
//                    Toast.makeText(ComputerProduct.this, "Đã vào đây", Toast.LENGTH_SHORT).show();
                }else{
                    if(Integer.parseInt(edtGiaMin.getText().toString()) >= Integer.parseInt(edtGiaMax.getText().toString())){
                        Toast.makeText(ComputerProduct.this, "Giá cao nhất phải lớn hơn giá thấp nhất", Toast.LENGTH_SHORT).show();
                    }else{
                        productPresenter.getPriceByRequest(Integer.parseInt(edtGiaMin.getText().toString()), Integer.parseInt(edtGiaMax .getText().toString()), "ASC", new ProductInterface() {
                            @Override
                            public void takeOnSuccess(List<Product> p) {
                                pList = new ArrayList<>();
                                for (Product product: p) {
                                    pList.add(product);
                                }
                                computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                    @Override
                                    public void setOnClickProductItemsListener(Product p,int i) {
                                        Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                        dataDetailProduct.putExtra("id",p.getId());
                                        dataDetailProduct.putExtra("tenSP",p.getName());
                                        startActivity(dataDetailProduct);
                                    }
                                });
                                LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                                rcyComputer.setLayoutManager(manager);
                                rcyComputer.setAdapter(computerAdapter);
                            }

                            @Override
                            public Context getContext() {
                                return ComputerProduct.this;
                            }
                        });
                    }

                }
                dlProduct.closeDrawer(GravityCompat.END);
            }
        });
        txtDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtGiaMin.getText().toString().equals("") && edtGiaMax.getText().toString().equals("")){
                    productPresenter.getPriceByRequest(0, 0, "DESC", new ProductInterface() {
                        @Override
                        public void takeOnSuccess(List<Product> p) {
                            pList = new ArrayList<>();
                            for (Product product: p) {
                                pList.add(product);
                            }
                            computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                @Override
                                public void setOnClickProductItemsListener(Product p,int i) {
                                    Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                    dataDetailProduct.putExtra("id",p.getId());
                                    dataDetailProduct.putExtra("tenSP",p.getName());
                                    startActivity(dataDetailProduct);
                                }
                            });
                            LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                            rcyComputer.setLayoutManager(manager);
                            rcyComputer.setAdapter(computerAdapter);
                        }

                        @Override
                        public Context getContext() {
                            return ComputerProduct.this;
                        }
                    });
//                    Toast.makeText(ComputerProduct.this, "Đã vào đây", Toast.LENGTH_SHORT).show();
                }else{
                    if(Integer.parseInt(edtGiaMin.getText().toString()) >= Integer.parseInt(edtGiaMax.getText().toString())){
                        Toast.makeText(ComputerProduct.this, "Giá cao nhất phải lớn hơn giá thấp nhất", Toast.LENGTH_SHORT).show();
                    }else{
                        productPresenter.getPriceByRequest(Integer.parseInt(edtGiaMin.getText().toString()), Integer.parseInt(edtGiaMax .getText().toString()), "DESC", new ProductInterface() {
                            @Override
                            public void takeOnSuccess(List<Product> p) {
                                pList = new ArrayList<>();
                                for (Product product: p) {
                                    pList.add(product);
                                }
                                computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                    @Override
                                    public void setOnClickProductItemsListener(Product p,int i) {
                                        Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                        dataDetailProduct.putExtra("id",p.getId());
                                        dataDetailProduct.putExtra("tenSP",p.getName());
                                        startActivity(dataDetailProduct);
                                    }
                                });
                                LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                                rcyComputer.setLayoutManager(manager);
                                rcyComputer.setAdapter(computerAdapter);
                            }

                            @Override
                            public Context getContext() {
                                return ComputerProduct.this;
                            }
                        });
                    }
                }
                dlProduct.closeDrawer(GravityCompat.END);
            }
        });
        txtFromAtoZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtGiaMin.getText().toString().equals("") && edtGiaMax.getText().toString().equals("")){
                    productPresenter.getPriceByRequest(0, 0, "kytu", new ProductInterface() {
                        @Override
                        public void takeOnSuccess(List<Product> p) {
                            pList = new ArrayList<>();
                            for (Product product: p) {
                                pList.add(product);
                            }
                            computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                @Override
                                public void setOnClickProductItemsListener(Product p,int i) {
                                    Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                    dataDetailProduct.putExtra("id",p.getId());
                                    dataDetailProduct.putExtra("tenSP",p.getName());
                                    startActivity(dataDetailProduct);
                                }
                            });
                            LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                            rcyComputer.setLayoutManager(manager);
                            rcyComputer.setAdapter(computerAdapter);
                        }

                        @Override
                        public Context getContext() {
                            return ComputerProduct.this;
                        }
                    });
//                    Toast.makeText(ComputerProduct.this, "Đã vào đây", Toast.LENGTH_SHORT).show();
                }else{
                    if(Integer.parseInt(edtGiaMin.getText().toString()) >= Integer.parseInt(edtGiaMax.getText().toString())){
                        Toast.makeText(ComputerProduct.this, "Giá cao nhất phải lớn hơn giá thấp nhất", Toast.LENGTH_SHORT).show();
                    }else{
                        productPresenter.getPriceByRequest(Integer.parseInt(edtGiaMin.getText().toString()), Integer.parseInt(edtGiaMax .getText().toString()), "kytu", new ProductInterface() {
                            @Override
                            public void takeOnSuccess(List<Product> p) {
                                pList = new ArrayList<>();
                                for (Product product: p) {
                                    pList.add(product);
                                }
                                computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                    @Override
                                    public void setOnClickProductItemsListener(Product p,int i) {
                                        Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                        dataDetailProduct.putExtra("id",p.getId());
                                        dataDetailProduct.putExtra("tenSP",p.getName());
                                        startActivity(dataDetailProduct);
                                    }
                                });
                                LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                                rcyComputer.setLayoutManager(manager);
                                rcyComputer.setAdapter(computerAdapter);
                            }

                            @Override
                            public Context getContext() {
                                return ComputerProduct.this;
                            }
                        });
                    }
                }
                dlProduct.closeDrawer(GravityCompat.END);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoLuongMax.setText("");
                edtSoLuongMin.setText("");
                edtGiaMax.setText("");
                edtGiaMin.setText("");
                dlProduct.closeDrawer(GravityCompat.END);

            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtGiaMin.getText().toString().equals("") && edtGiaMax.getText().toString().equals("")
                        && edtSoLuongMin.getText().toString().equals("") && edtSoLuongMax.getText().toString().equals("")){
                    Toast.makeText(ComputerProduct.this, "Chưa có dữ liệu tìm kiếm!", Toast.LENGTH_SHORT).show();
                }else if(!edtGiaMin.getText().toString().equals("") && !edtGiaMax.getText().toString().equals("") && edtSoLuongMin.getText().toString().equals("") && edtSoLuongMax.getText().toString().equals("")){
                    if(Integer.parseInt(edtGiaMin.getText().toString()) >= Integer.parseInt(edtGiaMax.getText().toString())){
                        Toast.makeText(ComputerProduct.this, "Giá cao nhất phải lớn hơn giá thấp nhất", Toast.LENGTH_SHORT).show();
                    }else{
                        productPresenter.getPriceByRequest(Integer.parseInt(edtGiaMin.getText().toString()), Integer.parseInt(edtGiaMax .getText().toString()), "chon", new ProductInterface() {
                            @Override
                            public void takeOnSuccess(List<Product> p) {
                                pList = new ArrayList<>();
                                for (Product product: p) {
                                    pList.add(product);
                                }
                                computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                    @Override
                                    public void setOnClickProductItemsListener(Product p,int i) {
                                        Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                        dataDetailProduct.putExtra("id",p.getId());
                                        dataDetailProduct.putExtra("tenSP",p.getName());
                                        startActivity(dataDetailProduct);
                                    }
                                });
                                LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                                rcyComputer.setLayoutManager(manager);
                                rcyComputer.setAdapter(computerAdapter);
                            }

                            @Override
                            public Context getContext() {
                                return ComputerProduct.this;
                            }
                        });
                    }
                }else if(edtGiaMax.getText().toString().equals("") && edtGiaMin.getText().toString().equals("") && !edtSoLuongMin.getText().toString().equals("") && !edtSoLuongMax.getText().toString().equals("")){
                    int slMin = Integer.parseInt(edtSoLuongMin.getText().toString());
                    int slMax = Integer.parseInt(edtSoLuongMax.getText().toString());
                    if(slMin >= slMax){
                        Toast.makeText(ComputerProduct.this, "Số lượng phải từ nhỏ đến lớn", Toast.LENGTH_SHORT).show();
                    }else{
                        productPresenter.getRequest(0, 0, slMin, slMax, new ProductInterface() {
                            @Override
                            public void takeOnSuccess(List<Product> p) {
                                pList = new ArrayList<>();
                                for (Product product: p) {
                                    pList.add(product);
                                }
                                computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                    @Override
                                    public void setOnClickProductItemsListener(Product p,int i) {
                                        Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                        dataDetailProduct.putExtra("id",p.getId());
                                        dataDetailProduct.putExtra("tenSP",p.getName());
                                        startActivity(dataDetailProduct);
                                    }
                                });
                                LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                                rcyComputer.setLayoutManager(manager);
                                rcyComputer.setAdapter(computerAdapter);
                            }

                            @Override
                            public Context getContext() {
                                return ComputerProduct.this;
                            }
                        });
                    }
                }else{
                    int giaMin = Integer.parseInt(edtGiaMin.getText().toString());
                    int giaMax = Integer.parseInt(edtGiaMax.getText().toString());
                    int slMin = Integer.parseInt(edtSoLuongMin.getText().toString());
                    int slMax = Integer.parseInt(edtSoLuongMax.getText().toString());
                    if(slMin >= slMax && giaMin >= giaMax){
                        Toast.makeText(ComputerProduct.this, "Số lượng và giá phải từ nhỏ đến lớn", Toast.LENGTH_SHORT).show();
                    }else{
                        productPresenter.getRequest(giaMin, giaMax, slMin, slMax, new ProductInterface() {
                            @Override
                            public void takeOnSuccess(List<Product> p) {
                                pList = new ArrayList<>();
                                for (Product product: p) {
                                    pList.add(product);
                                }
                                computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                                    @Override
                                    public void setOnClickProductItemsListener(Product p,int i) {
                                        Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                                        dataDetailProduct.putExtra("id",p.getId());
                                        dataDetailProduct.putExtra("tenSP",p.getName());
                                        startActivity(dataDetailProduct);
                                    }
                                });
                                LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                                rcyComputer.setLayoutManager(manager);
                                rcyComputer.setAdapter(computerAdapter);
                            }

                            @Override
                            public Context getContext() {
                                return ComputerProduct.this;
                            }
                        });
                    }
                }
                dlProduct.closeDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem itSearch = menu.findItem(R.id.itSearch);
        SearchView searchView = (SearchView) itSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Product> list = new ArrayList<>();
                for (Product p: pList) {
                    if(p.getName().toLowerCase().contains(s.toLowerCase())){
                        list.add(p);
                    }

                }
                if(list.isEmpty()){
                    Toast.makeText(ComputerProduct.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                }else{
                    computerAdapter.filterList(list);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void getAllComputerProduct() {
        Intent data = getIntent();
        int id = data.getIntExtra("id",0);
        ProductPresenter productPresenter = new ProductPresenter(this);
        productPresenter.getComputerProduct(new ComputerCallBack() {
            @Override
            public void onSuccessComputerProduct(List<Product> list) {
                pList = new ArrayList<>();
//                Product p = new Product(1,1,"Máy tính",2,30000,"","");
                for (Product pr: list){
                    pList.add(pr);
                }
//                pList.add(p);
                computerAdapter = new ComputerAdapter(ComputerProduct.this, R.layout.activity_computer_items, pList, new OnClickProductItems() {
                    @Override
                    public void setOnClickProductItemsListener(Product p,int i) {
                        Intent dataDetailProduct = new Intent(ComputerProduct.this,DetailProduct.class);
                        dataDetailProduct.putExtra("id",p.getId());
                        dataDetailProduct.putExtra("tenSP",p.getName());
                        startActivity(dataDetailProduct);
                    }
                });
                LinearLayoutManager manager = new GridLayoutManager(ComputerProduct.this,2,LinearLayoutManager.VERTICAL,false);
                rcyComputer.setLayoutManager(manager);
                rcyComputer.setAdapter(computerAdapter);
            }
        }, id);
    }

    private void getToolBar() {
        Intent data = getIntent();
        String name = data.getStringExtra("name");
        setSupportActionBar(tbComputer);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbComputer.setNavigationIcon(null);
        tbComputer.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                dlProduct.openDrawer(GravityCompat.END);
            }
        });
    }

    private void mapping() {
        rcyComputer = findViewById(R.id.rcyComputer);
        tbComputer = findViewById(R.id.tbComputer);
        dlProduct = findViewById(R.id.dlProduct);
        nvProduct = findViewById(R.id.nvProduct);
        edtGiaMin = findViewById(R.id.edtGiaMin);
        edtGiaMax = findViewById(R.id.edtGiaMax);
        txtLow = findViewById(R.id.txtLow);
        txtAverage = findViewById(R.id.txtAverage);
        txtHigh = findViewById(R.id.txtHigh);
        txtAsc = findViewById(R.id.txtAsc);
        txtDesc = findViewById(R.id.txtDesc);
        txtFromAtoZ = findViewById(R.id.txtFromAtoZ);
        edtSoLuongMax = findViewById(R.id.edtSoLuongMax);
        edtSoLuongMin = findViewById(R.id.edtSoLuongMin);
        txtSoLuongTang = findViewById(R.id.txtSlAsc);
        txtSoLuongGiam = findViewById(R.id.txtSlDesc);
        btnReset = findViewById(R.id.btnReset);
        btnOK = findViewById(R.id.btnOK);
    }

    @Override
    public Context getContext() {
        return ComputerProduct.this;
    }

    @Override
    public void takeOnSuccess(List<Product> p) {

    }
}