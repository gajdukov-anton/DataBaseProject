package com.example.user.airtickets.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Flight implements Parcelable {
    protected String pointOfDeparture;
    protected String pointOfDestination;
    protected String timeOfDeparture;
    protected String timeOfDestination;
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
        bundle.putString("timeOfDeparture", timeOfDeparture);
        bundle.putString("timeOfDestination", timeOfDestination);
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
        this.timeOfDeparture = bundle.getString("timeOfDeparture");
        this.timeOfDestination = bundle.getString("timeOfDestination");
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

    public String getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public String getTimeOfDestination() {
        return timeOfDestination;
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

    public void setTimeOfDeparture(String timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public void setTimeOfDestination(String timeOfDestination) {
        this.timeOfDestination = timeOfDestination;
    }

    public void setIdPlane(int idPlane) {
        this.idPlane = idPlane;
    }

    public void setAirportId(int airportId) {
        this.idAirport = airportId;
    }
}
