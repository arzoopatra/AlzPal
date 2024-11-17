package com.example.alzpal.dashboardactivity.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alzpal.AppDatabase;
import com.example.alzpal.R;
import java.util.List;

public class SavedLocationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SavedPatientsAdapter adapter;
    private AppDatabase db;
    private List<UserDetails> patientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_location);

        recyclerView = findViewById(R.id.recyclerViewSavedPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = AppDatabase.getDatabase(this);
        loadSavedPatients();
    }

    private void loadSavedPatients() {
        new Thread(() -> {
            patientsList = db.userDetailsDao().getAll();
            runOnUiThread(() -> {
                if (patientsList.isEmpty()) {
                    TextView noSavedPatientsTextView = findViewById(R.id.no_saved_patients_text_view);
                    noSavedPatientsTextView.setText("No Saved Patients Locations Found");
                } else {
                    adapter = new SavedPatientsAdapter(this, patientsList, db.userDetailsDao());
                    recyclerView.setAdapter(adapter);
                }
            });
        }).start();
    }

    public void onBackButtonClick(android.view.View view) {
        Intent intent = new Intent(SavedLocationActivity.this, TrackerActivity.class);
        startActivity(intent);
        finish();
    }
}
