package com.example.fricashop.view;

//import static com.example.fricashop.view.Test.REQUEST_PERMISSIONS;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fricashop.R;
import com.example.fricashop.face.FeedBackInterface;
import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.FeedBackPresenter;
import com.example.fricashop.presenter.UserPresenter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PostStatus extends AppCompatActivity implements FeedBackInterface {
    ImageView imgAnhPost;
    TextView edtTitlePost;
    Toolbar tbPostStatus;
    FeedBackPresenter fbPresenter;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_status);

        mapping();

        addActionBar();

        imgAnhPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(PostStatus.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(PostStatus.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(PostStatus.this,
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3){
            if(resultCode == RESULT_OK){
                Uri picUri = data.getData();
                    try {
                        InputStream is = getContentResolver().openInputStream(picUri);
                        bm = BitmapFactory.decodeStream(is);
//                        uploadBitmap(bitmap);
                        imgAnhPost.setImageBitmap(bm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post,menu);
        MenuItem itPost = menu.findItem(R.id.itPost);
        itPost.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                postStatus();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void postStatus() {
        fbPresenter = new FeedBackPresenter(this);
        fbPresenter.postStatus(bm, UserPresenter.userList.get(0).getId(),edtTitlePost.getText().toString());

    }


    private void addActionBar() {
        setSupportActionBar(tbPostStatus);
        getSupportActionBar().setTitle("Bài viết mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tbPostStatus.setNavigationIcon(R.drawable.arrow_back);
        tbPostStatus.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        imgAnhPost = findViewById(R.id.imgAnhPost);
        edtTitlePost = findViewById(R.id.edtTitlePost);
        tbPostStatus = findViewById(R.id.tbPostStatus);
    }

    @Override
    public void onSuccessFeedBack(List<Status> list, List<User> userList) {

    }

    @Override
    public void onSuccessBlog(List<Comment> cList) {

    }

    @Override
    public Context getContextFeedBack() {
        return PostStatus.this;
    }

    @Override
    public void returnFeedBack() {
        Intent data = new Intent(PostStatus.this,FeedBack.class);
        startActivity(data);

    }

    @Override
    public void setOnClickLikeListener(Status s, int i) {

    }

    @Override
    public void setOnClickCommentListener(Status s,User u, int i) {

    }

    @Override
    public void setOnClickDeleteListener(Status s, User u, int i) {

    }

    @Override
    public void setOnClickUpdateListener(Status s, User u, int i) {

    }

}