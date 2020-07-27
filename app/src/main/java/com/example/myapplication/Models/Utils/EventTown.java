package com.example.myapplication.Models.Utils;

import com.example.myapplication.Models.Town;

import java.util.List;

public class EventTown {
    private int country_id;
    private List<Town> towns;

    public EventTown(int country_id, List<Town> towns) {
        this.country_id = country_id;
        this.towns = towns;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }
}
