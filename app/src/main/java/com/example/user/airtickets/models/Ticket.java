package com.example.user.airtickets.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Ticket  implements Parcelable {
    private String userName;
    private String userSecondName;
    private String name;
    private int idTicket;
    private String classDescription;
    private String ticketDescription;
    private int numberTicket;
    private double price;
    private String classTicket;
    private String pointOfDeparture = "";
    private String pointOfDestination = "";



    public Ticket(String name, String secondName) {
        this.userName = name;
        this.userSecondName = secondName;
    }

    public Ticket(int numberTicket, double price, String classTicket) {
        this.numberTicket = numberTicket;
        this.price = price;
        this.classTicket = classTicket;
    }

    public Ticket(String name, String secondName, Flight flight) {
        this.userName = name;
        this.userSecondName = secondName;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("userSecondName", userSecondName);
        bundle.putString("name", name);
        bundle.putInt("idTicket", idTicket);
        bundle.putString("classDescription", classDescription);
        bundle.putString("ticketDescription", ticketDescription);
        bundle.putInt("numberTicket", numberTicket);
        bundle.putDouble("price", price);
        bundle.putString("classTicket", classTicket);
        bundle.putString("pointOfDeparture", pointOfDeparture);
        bundle.putString("pointOfDestination", pointOfDestination);
        parcel.writeBundle(bundle);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public Ticket(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        this.userName = bundle.getString("userName");
        this.userSecondName = bundle.getString("userSecondName");
        this.name = bundle.getString("name");
        this.idTicket = bundle.getInt("idTicket");
        this.classDescription = bundle.getString("classDescription");
        this.ticketDescription = bundle.getString("ticketDescription");
        this.numberTicket = bundle.getInt("numberTicket");
        this.price = bundle.getDouble("price");
        this.classTicket = bundle.getString("classTicket");
        this.pointOfDeparture = bundle.getString("pointOfDeparture");
        this.pointOfDestination = bundle.getString("pointOfDestination");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setClassTicket(String classTicket) {
        this.classTicket = classTicket;
    }

    public void setNumberTicket(int numberTicket) {
        this.numberTicket = numberTicket;
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public void setPointOfDestination(String pointOfDestination) {
        this.pointOfDestination = pointOfDestination;
    }

    public double getPrice() {
        return price;
    }

    public String getClassTicket() {
        return classTicket;
    }

    public int getNumberTicket() {
        return numberTicket;
    }

    public void setUserSecondName(String secondName) {
        this.userSecondName = secondName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public String getName() {
        return name;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public String getPointOfDestination() {
        return pointOfDestination;
    }
}
