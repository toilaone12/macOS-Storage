package com.example.fricashop.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fricashop.R;
import com.example.fricashop.face.FeedBackInterface;
import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.FeedBackPresenter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UpdateFeedBack extends AppCompatActivity implements FeedBackInterface {
    EditText edtUpdateTitlePost;
    ImageView imgUpdateAnhPost;
    Toolbar tbUpdateStatus;
    FeedBackPresenter feedBackPresenter;
    int id;
    String image_blog,title_blog;
    Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_feed_back);
        mapping(); // ánh xạ
        addActionToolbar();
        addAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // lớp này sử dụng để khởi tạo các tệp XML
        inflater.inflate(R.menu.menu_update,menu);
        MenuItem mnuItemUpdate = menu.findItem(R.id.itUpdate);
        mnuItemUpdate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(bm == null){
                    Toast.makeText(UpdateFeedBack.this, "Bạn phải sửa cả ảnh bài viết", Toast.LENGTH_SHORT).show();
                }else{
                    updatePost();
                }

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void updatePost() {
        feedBackPresenter = new FeedBackPresenter(this);
        feedBackPresenter.updatePost(id,bm,edtUpdateTitlePost.getText().toString());
    }

    private void addAction() {
        Intent data = getIntent(); // có 2 loại intent đó là intent explicit(tường minh) và intent implicit(không tường minh) đây là intent explicit
        id = data.getIntExtra("id_blog",0);
        image_blog = data.getStringExtra("image_blog");
        title_blog = data.getStringExtra("desc_blog");
        Picasso.get().load(image_blog).placeholder(R.drawable.loading).into(imgUpdateAnhPost);
//        Glide.with(UpdateFeedBack.this).asGif().load(R.drawable.loading).load(image_blog);
        edtUpdateTitlePost.setText(title_blog);
        
        imgUpdateAnhPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(UpdateFeedBack.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(UpdateFeedBack.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(UpdateFeedBack.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                3);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 5){
            if(resultCode == RESULT_OK){
                Uri picUri = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(picUri);
                    bm = BitmapFactory.decodeStream(is); // là thể hiện 1 bức ảnh trong android
//                        uploadBitmap(bitmap);
                    imgUpdateAnhPost.setImageBitmap(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void addActionToolbar() {
        setSupportActionBar(tbUpdateStatus);
        getSupportActionBar().setTitle("Chỉnh sửa bài viết");
        tbUpdateStatus.setNavigationIcon(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // cho biểu tượng có thể ấn vào đc
        tbUpdateStatus.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    
    

    private void mapping() {
        edtUpdateTitlePost = findViewById(R.id.edtUpdateTitlePost);
        imgUpdateAnhPost = findViewById(R.id.imgUpdateAnhPost);
        tbUpdateStatus = findViewById(R.id.tbUpdateStatus);
    }

    @Override
    public void onSuccessFeedBack(List<Status> list, List<User> userList) {

    }

    @Override
    public void onSuccessBlog(List<Comment> cList) {

    }

    @Override
    public Context getContextFeedBack() {
        return UpdateFeedBack.this;
    }

    @Override
    public void returnFeedBack() {
        Intent data = new Intent(UpdateFeedBack.this,FeedBack.class);
        startActivity(data);
    }

    @Override
    public void setOnClickLikeListener(Status s, int i) {

    }

    @Override
    public void setOnClickCommentListener(Status s, User u, int i) {

    }

    @Override
    public void setOnClickDeleteListener(Status s, User u, int i) {

    }

    @Override
    public void setOnClickUpdateListener(Status s, User u, int i) {

    }
}