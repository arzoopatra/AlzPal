package com.example.alzpal.dashboardactivity.tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String age;
    private String number;

    public UserDetails(String name, String age, String number) {
        this.name = name;
        this.age = age;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
