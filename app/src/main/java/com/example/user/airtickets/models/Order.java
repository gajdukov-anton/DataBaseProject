package com.example.user.airtickets.models;

public class Order {
    private int idBooking;
    private String date;
    private String idUser;
    private String status;
    private int cardNumber;
    private double bookingPrice;

    public Order() {

    }

    public Order(String date, String status, int cardNumber, double bookingPrice) {
        this.cardNumber = cardNumber;
        this.date = date;
        this.status = status;
        this.bookingPrice = bookingPrice;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
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

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public String getIdUser() {
        return idUser;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }
}
