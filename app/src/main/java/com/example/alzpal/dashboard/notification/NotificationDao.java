package com.example.alzpal.dashboard.notification;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void insert(Notification notification);

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    List<Notification> getAllNotifications();
}
