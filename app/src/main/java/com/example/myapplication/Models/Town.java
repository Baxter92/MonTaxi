package com.example.myapplication.Models;

public class Town {

    private int id;
    private String name;
    private long latitude;
    private long longitude;

    public Town(int id, String name, long latitude, long longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
