package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPageActivity extends AppCompatActivity {
    WebView wvPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        wvPage = findViewById(R.id.wvPage);

        Intent data = getIntent();
        String link = data.getStringExtra("link");
        wvPage.loadUrl(link);
        wvPage.getSettings().setJavaScriptEnabled(true);
        wvPage.setWebViewClient(new WebViewClient());
    }
}