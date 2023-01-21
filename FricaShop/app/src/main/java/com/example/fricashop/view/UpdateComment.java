package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fricashop.R;
import com.example.fricashop.face.FeedBackInterface;
import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.FeedBackPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpdateComment extends AppCompatActivity implements FeedBackInterface {
    ImageView imgUpdateComment;
    TextView txtCancel,txtUpdateComment;
    EditText edtUpdateDescComment;
    FeedBackPresenter feedBackPresenter;
    Toolbar tbUpdateComment;
    int id;
    String imgUpdate,descUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comment);

        mapping();
        getIntentData();
    }

    private void getIntentData() {
        Intent data = getIntent();
        id = data.getIntExtra("id",0);
        imgUpdate = data.getStringExtra("image_comment");
        descUpdate = data.getStringExtra("comment");
        Picasso.get().load(imgUpdate).into(imgUpdateComment);
        edtUpdateDescComment.setText(descUpdate);

        setSupportActionBar(tbUpdateComment);
        getSupportActionBar().setTitle("Chỉnh sửa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbUpdateComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        tbUpdateComment.setNavigationIcon(R.drawable.arrow_back);
        tbUpdateComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataComeback = new Intent(UpdateComment.this,CommentFeedBack.class);
                startActivity(dataComeback);
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtUpdateComment.setEnabled(false);
        txtUpdateComment.setBackgroundResource(R.drawable.border_disable);
        txtUpdateComment.setTextColor(getResources().getColor(R.color.disable));
        edtUpdateDescComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
//                Log.d("AAA",text + " - " + descUpdate);
                if(text != (descUpdate) && text.length() > 0){
                    txtUpdateComment.setEnabled(true);
                    txtUpdateComment.setBackgroundResource(R.drawable.border_update);
                    txtUpdateComment.setTextColor(Color.WHITE);
                    txtUpdateComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateComment(id,edtUpdateDescComment.getText().toString());
                        }
                    });
                }else{
                    txtUpdateComment.setEnabled(false);
                    txtUpdateComment.setBackgroundResource(R.drawable.border_disable);
                    txtUpdateComment.setTextColor(getResources().getColor(R.color.disable));
                }
            }
        });

    }

    private void updateComment(int id, String descUpdate) {
        feedBackPresenter = new FeedBackPresenter(this);
        feedBackPresenter.updateComment(id,descUpdate);
    }

    private void mapping() {
        imgUpdateComment = findViewById(R.id.imgUpdateComment);
        edtUpdateDescComment = findViewById(R.id.edtUpdateDescComment);
        txtCancel = findViewById(R.id.txtCancel);
        txtUpdateComment = findViewById(R.id.txtUpdateComment);
        tbUpdateComment = findViewById(R.id.tbUpdateComment);
    }

    @Override
    public void onSuccessFeedBack(List<Status> list, List<User> userList) {

    }

    @Override
    public void onSuccessBlog(List<Comment> cList) {

    }

    @Override
    public Context getContextFeedBack() {
        return UpdateComment.this;
    }

    @Override
    public void returnFeedBack() {
        Intent dataComeback = new Intent(UpdateComment.this,CommentFeedBack.class);
        dataComeback.putExtra("desc_blog",edtUpdateDescComment.getText().toString());
        setResult(RESULT_OK,dataComeback);
//        startActivity(dataComeback);
        finish();
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