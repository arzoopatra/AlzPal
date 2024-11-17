package com.example.alzpal.dashboardactivity.tracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserDetailsDao {

    @Insert
    void insert(UserDetails userDetails);

    @Query("SELECT * FROM UserDetails")
    List<UserDetails> getAll();

    @Delete
    void delete(UserDetails userDetails);
}

