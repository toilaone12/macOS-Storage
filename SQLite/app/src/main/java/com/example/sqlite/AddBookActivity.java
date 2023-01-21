package com.example.sqlite;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

//import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;

public class AddBookActivity extends AppCompatActivity{
    EditText edtTenSach, edtSoTrang, edtGiaSach, edtMoTa, edtMaSach, edtMa;
    ImageView imgAnhSach;
    Button btnThemSach, btnLayAnh, btnThuAnh, btnQuetMa;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        mapping();
        addAction();
    }

    private void addAction() {
//        btnThuAnh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean pick = true;
//                if(pick == true){
//                    if(!checkCameraPermission()){
//                        requestCameraPermissions();
//                    }else{
//                        PickImage();
//                    }
//                }
//            }
//        });
        btnThuAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(Intent.ACTION_PICK);
                data.setType("image/*");
                startActivityForResult(data,111);
            }
        });
        btnQuetMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                IntentIntegrator intentIntegrator = new IntentIntegrator(AddBookActivity.this);
//                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//                intentIntegrator.setPrompt("Đọc mã QR CODE !");
//                intentIntegrator.setCameraId(0);
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.initiateScan();
            }
        });
        btnThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maSach = Integer.parseInt(edtMaSach.getText().toString());
                String tenSach = edtTenSach.getText().toString();
                int soTrangSach = Integer.parseInt(edtSoTrang.getText().toString());
                int giaSach = Integer.parseInt(edtGiaSach.getText().toString());
                String moTaSach = edtMoTa.getText().toString();
//                imgAnhSach.setImageBitmap(bm);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG,75,stream);
                byte[] anhSach = stream.toByteArray();
                Intent data = new Intent();
                data.putExtra("maSach",maSach);
                data.putExtra("tenSach",tenSach);
                data.putExtra("soTrangSach",soTrangSach);
                data.putExtra("giaSach",giaSach);
                data.putExtra("moTaSach",moTaSach);
                data.putExtra("anhSach",anhSach);
//                System.out.println("Mảng byte là "+anhSach);
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }

    private void PickImage() {
        Intent data = new Intent(Intent.ACTION_PICK);
        startActivityForResult(data,111);
    }

//    private void requestStoragePermissions() {
//        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100 );
//    }

    private void requestCameraPermissions() {
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    private boolean checkStoragePermission() {
        boolean writeStorage = ContextCompat.checkSelfPermission(AddBookActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return writeStorage;
    }

    private boolean checkCameraPermission() {
        boolean checkCamera = ContextCompat.checkSelfPermission(AddBookActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
//        boolean writeStorage = ContextCompat.checkSelfPermission(AddBookActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return checkCamera;
    }

    private void mapping() {
        edtMaSach = findViewById(R.id.edtMaSach);
        edtTenSach = findViewById(R.id.edtTenSach);
        edtSoTrang = findViewById(R.id.edtSoTrang);
        edtGiaSach = findViewById(R.id.edtGiaSach);
        edtMoTa = findViewById(R.id.edtMoTa);
        imgAnhSach = findViewById(R.id.imgAnhSach);
//        btnLayAnh = findViewById(R.id.btnLayAnh);
        btnThuAnh = findViewById(R.id.btnThuAnh);
        btnThemSach = findViewById(R.id.btnThemSach);
        btnQuetMa = findViewById(R.id.btnQuetMa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if(requestCode == 111){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(uri);
                    bm = BitmapFactory.decodeStream(is);
                    imgAnhSach.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
//        else if(intentResult != null){
//            try {
//                String ma = intentResult.getContents();
//                Intent data1 = new Intent(AddBookActivity.this,WebPageActivity.class);
//                data1.putExtra("link",ma);
//                startActivity(data1);
//                Vibrator v= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // hiệu ứng rung khi scanner thành công!
//                v.vibrate(300);
////                   System.out.println(bm);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }
}