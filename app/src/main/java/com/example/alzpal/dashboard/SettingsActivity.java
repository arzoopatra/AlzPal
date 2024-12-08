package com.example.alzpal.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.alzpal.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppSettings";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String KEY_DARK_THEME_ENABLED = "dark_theme_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ToggleButton notificationsSwitch = findViewById(R.id.notifications_switch);
        ToggleButton themeSwitch = findViewById(R.id.theme_switch);
        Button backButton = findViewById(R.id.back);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean notificationsEnabled = sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
        boolean darkThemeEnabled = sharedPreferences.getBoolean(KEY_DARK_THEME_ENABLED, false);

        notificationsSwitch.setChecked(notificationsEnabled);
        themeSwitch.setChecked(darkThemeEnabled);

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(KEY_NOTIFICATIONS_ENABLED, isChecked);
            editor.apply();
            Toast.makeText(SettingsActivity.this, "Notifications " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(KEY_DARK_THEME_ENABLED, isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(SettingsActivity.this, "Dark Theme Enabled", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(SettingsActivity.this, "Dark Theme Disabled", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }
}
