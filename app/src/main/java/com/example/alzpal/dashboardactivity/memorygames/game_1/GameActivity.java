package com.example.alzpal.dashboardactivity.memorygames.game_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.TextView;
import com.example.alzpal.dashboardactivity.memorygames.GameSplashActivity;
import com.example.alzpal.R;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private GridView gridView;
    private List<Card> cards;
    private CardAdapter cardAdapter;
    private Card firstCard, secondCard;
    private boolean canClick = true;
    private final Handler handler = new Handler();
    private TextView timerTextView;
    private int countdownTime = 60;
    private Runnable timerRunnable;
    private boolean isTimerStopped = false;
    private boolean hasWon = false;

    private MediaPlayer cardClickSound;
    private MediaPlayer timerWarningSound;
    private MediaPlayer winSound;
    private MediaPlayer loseSound;
    private MediaPlayer matchSound;
    private MediaPlayer backgroundMusic;

    private int coins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridView = findViewById(R.id.grid_layout);
        timerTextView = findViewById(R.id.timer_text_view);

        cardClickSound = MediaPlayer.create(this, R.raw.card_click);
        timerWarningSound = MediaPlayer.create(this, R.raw.timer_warning);
        winSound = MediaPlayer.create(this, R.raw.win);
        loseSound = MediaPlayer.create(this, R.raw.lose);
        matchSound = MediaPlayer.create(this, R.raw.cards_match);
        backgroundMusic = MediaPlayer.create(this, R.raw.game1_sound);

        startBackgroundMusic();
        initializeCards();
        startTimer();

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        coins = sharedPreferences.getInt("coins", 0);
    }

    private void startBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.setLooping(true);
            backgroundMusic.start();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.pause();
            backgroundMusic.seekTo(0);
        }
    }

    private void initializeCards() {
        cards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            cards.add(new Card(i));
            cards.add(new Card(i));
        }
        Collections.shuffle(cards);

        cardAdapter = new CardAdapter(this, cards, new CardAdapter.CardClickListener() {
            @Override
            public void onCardClicked(Card card) {
                if (!canClick || card.isFlipped() || card.isMatched() || countdownTime <= 0) {
                    return;
                }

                card.flip();
                cardClickSound.start();

                if (firstCard == null) {
                    firstCard = card;
                } else {
                    secondCard = card;
                    canClick = false;
                    handler.postDelayed(() -> checkForMatch(), 1000);
                }
                cardAdapter.notifyDataSetChanged();
            }
        });
        gridView.setAdapter(cardAdapter);
    }

    private void checkForMatch() {
        if (firstCard.getId() == secondCard.getId()) {
            firstCard.setMatched(true);
            secondCard.setMatched(true);
            firstCard.setVisible(false);
            secondCard.setVisible(false);
            cardAdapter.notifyDataSetChanged();
            matchSound.start();

            if (allCardsHidden()) {
                showWinMessage();
                return;
            }
        } else {
            firstCard.flip();
            secondCard.flip();
        }
        firstCard = null;
        secondCard = null;
        canClick = true;
    }

    private boolean allCardsHidden() {
        for (Card card : cards) {
            if (card.isVisible()) {
                return false;
            }
        }
        return true;
    }

    private void startTimer() {
        if (timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
        }
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (countdownTime > 0) {
                    countdownTime--;
                    int minutes = countdownTime / 60;
                    int seconds = countdownTime % 60;
                    timerTextView.setText(String.format("%02d:%02d", minutes, seconds));

                    if (countdownTime == 10) {
                        timerWarningSound.start();
                    }

                    handler.postDelayed(this, 1000);
                } else {
                    stopTimer();
                }
            }
        };
        handler.post(timerRunnable);
    }

    private void stopTimer() {
        if (isTimerStopped) return;
        isTimerStopped = true;

        handler.removeCallbacks(timerRunnable);
        timerTextView.setText("Game Over!");

        if (!allCardsHidden()) {
            loseSound.start();
            updateLosses();
            showSplashScreen("All the Best for Next Time!", false);
        } else {
            showWinMessage();
        }

        stopBackgroundMusic();
    }

    private void showWinMessage() {
        if (!isTimerStopped) {
            stopTimer();
        }

        winSound.start();
        hasWon = true;
        incrementCoins();
        updateWins();
        showSplashScreen("Congratulations! \nYou did a Wonderful Job!", true);
    }

    private void incrementCoins() {
        if (hasWon) {
            SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            int currentWins = sharedPreferences.getInt("wins", 0);
            int rewardCoins = 2 * (currentWins + 1);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int currentCoins = sharedPreferences.getInt("coins", 0);
            editor.putInt("coins", currentCoins + rewardCoins);
            editor.apply();
        }
    }

    private void updateWins() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int wins = sharedPreferences.getInt("wins", 0);
        editor.putInt("wins", wins + 1);
        editor.apply();
    }

    private void updateLosses() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int losses = sharedPreferences.getInt("losses", 0);
        editor.putInt("losses", losses + 1);
        editor.apply();
    }

    private void showSplashScreen(String message, boolean isWin) {
        Intent intent = new Intent(GameActivity.this, GameSplashActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("isWin", isWin);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopBackgroundMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cardClickSound != null) cardClickSound.release();
        if (timerWarningSound != null) timerWarningSound.release();
        if (winSound != null) winSound.release();
        if (loseSound != null) loseSound.release();
        if (matchSound != null) matchSound.release();
        if (backgroundMusic != null) backgroundMusic.release();
    }
}
