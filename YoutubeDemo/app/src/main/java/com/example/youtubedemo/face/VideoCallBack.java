package com.example.youtubedemo.face;

import com.example.youtubedemo.model.Video;

import java.util.List;

public interface VideoCallBack {
    void onSuccessListVideo(List<Video> videoList);
}
