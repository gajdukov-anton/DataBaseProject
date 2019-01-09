package com.example.user.airtickets.api.retrofit;

import com.example.user.airtickets.models.Airport;
import com.example.user.airtickets.models.Booking;
import com.example.user.airtickets.models.Class;
import com.example.user.airtickets.models.Company;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.FlightForUpload;
import com.example.user.airtickets.models.Order;
import com.example.user.airtickets.models.Plane;
import com.example.user.airtickets.models.TicketForUpload;
import com.example.user.airtickets.models.User;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.Ticket;
import com.example.user.airtickets.models.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("/flights")
    Call<List<Flight>> downloadFlights();

    @GET("/flight/tickets")
    Call<List<Ticket>> downloadTickets(@Query("idFlight") int idFlight);

    @GET("/search-flights-by-two-cities")
    Call<List<Flight>> downloadFlights(@Query("pointOfDestination") String pointOfDestination, @Query("pointOfDeparture") String pointOfDeparture);

    @GET("/search-flights-by-city")
    Call<List<Flight>> downloadFlights(@Query("city") String city);

    @POST("/login")
    Call<ResponseFromServer> postUserData(@Body UserData user);

    @POST("/register")
    Call<ResponseFromServer> postNewUser(@Body User user);

    @FormUrlEncoded
    @POST("/user/get")
    Call<User> downloadUser(@Field("login") String login, @Field("password") String password);

    @POST("/user/change")
    Call<ResponseFromServer> postEditUser(@Body User user);

    @POST("/book-tickets")
    Call<ResponseFromServer> postBooking(@Body Booking booking);

    @POST("/admin/add-flight")
    Call<ResponseFromServer> postNewFlight(@Body FlightForUpload flight);

    @GET("/user/booking")
    Call<List<Order>> downloadOrders(@Query("login") String login, @Query("password") String password);

    @POST("/admin/add-ticket")
    Call<ResponseFromServer> postNewTicket(@Body TicketForUpload ticket);

    @FormUrlEncoded
    @POST("/booking/confirm")
    Call<ResponseFromServer> postConFirmOrderToServer(@Field("idBooking") String idBooking);

    @FormUrlEncoded
    @POST("/reject/booking")
    Call<ResponseFromServer> postRejectOrderToServer(@Field("idBooking") String idBooking, @Field("login") String login, @Field("password") String password);

    @GET("/ticket/check-status")
    Call<ResponseFromServer> checkStatusTicket(@Query("idTicket") String idTicket);

    @GET("/planes")
    Call<List<Plane>> getAllPlanesFromServer();

    @GET("/classes")
    Call<List<Class>> getAllClassesFromServer();

    @GET("/airports")
    Call<List<Airport>> getAllAirportsFromServer();

    @GET("/companies")
    Call<List<Company>> getAllCompaniesFromServer();
}
