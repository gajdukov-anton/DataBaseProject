package com.example.user.airtickets.api.retrofit;

import com.example.user.airtickets.object.Booking;
import com.example.user.airtickets.object.Flight;
import com.example.user.airtickets.object.Order;
import com.example.user.airtickets.object.User;
import com.example.user.airtickets.object.ResponseFromServer;
import com.example.user.airtickets.object.Ticket;
import com.example.user.airtickets.object.UserData;

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

    @POST("/login")
    Call<ResponseFromServer> postUserData(@Body UserData user);

    @POST("/register")
    Call<ResponseFromServer> postNewUser(@Body User user);

    @FormUrlEncoded
    @POST("/user/get")
    Call<User> downloadUser(@Field("login") String login, @Field("password") String password);

    @POST("/user/change")
    Call<ResponseFromServer> postEditUser(@Body User user);

    @POST("/Book-tickets")
    Call<ResponseFromServer> postBooking(@Body Booking booking);

    @POST("//")
    Call<List<Order>> downloadOrders(@Body UserData userData);
}
