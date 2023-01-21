package com.example.fricashop.view;

import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fricashop.R;
import com.example.fricashop.adapter.CommentAdapter;
import com.example.fricashop.face.FeedBackInterface;
import com.example.fricashop.face.OnClickComment;
import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.FeedBackPresenter;
import com.example.fricashop.presenter.UserPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentFeedBack extends AppCompatActivity implements FeedBackInterface {
    RecyclerView rcyComment;
    Toolbar tbComment;
    CommentAdapter cAdapter;
    List<Comment> commentList;
    FeedBackPresenter feedBackPresenter = new FeedBackPresenter(this);;
    int id;
    String name_blog;
    String image_blog;
    String desc_blog;
    String name_comment, image_name;
    ImageView btnAddComment;
    ImageButton imgCommentBlog,imgNameComment;
    TextView txtNameComment,txtDescComment;
    EditText edtAddComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_feed_back);
        mapping();
        getDataIntent();
        getBlogComment();
        actionToolBar();
    }

    private void getDataIntent() {
        Intent data = getIntent();
        id = data.getIntExtra("blog_id",0);
        name_blog = data.getStringExtra("name_blog");
        image_blog = data.getStringExtra("img_blog");
        desc_blog = data.getStringExtra("desc_blog");
        image_name = data.getStringExtra("img_name");
        name_comment = data.getStringExtra("name_comment");
//        Log.d("AAAA",name_comment);
        Picasso.get().load(image_blog).resize(200,200).centerCrop().into(imgNameComment);
        txtNameComment.setText(name_blog);
        txtDescComment.setText(desc_blog);
        Picasso.get().load(data.getStringExtra("img_name")).resize(200,200).centerCrop().into(imgCommentBlog);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedBackPresenter.addComment(id,image_name, UserPresenter.userList.get(0).getName(),edtAddComment.getText().toString());
                edtAddComment.setText("");
                refresh(100);
            }
        });
    }

    private void refresh(int millisecond) {
        Handler handler = new Handler(); // do Android cung cấp để trao đổi, liên kết giữa các Thread(luồng dữ liệu). Có nv là gửi và thực thi các Message hoặc Runnable tới Message Queue
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getBlogComment(); // thiết lập luồng dữ liệu mới
            }
        };
        handler.postDelayed(runnable,millisecond); // thực hiện luồng mới trong 0.5s
    }

    public void getBlogComment() {
//        feedBackPresenter = new FeedBackPresenter(this);
        feedBackPresenter.getDetailBlog(new FeedBackInterface() {
            @Override
            public void onSuccessFeedBack(List<Status> list, List<User> userList) {

            }

            @Override
            public void onSuccessBlog(List<Comment> cList) {
                commentList = new ArrayList<>();
                for (Comment c: cList) {
                    commentList.add(c);
                }
                Log.d("AAAA",commentList+"");
                cAdapter = new CommentAdapter(CommentFeedBack.this, R.layout.activity_person_comment, commentList, new OnClickComment() {
                    @Override
                    public void onClickEdit(Comment c, int i) {
                        if(UserPresenter.userList.get(0).getName().equals(c.getName_blog())){
                            Intent data = new Intent(CommentFeedBack.this,UpdateComment.class);
                            data.putExtra("id",c.getId());
                            data.putExtra("comment",c.getDesc_blog());
                            data.putExtra("image_comment",c.getImg_blog());
                            startActivityForResult(data,3005);
                        }else{
                            Toast.makeText(CommentFeedBack.this, "Đây không phải tài khoản của bạn!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onClickDelete(Comment c, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommentFeedBack.this);
                        builder.setTitle("Xóa bình luận");
                        builder.setMessage("Có nên xóa bình luận này không?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(UserPresenter.userList.get(0).getName().equals(c.getName_blog())){
                                    feedBackPresenter.deleteComment(c.getId(),c.getBlog_id());
//                                    Toast.makeText(CommentFeedBack.this, ""+position, Toast.LENGTH_SHORT).show();
                                    commentList.remove(position);
                                    cAdapter.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(CommentFeedBack.this, "Không thể xóa được", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                finish();
                            }
                        });
                        builder.setCancelable(true);
                        builder.show();
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(CommentFeedBack.this,LinearLayoutManager.VERTICAL,false);
                rcyComment.setLayoutManager(layoutManager);
                rcyComment.setAdapter(cAdapter);
            }

            @Override
            public Context getContextFeedBack() {
                return CommentFeedBack.this;
            }

            @Override
            public void returnFeedBack() {

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
        },id);
    }

    private void actionToolBar() {
        setSupportActionBar(tbComment);
        getSupportActionBar().setTitle("Bình luận");
        tbComment.setNavigationIcon(R.drawable.arrow_back);
        tbComment.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(CommentFeedBack.this,FeedBack.class);
                startActivity(data);
//                finish();
            }
        });
    }

    private void mapping() {
        rcyComment = findViewById(R.id.rcyComment);
        tbComment = findViewById(R.id.tbComment);
        txtNameComment = findViewById(R.id.txtNameComment);
        txtDescComment = findViewById(R.id.txtDescComment);
        imgCommentBlog = findViewById(R.id.imgCommentBlog);
        imgNameComment = findViewById(R.id.imgNameComment);
        edtAddComment = findViewById(R.id.edtAddComment);
        btnAddComment = findViewById(R.id.btnAddComment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3005){
            if(resultCode == RESULT_OK){
                desc_blog = data.getStringExtra("desc_blog");
//                getBlogComment();
                refresh(100);
            }
        }
    }

    @Override
    public void onSuccessFeedBack(List<Status> list, List<User> userList) {

    }

    @Override
    public void onSuccessBlog(List<Comment> cList) {

    }

    @Override
    public Context getContextFeedBack() {
        return CommentFeedBack.this;
    }

    @Override
    public void returnFeedBack() {

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