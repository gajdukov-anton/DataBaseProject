package com.example.user.airtickets.models;

public class Airport {
    private int idAirport;
    private String name;
    private String location;

    public Airport() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIdAirport(int idAirport) {
        this.idAirport = idAirport;
    }

    public int getIdAirport() {
        return idAirport;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
