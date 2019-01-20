package com.example.user.airtickets.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Flight implements Parcelable {
    protected String pointOfDeparture;
    protected String pointOfDestination;
    protected Date timeOfDeparture;
    protected Date timeOfDestination;
    protected String companyName;
    protected String timeInTravel;
    protected int image;
    protected int idFlight = 1;
    protected int idAirport = -1;
    protected int idPlane = -1;

    public Flight() {
    }

    public Flight(String point_of_departure, String point_of_destination, String name_of_company) {
        this.pointOfDeparture = point_of_departure;
        this.pointOfDestination = point_of_destination;
        this.companyName = name_of_company;
        this.timeInTravel = "2 часа";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("pointOfDeparture", pointOfDeparture);
        bundle.putString("pointOfDestination", pointOfDestination);
        bundle.putLong("timeOfDeparture", timeOfDeparture.getTime());
        bundle.putLong("timeOfDestination", timeOfDestination.getTime());
        bundle.putString("companyName", companyName);
        bundle.putString("timeInTravel", timeInTravel);
        parcel.writeBundle(bundle);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

    public Flight(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        this.pointOfDeparture = bundle.getString("pointOfDeparture");
        this.pointOfDestination = bundle.getString("pointOfDestination");
        this.timeOfDeparture = new Date(bundle.getLong("timeOfDeparture"));
        this.timeOfDestination = new Date(bundle.getLong("timeOfDestination"));
        this.companyName = bundle.getString("companyName");
        this.timeInTravel = bundle.getString("timeInTravel");
    }

    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public int getImage() {
        return image;
    }

    public String getPointOfDestination() {
        return pointOfDestination;
    }

    public Date getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public String getFormattedTimeOfDeparture() {
        Locale local = new Locale("ru","RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
        return df.format(timeOfDeparture);
    }

    public Date getTimeOfDestination() {
        return timeOfDestination;
    }

    public String getFormattedTimeOfDestination() {
        Locale local = new Locale("ru","RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
        return df.format(timeOfDestination);
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTimeInTravel() {
        return timeInTravel;
    }

    public int getIdAirport() {
        return idAirport;
    }

    public int getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }


    public void setTimeInTravel(String timeInTravel) {
        this.timeInTravel = timeInTravel;
    }


    public void setImage(int image) {
        this.image = image;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public void setIdPlane(int idPlane) {
        this.idPlane = idPlane;
    }

    public void setAirportId(int airportId) {
        this.idAirport = airportId;
    }
}
