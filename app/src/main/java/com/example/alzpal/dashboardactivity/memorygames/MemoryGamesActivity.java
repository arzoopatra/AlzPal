package com.example.alzpal.dashboardactivity.memorygames;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.alzpal.dashboard.HomeActivity;
import com.example.alzpal.dashboardactivity.memorygames.game_2.ImageGamesActivity;
import com.example.alzpal.R;
import com.example.alzpal.dashboardactivity.memorygames.game_1.GameActivity;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import timber.log.Timber;

public class MemoryGamesActivity extends AppCompatActivity {

    private ImageView avatarImageView;
    private TextView coinsTextView;
    private PieChart pieChart;
    private TextView winsTextView;
    private TextView lossesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_games);

        avatarImageView = findViewById(R.id.avatar_image_view);
        coinsTextView = findViewById(R.id.coin_text_view);
        pieChart = findViewById(R.id.pie_chart);
        winsTextView = findViewById(R.id.wins_text_view);
        lossesTextView = findViewById(R.id.losses_text_view);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int savedAvatarId = prefs.getInt("avatarId", R.drawable.others);
        avatarImageView.setImageResource(savedAvatarId);

        updateCoinsDisplay();

        CardView cardsGameCard = findViewById(R.id.cards_game);
        CardView imageGamesCard = findViewById(R.id.image_games);

        cardsGameCard.setOnClickListener(v -> openCardsGameActivity());
        imageGamesCard.setOnClickListener(v -> openImageGamesActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(this::updatePieChart, 200);
    }

    private void updateCoinsDisplay() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int coins = prefs.getInt("coins", 0);
        coinsTextView.setText("Coins: " + coins);
    }

    private void openCardsGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void openImageGamesActivity() {
        Intent intent = new Intent(this, ImageGamesActivity.class);
        startActivity(intent);
    }

    private void updatePieChart() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int wins = prefs.getInt("wins", 0);
        int losses = prefs.getInt("losses", 0);

        Timber.d("Wins: %d, Losses: %d", wins, losses);

        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Wins", wins, Color.parseColor("#45A834")));
        pieChart.addPieSlice(new PieModel("Losses", losses, Color.parseColor("#FF0000")));

        pieChart.startAnimation();
        winsTextView.setText("Wins : " + wins);
        lossesTextView.setText("Loss : " + losses);
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        ActivityOptions options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right,
                R.anim.slide_out_left
        );

        startActivity(intent, options.toBundle());
        finish();
    }
}
