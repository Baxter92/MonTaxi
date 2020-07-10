package com.example.myapplication.Models;

public class Driver {
    private String id;
    private long ttl;
    private String created_at;
    private int userId;
    private boolean signedup;

    public Driver(String id, long ttl, String created_at, int userId, boolean signedup) {
        this.id = id;
        this.ttl = ttl;
        this.created_at = created_at;
        this.userId = userId;
        this.signedup = signedup;
    }

    public String getId() {
        return id;
    }

    public long getTtl() {
        return ttl;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isSignedup() {
        return signedup;
    }
}
