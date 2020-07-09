package com.example.myapplication.Models;

public class Country {
    private String flag;
    private String country_code;
    private String alpha3code;

    public Country() {
    }

    public Country(String flag, String country_code, String alpha3code) {
        this.flag = flag;
        this.country_code = country_code;
        this.alpha3code = alpha3code;
    }

    public String getFlag() {
        return flag;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getAlpha3code() {
        return alpha3code;
    }
}
