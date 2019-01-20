package com.example.user.airtickets.models;

import android.widget.Toast;

import java.util.Date;

public class FlightForUpload {
    private int idPlane = -1;
    private int idAirport = -1;
    private String pointOfDeparture = "";
    private String description = "";
    private String pointOfDestination = "";
    private Date timeOfDeparture;
    private Date timeOfDestination;

    public FlightForUpload() {

    }

    public boolean isAllMethodsFilled() {
        if (idAirport != -1 && idPlane != -1
                && !pointOfDeparture.equals("") && !pointOfDestination.equals("") && timeOfDeparture != null
                && timeOfDestination != null) {
            return true;
        } else {
            return false;
        }
    }

    public int getIdAirport() {
        return idAirport;
    }

    public int getIdPlane() {
        return idPlane;
    }

    public String getDescription() {
        return description;
    }

    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public String getPointOfDestination() {
        return pointOfDestination;
    }

    public Date getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public Date getTimeOfDestination() {
        return timeOfDestination;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdAirport(int idAirport) {
        this.idAirport = idAirport;
    }

    public void setIdAirport(String idAirport) {
        try {
            this.idAirport = Integer.valueOf(idAirport);
        } catch (NumberFormatException exception) {
            this.idAirport = -1;
        }
    }

    public void setIdPlane(int idPlane) {
        this.idPlane = idPlane;
    }

    public void setIdPlane(String idPlane) {
        try {
            this.idPlane = Integer.valueOf(idPlane);
        } catch (NumberFormatException exception) {
            this.idPlane = -1;
        }
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public void setPointOfDestination(String pointOfDestination) {
        this.pointOfDestination = pointOfDestination;
    }

    public void setTimeOfDeparture(Date timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public void setTimeOfDestination(Date timeOfDestination) {
        this.timeOfDestination = timeOfDestination;
    }
}
