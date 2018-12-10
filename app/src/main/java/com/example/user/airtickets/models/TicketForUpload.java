package com.example.user.airtickets.models;

public class TicketForUpload {
    private int idFlight = -1;
    private int idClass = -1;
    private double price = -1.0;
    private String description = "";
    private int placeNumber = -1;

    public TicketForUpload() {

    }

    public boolean isReadyToUpload() {
        if (idFlight != -1 && idClass != -1
                && price != -1.0 && !description.equals("") && placeNumber != -1) {
            return true;
        } else {
            return false;
        }
    }

    public String getDescription() {
        return description;
    }

    public int getIdClass() {
        return idClass;
    }

    public double getPrice() {
        return price;
    }

    public int getIdFlight() {
        return idFlight;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public void setIdClass(String idClass) {
        try {
            this.idClass = Integer.valueOf(idClass);
        } catch (NumberFormatException exception) {
            this.idClass = -1;
        }
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        try {
            this.placeNumber = Integer.valueOf(placeNumber);
        } catch (NumberFormatException exception) {
            this.placeNumber = -1;
        }
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrice(String price) {
        try {
            this.price = Double.valueOf(price);
        } catch (NumberFormatException exception) {
            this.price = -1.0;
        }
    }

}
