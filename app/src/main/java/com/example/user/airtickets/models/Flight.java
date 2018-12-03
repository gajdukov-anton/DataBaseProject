package com.example.user.airtickets.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Flight implements Parcelable {
    protected String point_of_departure;
    protected String point_of_destination;
    protected String time_of_departure;
    protected String time_of_destination;
    protected String name_of_company;
    protected String timeInTravel;
    protected int image;
    protected int flightId = 1;

    public Flight() {
    }

    public Flight(String point_of_departure, String point_of_destination, String name_of_company) {
        this.point_of_departure = point_of_departure;
        this.point_of_destination = point_of_destination;
        this.name_of_company = name_of_company;
        this.timeInTravel = "2 часа";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("point_of_departure", point_of_departure);
        bundle.putString("point_of_destination", point_of_destination);
        bundle.putString("time_of_departure", time_of_departure);
        bundle.putString("time_of_destination", time_of_destination);
        bundle.putString("name_of_company", name_of_company);
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
        this.point_of_departure = bundle.getString("point_of_departure");
        this.point_of_destination = bundle.getString("point_of_destination");
        this.time_of_departure = bundle.getString("time_of_departure");
        this.time_of_destination = bundle.getString("time_of_destination");
        this.name_of_company = bundle.getString("name_of_company");
        this.timeInTravel = bundle.getString("timeInTravel");
    }

    public String getPoint_of_departure() {
        return point_of_departure;
    }

    public int getImage() {
        return image;
    }

    public String getPoint_of_destination() {
        return point_of_destination;
    }

    public String getTime_of_departure() {
        return time_of_departure;
    }

    public String getTime_of_destination() {
        return time_of_destination;
    }

    public String getName_of_company() {
        return name_of_company;
    }

    public String getTimeInTravel() {
        return timeInTravel;
    }



    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setTimeInTravel(String timeInTravel) {
        this.timeInTravel = timeInTravel;
    }


    public void setImage(int image) {
        this.image = image;
    }

    public void setName_of_company(String name_of_company) {
        this.name_of_company = name_of_company;
    }

    public void setPoint_of_departure(String point_of_departure) {
        this.point_of_departure = point_of_departure;
    }

    public void setPoint_of_destination(String point_of_destination) {
        this.point_of_destination = point_of_destination;
    }

    public void setTime_of_departure(String time_of_departure) {
        this.time_of_departure = time_of_departure;
    }

    public void setTime_of_destination(String time_of_destination) {
        this.time_of_destination = time_of_destination;
    }
}
