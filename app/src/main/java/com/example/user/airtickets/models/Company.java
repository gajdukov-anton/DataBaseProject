package com.example.user.airtickets.models;

public class Company {
    private int idCompany;
    private double rating = -1.0;
    private String name = "";

    public Company() {

    }

    public boolean isReadyToUpload() {
        return !name.equals("") && rating != -1.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
