package com.example.myapplication.Models;

public class Profil {

    private String first_name;
    private String last_name;
    private String password;
    private String picture;
    private String town;

    public Profil(String first_name, String last_name, String password, String picture, String town) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.picture = picture;
        this.town = town;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
