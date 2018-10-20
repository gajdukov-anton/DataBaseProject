package com.example.user.airtickets.api;

import com.example.user.airtickets.object.NewUser;
import com.example.user.airtickets.object.ResponseFromServer;
import com.example.user.airtickets.object.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerApi {
    @POST("/login")
    Call<ResponseFromServer> postBook(@Body User user);

    @POST("/register")
    Call<ResponseFromServer> postNewUser(@Body NewUser user);
}
