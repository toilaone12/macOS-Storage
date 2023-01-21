package com.example.googlemapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class GoogleMap extends AppCompatActivity {
    TextInputEditText edtStart, edtEnd;
    Button btnFind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        mapping();
    }

    private void mapping() {
        edtStart = findViewById(R.id.edtStart);
        edtEnd = findViewById(R.id.edtEnd);
        btnFind = findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy giá trị từ edittext
                String startLocation = edtStart.getText().toString();
                String endLocation = edtEnd.getText().toString();
                if(startLocation.equals("") && endLocation.equals("")){
                    Toast.makeText(GoogleMap.this, "Chưa nhập địa chỉ đến và đi", Toast.LENGTH_SHORT).show();
                }else if(startLocation.equals("")){
                    Toast.makeText(GoogleMap.this, "Chưa nhập địa chỉ đến", Toast.LENGTH_SHORT).show();
                }else if (endLocation.equals("")){
                    Toast.makeText(GoogleMap.this, "Chưa nhập điểm đến cần tìm", Toast.LENGTH_SHORT).show();
                }else{
                    DisplayLocation(startLocation,endLocation);
                }
            }
        });
    }

    private void DisplayLocation(String startLocation, String endLocation) {
        //Nếu thiết bị chưa có phần mềm gg map
        try {
            Uri u = Uri.parse("http://www.google.co.in/maps/dir/"+startLocation+"/"+endLocation);
            Intent dataLocation = new Intent(Intent.ACTION_VIEW,u);
            dataLocation.setPackage("com.google.android.apps.maps");
            dataLocation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dataLocation);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }
}