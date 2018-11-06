package com.example.user.airtickets.api;

import com.example.user.airtickets.object.Flight;
import com.example.user.airtickets.object.NewUser;
import com.example.user.airtickets.object.ResponseFromServer;
import com.example.user.airtickets.object.Ticket;
import com.example.user.airtickets.object.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<ResponseFromServer> postNewUser(@Body NewUser user);
}
