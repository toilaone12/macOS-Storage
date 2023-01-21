package com.example.youtubedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    YouTubePlayerView ytbPlayer;
    public static final String API_KEY = "AIzaSyCLrmoFXiqWnuHGCXQE23CV-CYhjUZMRdU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ytbPlayer = findViewById(R.id.ytbPlayer);
        ytbPlayer.initialize(MainActivity.API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo("gpeeXlRvQuU");
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            ytbPlayer.initialize(API_KEY,this);
        }else{
            Toast.makeText(this, "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
        }
    }
}