package com.example.alzpal.dashboardactivity.musictherapy;

import android.media.MediaPlayer;
import android.util.Log;

public class MyMediaPlayer {
    private static MediaPlayer instance;
    public static int currentIndex = -1;

    // Private constructor to prevent instantiation
    private MyMediaPlayer() {
    }

    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.release();
            instance = null; // Allow instance to be garbage collected
        }
    }

    public static void resetInstance() {
        if (instance != null) {
            instance.reset();
        }
    }

    public static void setDataSource(String path) {
        try {
            instance.setDataSource(path);
        } catch (Exception e) {
            Log.e("MyMediaPlayer", "Error setting data source", e);
        }
    }
}
