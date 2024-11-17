package com.example.alzpal.dashboardactivity.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alzpal.AppDatabase;
import com.example.alzpal.R;

public class TrackLocationActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText numberEditText;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_location);

        nameEditText = findViewById(R.id.editTextName);
        ageEditText = findViewById(R.id.editage);
        numberEditText = findViewById(R.id.editNumber);
        Button trackButton = findViewById(R.id.buttonTrack);
        Button saveButton = findViewById(R.id.buttonSave);
        Button goToTrackerButton = findViewById(R.id.back);

        db = AppDatabase.getDatabase(this);

        trackButton.setOnClickListener(v -> trackLocation());
        saveButton.setOnClickListener(v -> saveDetailsAndRedirect());
        goToTrackerButton.setOnClickListener(v -> {
            Intent intent = new Intent(TrackLocationActivity.this, TrackerActivity.class);
            startActivity(intent);
        });
    }

    private void trackLocation() {
        if (validateInputs()) {
            String name = nameEditText.getText().toString();
            String age = ageEditText.getText().toString();
            String number = numberEditText.getText().toString();

            Intent intent = new Intent(TrackLocationActivity.this, MapsActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("age", age);
            intent.putExtra("number", number);
            startActivity(intent);
        }
    }

    private void saveDetailsAndRedirect() {
        if (validateInputs()) {
            String name = nameEditText.getText().toString();
            String age = ageEditText.getText().toString();
            String number = numberEditText.getText().toString();

            UserDetails userDetails = new UserDetails(name, age, number);

            new Thread(() -> {
                try {
                    db.userDetailsDao().insert(userDetails);
                    runOnUiThread(() -> {
                        Toast.makeText(TrackLocationActivity.this, "Details saved successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TrackLocationActivity.this, SavedLocationActivity.class);
                        startActivity(intent);
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(TrackLocationActivity.this, "Failed to save details. Please try again.", Toast.LENGTH_SHORT).show());
                }
            }).start();
        }
    }

    private boolean validateInputs() {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String number = numberEditText.getText().toString();

        if (name.isEmpty() || age.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
