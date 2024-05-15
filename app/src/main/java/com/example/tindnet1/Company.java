package com.example.tindnet1;

public class Company {

    private int imageResId;
    private String description;

    public Company(int imageResId, String description) {
        this.imageResId = imageResId;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }

}
