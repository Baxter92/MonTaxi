package com.example.myapplication.Models;

public class Country {
    private String flag;
    private String country_code;
    private String alpha3code;
    private String label;
    private String label_fr;

    public Country() {
    }

    public Country(String flag, String country_code, String alpha3code, String label, String label_fr) {
        this.flag = flag;
        this.country_code = country_code;
        this.alpha3code = alpha3code;
        this.label = label;
        this.label_fr = label_fr;
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

    public String getLabel() {
        return label;
    }

    public String getLabel_fr() {
        return label_fr;
    }
}
