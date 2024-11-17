package com.example.alzpal.splashactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.alzpal.dashboardactivity.carepal.CarepalActivity;

public class CarePalSplashActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CarePalSplashActivity.this, CarepalActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
