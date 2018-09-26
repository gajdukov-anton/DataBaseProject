package com.example.user.ticketbooking;

public class Flight {
    private String point_of_departure;
    private String point_of_destination;
    private String time_of_departure;
    private String time_of_destination;
    private String id_plane;
    private String name_of_company;
    private String timeInTravel;
    private int image;
    private double price = 2500;

    Flight(String point_of_departure,String point_of_destination, String name_of_company) {
        this.point_of_departure = point_of_departure;
        this.point_of_destination = point_of_destination;
        this.name_of_company = name_of_company;
        this.timeInTravel = "2 часа";
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

    public void setTimeInTravel(String timeInTravel) {
        this.timeInTravel = timeInTravel;
    }

    public double getPrice() {
        return price;
    }

    public String getStrPrice() {
        return Double.toString(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
