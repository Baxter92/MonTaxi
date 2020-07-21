package com.example.myapplication.Models;

public class ChatComment {
    private int id;
    private String phone;
    private String message;
    private boolean read;
    private boolean favoris;
    private String received_at;

    public ChatComment(int id, String phone, String message, boolean read, boolean favoris, String received_at) {
        this.id = id;
        this.phone = phone;
        this.message = message;
        this.read = read;
        this.favoris = favoris;
        this.received_at = received_at;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isFavoris() {
        return favoris;
    }

    public String getReceived_at() {
        return received_at;
    }
}
