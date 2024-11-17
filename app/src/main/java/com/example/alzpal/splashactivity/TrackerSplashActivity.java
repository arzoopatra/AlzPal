package com.example.alzpal.splashactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.alzpal.R;
import com.example.alzpal.dashboardactivity.tracker.TrackerActivity;

public class TrackerSplashActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_splash);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TrackerSplashActivity.this, TrackerActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
