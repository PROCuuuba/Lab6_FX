package com.example.lab6_fx.Model;

public class Country {
    private String name;
    private String landmark;

    public Country(String name, String landmark) {
        this.name = name;
        this.landmark = landmark;
    }

    public String getName() {
        return name;
    }

    public String getLandmark() {
        return landmark;
    }
}