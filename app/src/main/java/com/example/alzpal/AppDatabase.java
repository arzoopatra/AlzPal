package com.example.alzpal;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.alzpal.dashboard.notification.Notification;
import com.example.alzpal.dashboard.notification.NotificationDao;
import com.example.alzpal.dashboardactivity.tracker.UserDetails;
import com.example.alzpal.dashboardactivity.tracker.UserDetailsDao;

@Database(entities = {UserDetails.class, Notification.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDetailsDao userDetailsDao();
    public abstract NotificationDao notificationDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
