package com.example.fricashop.face;

import android.content.Context;

import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.view.FeedBack;

import java.util.List;

public interface FeedBackInterface {
    void onSuccessFeedBack(List<Status> list, List<User> userList);
    void onSuccessBlog(List<Comment> cList);
    Context getContextFeedBack();
    void returnFeedBack();
    void setOnClickLikeListener(Status s, int i);
    void setOnClickCommentListener(Status s,User u, int i);
    void setOnClickDeleteListener(Status s,User u, int i);
    void setOnClickUpdateListener(Status s,User u, int i);
}
