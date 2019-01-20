package com.example.user.airtickets.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Ticket  implements Parcelable {
    private String firstName;
    private String lastName;
    private String sex = "female";
    private String dateOfBirth = "12.08.12";
    private String name;
    private int idTicket = -1;
    private int idClass = -1;
    private int idFlight = -1;
    private String classDescription;
    private String ticketDescription;
    private int placeNumber;
    private double price = -1;
    private String classTicket;
    private String pointOfDeparture = "";
    private String pointOfDestination = "";
    private String isBooked;

    public Ticket() {

    }

    public Ticket(String name, String secondName) {
        this.firstName = name;
        this.lastName = secondName;
    }

    public Ticket(int placeNumber, double price, String classTicket) {
        this.placeNumber = placeNumber;
        this.price = price;
        this.classTicket = classTicket;
    }

    public Ticket(String name, String secondName, Flight flight) {
        this.firstName = name;
        this.lastName = secondName;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("firstName", firstName);
        bundle.putString("lastName", lastName);
        bundle.putString("name", name);
        bundle.putInt("idTicket", idTicket);
        bundle.putString("classDescription", classDescription);
        bundle.putString("ticketDescription", ticketDescription);
        bundle.putInt("placeNumber", placeNumber);
        bundle.putDouble("price", price);
        bundle.putString("classTicket", classTicket);
        bundle.putString("pointOfDeparture", pointOfDeparture);
        bundle.putString("pointOfDestination", pointOfDestination);
        bundle.putString("sex", sex);
        bundle.putString("dateOfBirth", dateOfBirth);
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
        this.firstName = bundle.getString("firstName");
        this.lastName = bundle.getString("lastName");
        this.name = bundle.getString("name");
        this.idTicket = bundle.getInt("idTicket");
        this.classDescription = bundle.getString("classDescription");
        this.ticketDescription = bundle.getString("ticketDescription");
        this.placeNumber = bundle.getInt("placeNumber");
        this.price = bundle.getDouble("price");
        this.classTicket = bundle.getString("classTicket");
        this.pointOfDeparture = bundle.getString("pointOfDeparture");
        this.pointOfDestination = bundle.getString("pointOfDestination");
        this.sex = bundle.getString("sex");
        this.dateOfBirth = bundle.getString("dateOfBirth");
    }

    public boolean isReadyToUpload() {
        if (idClass != -1 && price != -1.0 &&
                placeNumber != -1 && classDescription != null) {
            return true;
        } else {
            return false;
        }
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

    public void setIdClass(String idClass) {
        try {
            this.idClass = Integer.valueOf(idClass);
        } catch (NumberFormatException exception) {
            this.idClass = -1;
        }
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrice(String price) {
        try {
            this.price = Double.valueOf(price);
        } catch (NumberFormatException exception) {
            this.price = -1;
        }
    }

    public void setClassTicket(String classTicket) {
        this.classTicket = classTicket;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }

    public void setNumberTicket(String numberTicket) {
        try {
            this.placeNumber = Integer.valueOf(numberTicket);
        } catch (NumberFormatException exception) {
            this.placeNumber = -1;
        }
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public void setPointOfDestination(String pointOfDestination) {
        this.pointOfDestination = pointOfDestination;
    }

    public String getIsBooked() {
        if (isBooked.equals("1")) {
            return "заказан";
        } else if (isBooked.equals("0")) {
            return  "свободен";
        } else {
            return isBooked;
        }
    }

    public void setIsBooked(String isBooked) {
        if (isBooked.equals("1")) {
            this.isBooked = "заказан";
        } else if (isBooked.equals("0")) {
            this.isBooked = "свободен";
        } else {
            this.isBooked = isBooked;
        }
    }

    public double getPrice() {
        return price;
    }

    public String getClassTicket() {
        return classTicket;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public int getIdClass() {
        return idClass;
    }

    public int getIdFlight() {
        return idFlight;
    }
}
