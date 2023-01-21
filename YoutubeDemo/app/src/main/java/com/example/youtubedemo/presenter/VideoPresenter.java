package com.example.youtubedemo.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubedemo.MainActivity;
import com.example.youtubedemo.face.VideoCallBack;
import com.example.youtubedemo.face.VideoInterface;
import com.example.youtubedemo.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class VideoPresenter {
    public static final String NAME_LIST = "RDaRTpWKnOrAY";
    List<Video> videoList;
//    VideoCallBack videoCallBack;
//
//    public VideoPresenter(VideoCallBack videoCallBack) {
//        this.videoCallBack = videoCallBack;
//    }
    VideoInterface videoInterface;

    public VideoPresenter(VideoInterface videoInterface) {
        this.videoInterface = videoInterface;
    }

    public void takeAllVideoList(final VideoCallBack videoCallBack){
        RequestQueue queue = Volley.newRequestQueue(videoInterface.getContext());
        String urlGetYouTubeAPI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+NAME_LIST+"&key="+ MainActivity.API_KEY+"&maxResults=15";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetYouTubeAPI, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    videoList = new ArrayList<>();
                    JSONArray array = response.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        JSONObject objectSnippet = object.getJSONObject("snippet");
                        //tên video
                        String nameVideo = objectSnippet.getString("title");
                        //ảnh video
                        JSONObject objectThumb = objectSnippet.getJSONObject("thumbnails");
                        JSONObject objectImage = objectThumb.getJSONObject("medium");
                        String imageVideo = objectImage.getString("url");
                        //mã id video
                        JSONObject objectId = objectSnippet.getJSONObject("resourceId");
                        String idVideo = objectId.getString("videoId");
                        //tên tác giả video
                        String authorVideo = objectSnippet.getString("videoOwnerChannelTitle");

                        Video v = new Video(nameVideo,imageVideo,authorVideo,idVideo);
                        videoList.add(v);
                        videoCallBack.onSuccessListVideo(videoList);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(videoInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("er","Error "+error);
            }
        });
        queue.add(jsonObjectRequest);
    }
}
