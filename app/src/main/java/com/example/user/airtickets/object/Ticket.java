package com.example.user.airtickets.object;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class Ticket extends Flight  implements Parcelable {
    private String name;
    private String secondName;



    public Ticket(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
    }


    public Ticket(String name, String secondName, Flight flight) {
        this.name = name;
        this.secondName = secondName;
        this.price = flight.price;
        this.timeInTravel = flight.timeInTravel;
        this.time_of_destination = flight.time_of_destination;
        this.time_of_departure = flight.time_of_departure;
        this.point_of_destination = flight.point_of_destination;
        this.point_of_departure = flight.point_of_departure;
        this.name_of_company = flight.name_of_company;
        this.image = flight.image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("secondName", secondName);
        bundle.putInt("image", image);
        bundle.putString("name_of_company", name_of_company);
        bundle.putString("point_of_departure", point_of_departure);
        bundle.putString("point_of_destination", point_of_destination);
        bundle.putString("time_of_departure", time_of_departure);
        bundle.putString("time_of_destination", time_of_destination);
        bundle.putString("timeInTravel", timeInTravel);
        bundle.putDouble("price", price);

        //bundle.putParcelable("flight", flight);
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
        this.name = bundle.getString("name");
        this.secondName = bundle.getString("secondName");
//        setImage(bundle.getInt("image"));
//        setName_of_company(bundle.getString("name_of_company"));
//        setPoint_of_departure(bundle.getString("point_of_departure"));
//        setPoint_of_destination(bundle.getString("point_of_destination"));
//        setTime_of_departure(bundle.getString("time_of_departure"));
//        setTime_of_destination("time_of_destination");
//        setTimeInTravel(bundle.getString("timeInTravel"));
//        setPrice(bundle.getDouble("price"));


        this.image = bundle.getInt("image");
        this.name_of_company = bundle.getString("name_of_company");
        this.point_of_departure = bundle.getString("point_of_departure");
        this.point_of_destination = bundle.getString("point_of_destination");
        this.time_of_departure = bundle.getString("time_of_departure");
        this.time_of_destination = bundle.getString("time_of_destination");
        this.timeInTravel = bundle.getString("timeInTravel");
        this.price = bundle.getDouble("price");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }


}
