package com.example.alzpal.dashboardactivity.memorygames;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alzpal.dashboardactivity.memorygames.game_1.GameActivity;
import com.example.alzpal.R;

public class GameSplashActivity extends AppCompatActivity {

    private TextView coinsCollectedTextView;
    private TextView congratulationsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_splash);

        congratulationsText = findViewById(R.id.congratulations_text);
        coinsCollectedTextView = findViewById(R.id.coins_collected_text_view);
        Button replayButton = findViewById(R.id.replay_button);
        Button exitButton = findViewById(R.id.exit_button);

        String message = getIntent().getStringExtra("message");
        boolean won = getIntent().getBooleanExtra("isWin", false);

        congratulationsText.setText(message);

        if (won) {
            incrementCoins();
        }

        updateCoinsDisplay();

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSplashActivity.this, GameActivity.class);
                intent.putExtra("SHOW_REPLAY_BUTTON", true);
                startActivity(intent);
                finish();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSplashActivity.this, MemoryGamesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateCoinsDisplay() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int coins = prefs.getInt("coins", 0);
        coinsCollectedTextView.setText("Coins: " + coins);
    }

    private void incrementCoins() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int currentWins = sharedPreferences.getInt("wins", 0);
        int rewardCoins = 2 * (currentWins + 1);
        int currentCoins = sharedPreferences.getInt("coins", 0);
        editor.putInt("coins", currentCoins + rewardCoins);
        editor.apply();
    }
}
