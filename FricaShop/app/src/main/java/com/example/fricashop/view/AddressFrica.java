package com.example.fricashop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fricashop.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddressFrica extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap map;
    Toolbar tbAddress;
    TextView txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_frica);
        tbAddress = findViewById(R.id.tbAddress);
        setSupportActionBar(tbAddress);
        getSupportActionBar().setTitle("Thông tin");
        tbAddress.setNavigationIcon(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtAddress = findViewById(R.id.txtAddress);
        txtAddress.setText("Địa chỉ: Ngõ 3 Cầu Bươu, đường Cầu Bươu," +
                "Tả Thanh Oai, Thanh Trì, Hà Nội");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        map.setMyLocationEnabled(true);
        LatLng address = new LatLng(20.9593223, 105.8065150);
        map.addMarker(new MarkerOptions().position(address).title("Trung tâm Frica").snippet("Ngõ 3 Cầu Bươu, đường Cầu Bươu," +
                " Tả Thanh Oai, Thanh Trì, Hà Nội").icon(BitmapDescriptorFactory.defaultMarker()));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); // set Bản đồ
        CameraPosition cameraPosition = new CameraPosition.Builder().target(address).zoom(90).build(); // phóng camera gg map
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        map.moveCamera(CameraUpdateFactory.newLatLng(address));
    }
}