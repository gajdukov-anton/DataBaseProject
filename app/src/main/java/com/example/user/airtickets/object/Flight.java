package com.example.user.airtickets.object;

public class Flight {
    protected String point_of_departure;
    protected String point_of_destination;
    protected String time_of_departure;
    protected String time_of_destination;
   protected String name_of_company;
    protected String timeInTravel;
    protected int image;
    protected double price = 2500;

    public Flight() {}

    public Flight(String point_of_departure,String point_of_destination, String name_of_company) {
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

    public double getPrice() {
        return price;
    }

    public String getStrPrice() {
        return Double.toString(price);
    }

    public void setTimeInTravel(String timeInTravel) {
        this.timeInTravel = timeInTravel;
    }

    public void setPrice(double price) {
        this.price = price;
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
