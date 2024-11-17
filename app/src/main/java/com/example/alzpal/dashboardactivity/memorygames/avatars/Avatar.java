package com.example.alzpal.dashboardactivity.memorygames.avatars;

public class Avatar {
    private final int imageResourceId;
    private String name;

    public Avatar(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
