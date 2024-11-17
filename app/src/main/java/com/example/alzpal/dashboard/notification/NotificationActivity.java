package com.example.alzpal.dashboard.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alzpal.AppDatabase;
import com.example.alzpal.R;
import com.example.alzpal.dashboard.HomeActivity;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private AppDatabase db;
    private List<Notification> notificationList;
    private TextView noNotificationsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        noNotificationsTextView = findViewById(R.id.noNotificationsTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = AppDatabase.getDatabase(this);
        loadNotifications();
    }

    private void loadNotifications() {
        new Thread(() -> {
            notificationList = db.notificationDao().getAllNotifications();
            runOnUiThread(() -> {
                if (notificationList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    noNotificationsTextView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noNotificationsTextView.setVisibility(View.GONE);
                    adapter = new NotificationAdapter(this, notificationList);
                    recyclerView.setAdapter(adapter);
                }
            });
        }).start();
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
