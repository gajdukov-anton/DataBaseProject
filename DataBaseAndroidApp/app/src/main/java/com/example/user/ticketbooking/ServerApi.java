package com.example.user.ticketbooking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerApi {
    @POST("/login")
    Call<ResponseFromServer> postBook(@Body User user);
}
