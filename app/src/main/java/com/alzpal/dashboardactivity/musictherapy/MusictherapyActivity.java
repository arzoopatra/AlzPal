package com.alzpal.dashboardactivity.musictherapy;

import android.os.Bundle;
import com.alzpal.R;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MusictherapyActivity extends AppCompatActivity {

    private WebView youtubeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musictherapy);

        youtubeWebView = findViewById(R.id.youtube_webview);
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript

        youtubeWebView.setWebViewClient(new WebViewClient()); // Set WebViewClient

        // Load YouTube video using iframe
        String html = "<html><body style='margin:0;padding:0;'>" +
                "<iframe width='100%' height='100%' " +
                "src='https://www.youtube.com/embed/KsGj--bv0Ho' " +
                "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' " +
                "referrerpolicy='strict-origin-when-cross-origin' allowfullscreen></iframe>" +
                "</body></html>";
        youtubeWebView.loadData(html, "text/html", "UTF-8");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (youtubeWebView != null) {
            youtubeWebView.destroy();
        }
    }
}
