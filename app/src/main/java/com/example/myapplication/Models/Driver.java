package com.example.myapplication.Models;

public class Driver {
    private String id;
    private long ttl;
    private String created_at;
    private String number;
    private int userId;
    private boolean signedup;
    private Country country;
    private Profil profil;

    public Driver(String id, long ttl, String created_at, String number, int userId, boolean signedup, Country country, Profil profil) {
        this.id = id;
        this.ttl = ttl;
        this.created_at = created_at;
        this.number = number;
        this.userId = userId;
        this.signedup = signedup;
        this.country = country;
        this.profil = profil;
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

    public String getNumber() {
        return number;
    }

    public boolean isSignedup() {
        return signedup;
    }

    public Country getCountry() {
        return country;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSignedup(boolean signedup) {
        this.signedup = signedup;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}
