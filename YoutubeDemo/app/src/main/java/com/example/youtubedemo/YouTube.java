package com.example.youtubedemo;

import static com.example.youtubedemo.MainActivity.API_KEY;
import static com.example.youtubedemo.presenter.VideoPresenter.NAME_LIST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubedemo.adapter.VideoAdapter;
import com.example.youtubedemo.face.OnClickVideoItems;
import com.example.youtubedemo.face.VideoCallBack;
import com.example.youtubedemo.face.VideoInterface;
import com.example.youtubedemo.model.Video;
import com.example.youtubedemo.presenter.VideoPresenter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

public class YouTube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, VideoInterface,VideoCallBack {
    RecyclerView rcyPlayList;
    TextView txtNamePlayList;
    YouTubePlayerView ytbVideo;
    VideoInterface videoInterface;
    VideoCallBack videoCallBack;
    List<Video> vList;
    VideoAdapter videoAdapter;
    VideoPresenter videoPresenter;
    Video v;
    String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        mapping();

        getAllVideo();

//        ytbVideo.initialize(API_KEY,this);
    }


    private void getAllVideo() {
        videoPresenter = new VideoPresenter(this);
        videoPresenter.takeAllVideoList(new VideoCallBack() {
            @Override
            public void onSuccessListVideo(List<Video> videoList) {
                vList = new ArrayList<>();
                for (Video v: videoList) {
                    vList.add(v);
                }
                for (int i = 0; i < vList.size(); i++) {
                    v = vList.get(i);
                }
                videoAdapter = new VideoAdapter(YouTube.this, R.layout.activity_list_video, vList, new OnClickVideoItems() {
                    @Override
                    public void setOnClickVideoListener(String id,int i) {
                        ytbVideo.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                                youTubePlayer.loadVideo(vList.get(i).getVideoId());
////                                youTubePlayer.loadPlaylist(NAME_LIST);
//                                youTubePlayer.play();
//                                vList.clear();
                                Toast.makeText(YouTube.this, ""+vList.get(i).getVideoId(), Toast.LENGTH_SHORT).show();
//                                txtNamePlayList.setText("Danh sách nhạc kết hợp - "+name);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                if(youTubeInitializationResult.isUserRecoverableError()){
                                    ytbVideo.initialize(API_KEY,this);
                                }else{
                                    Toast.makeText(YouTube.this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                        Toast.makeText(YouTube.this, ""+id, Toast.LENGTH_SHORT).show();
                    }
                });
                LinearLayoutManager manager = new LinearLayoutManager(YouTube.this,LinearLayoutManager.VERTICAL,false);
                rcyPlayList.setLayoutManager(manager);
                rcyPlayList.setAdapter(videoAdapter);
            }
        });
    }

    private void mapping() {
        rcyPlayList = findViewById(R.id.rcyPlayList);
        txtNamePlayList = findViewById(R.id.txtNamePlayList);
        ytbVideo = findViewById(R.id.ytbVideo);
    }

    @Override
    public Context getContext() {
        return YouTube.this;
    }

    @Override
    public void onSuccessListVideo(List<Video> videoList) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}