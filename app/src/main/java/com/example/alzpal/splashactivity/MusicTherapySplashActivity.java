package com.example.alzpal.splashactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.alzpal.R;
import com.example.alzpal.dashboardactivity.musictherapy.MusictherapyActivity;

public class MusicTherapySplashActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_therapy_splash); // Use a separate layout for splash

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MusicTherapySplashActivity.this, MusictherapyActivity.class);
                startActivity(intent);
                finish(); // Close the splash activity
            }
        }, 2000); // 2 seconds delay
    }
}
