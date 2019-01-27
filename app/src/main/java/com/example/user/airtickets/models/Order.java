package com.example.user.airtickets.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Order{
    private int idBooking;
    private Date date;
    private String idUser;
    private String status;
    private int cardNumber;
    private double bookingPrice;

    public Order() {

    }

    public Order(Date date, String status, int cardNumber, double bookingPrice) {
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        Locale local = new Locale("ru","RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
        return df.format(date);
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
