package com.example.fricashop.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.fricashop.HomePage;
import com.example.fricashop.R;
import com.example.fricashop.adapter.FeedBackAdapter;
import com.example.fricashop.face.FeedBackInterface;
import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.FeedBackPresenter;
import com.example.fricashop.presenter.UserPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FeedBack extends AppCompatActivity implements FeedBackInterface {
    Toolbar tbFeedBack;
    RecyclerView rcyFeedBack;
    FeedBackAdapter feedBackAdapter;
    List<Status> sList;
    FirebaseAuth mAuth;
    DatabaseReference LikeRef;
    FloatingActionButton fabPost;
    FeedBackPresenter fbPresenter;
    List<User> uList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        mapping();

        addActionBar();
        addAction();
//        for (int i = 0; i < UserPresenter.userList.size(); i++) {
//            User u = UserPresenter.userList.get(i);
//        }
        refresh(1000);
        getAllFB();

    }

    private void getAllFB() {
        fbPresenter = new FeedBackPresenter(this);
        fbPresenter.getAllFeedBack(new FeedBackInterface() {
            @Override
            public void onSuccessFeedBack(List<Status> list, List<User> userList) {
                sList = new ArrayList<>();
                uList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    sList.add(list.get(i));
                }
                for (User user: userList) {
                    uList.add(user);
                }
                feedBackAdapter = new FeedBackAdapter(FeedBack.this, R.layout.activity_feed_back_status, sList, uList, new FeedBackInterface() {
                    @Override
                    public void onSuccessFeedBack(List<Status> list, List<User> userList) {

                    }

                    @Override
                    public void onSuccessBlog(List<Comment> cList) {

                    }

                    @Override
                    public Context getContextFeedBack() {
                        return FeedBack.this;
                    }

                    @Override
                    public void returnFeedBack() {

                    }

                    @Override
                    public void setOnClickLikeListener(Status s, int position) {
//                        Toast.makeText(FeedBack.this, ""+s.getNumberLike()+" id: "+s.getTitle(), Toast.LENGTH_SHORT).show();
                        fbPresenter.likeStatus(s.getNumberLike(),s.getTitle());
                    }

                    @Override
                    public void setOnClickCommentListener(Status s,User u, int i) {
//                        Log.d("AAA",s.getId()+"");
                        Intent data = new Intent(FeedBack.this,CommentFeedBack.class);
                        data.putExtra("blog_id",s.getId());
                        data.putExtra("name_blog",s.getName());
                        data.putExtra("img_blog",s.getImage());
                        data.putExtra("desc_blog",s.getTitle());
                        data.putExtra("img_name",u.getImage());
                        data.putExtra("name_comment",UserPresenter.userList.get(0).getName());
                        startActivity(data);
                    }

                    @Override
                    public void setOnClickDeleteListener(Status s, User u, int i) {
                                if(UserPresenter.userList.get(0).getName().equals(u.getName())){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FeedBack.this); // hiện thông báo (confirm) để xác nhận yêu cầu
                                    builder.setTitle("Xóa bài đăng");
                                    builder.setMessage("Bạn có muốn xóa bài đăng này không?");
                                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    });
                                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            fbPresenter.deletePost(s.getId());
                                        }
                                    });
                                    builder.show();

                                }else{

                                }
                    }

                    @Override
                    public void setOnClickUpdateListener(Status s, User u, int i) {
                        Intent data = new Intent(FeedBack.this,UpdateFeedBack.class);
                        data.putExtra("id_blog",s.getId());
                        data.putExtra("image_blog",s.getImage());
                        data.putExtra("desc_blog",s.getTitle());
                        startActivity(data);
                    }
                });
                LinearLayoutManager llManager = new LinearLayoutManager(FeedBack.this,RecyclerView.VERTICAL,false);
                rcyFeedBack.setLayoutManager(llManager);
                rcyFeedBack.setAdapter(feedBackAdapter);
                feedBackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccessBlog(List<Comment> cList) {

            }

            @Override
            public Context getContextFeedBack() {
                return FeedBack.this;
            }

            @Override
            public void returnFeedBack() {
                Intent data = new Intent(FeedBack.this,FeedBack.class);
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
        });
    }

    private void addAction() {
        fabPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(FeedBack.this,PostStatus.class);
                startActivity(data);
            }
        });
    }

    private void addActionBar() {
        setSupportActionBar(tbFeedBack);
        getSupportActionBar().setTitle("FeedBack");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tbFeedBack.setNavigationIcon(R.drawable.arrow_back);
        tbFeedBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(FeedBack.this, HomePage.class);
                startActivity(data);
            }
        });
    }

    private void refresh(int millisecond) {
        Handler handler = new Handler(); // do Android cung cấp để trao đổi, liên kết giữa các Thread(luồng dữ liệu). Có nv là gửi và thực thi các Message hoặc Runnable tới Message Queue
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getAllFB(); // thiết lập luồng dữ liệu mới
            }
        };
        handler.postDelayed(runnable,millisecond); // thực hiện luồng mới trong 0.5s
    }

    private void mapping() {
        tbFeedBack = findViewById(R.id.tbFeedBack);
        rcyFeedBack = findViewById(R.id.rcyFeedBack);
        fabPost = findViewById(R.id.fabPost);
    }

    @Override
    public void onSuccessFeedBack(List<Status> list, List<User> userList) {

    }

    @Override
    public void onSuccessBlog(List<Comment> cList) {

    }

    @Override
    public Context getContextFeedBack() {
        return this;
    }

    @Override
    public void returnFeedBack() {
        Intent data = new Intent(FeedBack.this,FeedBack.class);
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