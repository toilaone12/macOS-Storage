package com.example.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TechnologyProductAdapter adapter;
    List<Books> booksList1 = new ArrayList<>();
    ListView lvDSach;
    BookVM bookVM = new BookVM();
    MyDatabase database;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new MyDatabase(this);
        lvDSach = findViewById(R.id.lvDSach);

        adapter = new TechnologyProductAdapter(booksList1, MainActivity.this, R.layout.list_items, new OnDeleteItemsClickListener() {
            @Override
            public void OnDeleteItemsClickListener(int i, Books books) {
                if (bookVM.deleteSQL(books.getId()) > 0) {
                    booksList1.remove(i);
                    Toast.makeText(MainActivity.this, "Xóa thành công " + books.getName(), Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Xóa không thành công " + books.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new OnUpdateItemsClickListener() {
            @Override
            public void onUpdateItemsClickListener(int i, Books books) {
                index = i;
                books = booksList1.get(i);
                Intent data = new Intent(MainActivity.this,UpdateBookActivity.class);
                data.putExtra("maSach",books.getId());
                data.putExtra("tenSach",books.getName());
                data.putExtra("soTrangSach",books.getPage());
                data.putExtra("giaSach",books.getPrice());
                data.putExtra("anhSach",books.getImage());
                data.putExtra("moTaSach",books.getDesc());
                startActivityForResult(data,301168);
                System.out.println("i = "+index);
            }
        });
        lvDSach.setAdapter(adapter);
        for (Books b1 : bookVM.getAll()) {
//            System.out.println(b1.getName()+b1.getDesc());
            booksList1.add(new Books(b1.getId(),b1.getName(),b1.getPage(),b1.getPrice(),b1.getDesc(),b1.getImage()));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.itThem);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent data = new Intent(MainActivity.this,AddBookActivity.class);
                startActivityForResult(data,123456);
                return true;
            }
        });
//        MenuItem menuItem1 = menu.findItem(R.id.itQuet1);
//        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
//                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//                intentIntegrator.setPrompt("Đọc mã QR CODE !");
//                intentIntegrator.setCameraId(0);
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.initiateScan();
//                return true;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(requestCode == 123456) {
            if (resultCode == Activity.RESULT_OK) {
//                int i = 0;
                int maSach = data.getIntExtra("maSach",0);
                String tenSach = data.getStringExtra("tenSach");
                int soTrangSach = data.getIntExtra("soTrangSach", 0);
                int giaSach = data.getIntExtra("giaSach", 0);
                String moTaSach = data.getStringExtra("moTaSach");
                byte[] anhSach = data.getByteArrayExtra("anhSach");
//                System.out.println("Mảng đó là: "+anhSach);
                Books b = new Books(maSach,tenSach,soTrangSach,giaSach,moTaSach,anhSach);
                if(bookVM.insertSQL(b) > -1){
                    booksList1.add(b);
                    Toast.makeText(MainActivity.this, "Thêm thành công "+tenSach, Toast.LENGTH_SHORT).show();
//                    booksList1 = bookVM.getAll();
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "Không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == 301168){
            int maSuaSach = data.getIntExtra("maSuaSach",0);
            String tenSuaSach = data.getStringExtra("tenSuaSach");
            int soTrangSuaSach = data.getIntExtra("soSuaTrangSach", 0);
            int giaSuaSach = data.getIntExtra("giaSuaSach", 0);
            String moTaSuaSach = data.getStringExtra("moTaSuaSach");
            byte[] anhSuaSach = data.getByteArrayExtra("anhSuaSach");
//            System.out.println("Mảng đó là: "+anhSach);
            Books bUpdate = new Books(maSuaSach,tenSuaSach,soTrangSuaSach,giaSuaSach,moTaSuaSach,anhSuaSach);
            if(bookVM.updateSQL(bUpdate) > 0){
                booksList1.set(index,bUpdate);
                Toast.makeText(MainActivity.this, "Sửa thành công "+bUpdate.getName(), Toast.LENGTH_SHORT).show();
//                    booksList1 = bookVM.getAll();
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(MainActivity.this, "Không thành công!", Toast.LENGTH_SHORT).show();
            }
        }
//        else if(intentResult != null){
//            String ma = intentResult.getContents();
//            Intent data1 = new Intent(MainActivity.this,WebPageActivity.class);
//            data1.putExtra("link",ma);
//            startActivity(data1);
//            Vibrator v= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // hiệu ứng rung khi scanner thành công!
//            v.vibrate(300);
//        }
    }
}