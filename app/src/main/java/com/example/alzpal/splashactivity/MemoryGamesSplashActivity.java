package com.example.alzpal.splashactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.alzpal.R;
import com.example.alzpal.dashboardactivity.memorygames.avatars.AvatarSelectionActivity;

public class MemoryGamesSplashActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_games_splash);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MemoryGamesSplashActivity.this, AvatarSelectionActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
