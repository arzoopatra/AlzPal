package com.example.alzpal.dashboardactivity.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.alzpal.R;
import com.example.alzpal.dashboard.HomeActivity;

public class TrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        CardView shareLocationCard = findViewById(R.id.reminder_card);
        CardView trackLocationCard = findViewById(R.id.tracker_card);
        CardView savedPatientsLocationCard = findViewById(R.id.SavedPatientsLocation);
        Button backButton = findViewById(R.id.back);

        shareLocationCard.setOnClickListener(v -> shareLocation());

        trackLocationCard.setOnClickListener(v -> {
            Intent intent = new Intent(TrackerActivity.this, TrackLocationActivity.class);
            startActivity(intent);
        });

        savedPatientsLocationCard.setOnClickListener(v -> {
            Intent intent = new Intent(TrackerActivity.this, SavedLocationActivity.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void shareLocation() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I'm at: [Your Location URL]");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TrackerActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onBackButtonClick(View view) {
    }
}
