package com.example.sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateBookActivity extends AppCompatActivity {
    EditText edtSuaTenSach, edtSuaSoTrang, edtSuaGiaSach, edtSuaMoTa, edtSuaMaSach;
    ImageView imgSuaAnhSach;
    Button btnSuaSach, btnSuaAnh;
    Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        mapping();
        addAction();
    }

    private void addAction() {
        Intent data = getIntent();
        byte[] anhSach = data.getByteArrayExtra("anhSach");
        bm = BitmapFactory.decodeByteArray(anhSach,0,anhSach.length);
        imgSuaAnhSach.setImageBitmap(bm);
        int maSach = data.getIntExtra("maSach",0);
        edtSuaMaSach.setText(Integer.toString(data.getIntExtra("maSach",0)));
        edtSuaTenSach.setText(data.getStringExtra("tenSach"));
        edtSuaSoTrang.setText(Integer.toString(data.getIntExtra("soTrangSach",0)));
        edtSuaGiaSach.setText(Integer.toString(data.getIntExtra("giaSach",0)));
        edtSuaMoTa.setText(data.getStringExtra("moTaSach"));

        btnSuaAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pick = true;
                if(pick == true){
                    if(!checkCameraPermission()){
                        requestCameraPermissions();
                    }else{
                        PickImage();
                    }
                }else{
                    if(!checkStoragePermission()){
                        requestStoragePermissions();
                    }else{
                        PickImage();
                    }
                }
            }
        });
        btnSuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maSach = Integer.parseInt(edtSuaMaSach.getText().toString());
                String tenSach = edtSuaTenSach.getText().toString();
                int soTrangSach = Integer.parseInt(edtSuaSoTrang.getText().toString());
                int giaSach = Integer.parseInt(edtSuaGiaSach.getText().toString());
                String moTaSach = edtSuaMoTa.getText().toString();
//                imgAnhSach.setImageBitmap(bm);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG,75,stream);
                byte[] anhSach = stream.toByteArray();
                Intent data1 = new Intent();
                data1.putExtra("maSuaSach",maSach);
                data1.putExtra("tenSuaSach",tenSach);
                data1.putExtra("soSuaTrangSach",soTrangSach);
                data1.putExtra("giaSuaSach",giaSach);
                data1.putExtra("moTaSuaSach",moTaSach);
                data1.putExtra("anhSuaSach",anhSach);
//                System.out.println("Mảng byte là "+anhSach);
                setResult(RESULT_OK,data1);
                finish();
            }
        });
        
    }
    
    private void PickImage() {
        Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(data,123);
    }

    private void requestStoragePermissions() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100 );
    }

    private void requestCameraPermissions() {
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    private boolean checkStoragePermission() {
        boolean writeStorage = ContextCompat.checkSelfPermission(UpdateBookActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return writeStorage;
    }

    private boolean checkCameraPermission() {
        boolean checkCamera = ContextCompat.checkSelfPermission(UpdateBookActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean writeStorage = ContextCompat.checkSelfPermission(UpdateBookActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return checkCamera && writeStorage;
    }
    private void mapping() {
        edtSuaTenSach = findViewById(R.id.edtSuaTenSach);
        edtSuaSoTrang = findViewById(R.id.edtSuaSoTrang);
        edtSuaGiaSach = findViewById(R.id.edtSuaGiaSach);
        edtSuaMoTa = findViewById(R.id.edtSuaMoTa);
        edtSuaMaSach = findViewById(R.id.edtSuaMaSach);
        imgSuaAnhSach = findViewById(R.id.imgSuaAnhSach);
        btnSuaSach = findViewById(R.id.btnSuaSach);
        btnSuaAnh = findViewById(R.id.btnSuaAnh);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            if (resultCode == RESULT_OK) {
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
        if(requestCode == 123){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(uri);
                    bm = BitmapFactory.decodeStream(is);
                    imgSuaAnhSach.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}