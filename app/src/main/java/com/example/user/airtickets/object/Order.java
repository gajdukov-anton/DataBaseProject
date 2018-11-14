package com.example.user.airtickets.object;

public class Order {
    private String idBooking;
    private String date;
    private String idUser;
    private String status;
    private String numberCard;

    public Order () {

    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdBooking(String idBooking) {
        this.idBooking = idBooking;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getIdBooking() {
        return idBooking;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNumberCard() {
        return numberCard;
    }
}
