package com.example.user.airtickets.api;

import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.user.airtickets.activity.Authorization;
import com.example.user.airtickets.activity.FlightActivity;
import com.example.user.airtickets.activity.MainActivity;
import com.example.user.airtickets.activity.RegistrationActivity;
import com.example.user.airtickets.object.Flight;
import com.example.user.airtickets.object.NewUser;
import com.example.user.airtickets.object.ResponseFromServer;
import com.example.user.airtickets.object.Ticket;
import com.example.user.airtickets.object.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerApi {
    private RegistrationListener registrationListener;
    private AuthorizationListener authorizationListener;
    private DownloadFlightsListener downloadFlightsListener;
    private DownloadTicketsListener downloadTicketsListener;
    static private Api api;
    static private Retrofit retrofit;

    private static class ServerApiHolder {
        private final static ServerApi instance = new ServerApi();
    }

    public static ServerApi getInstance() {
        return ServerApiHolder.instance;
    }

    public interface RegistrationListener {
        void onNewUserUploaded(ResponseFromServer responseFromServer);
    }

    public interface AuthorizationListener {
        void onAuthenticatedUser(ResponseFromServer responseFromServer);
        void onFailure(String response);
    }

    public interface DownloadFlightsListener {
        void onDownloadedFlights(List<Flight> flights);
    }

    public interface DownloadTicketsListener {
        void onDownloadedTickets(List<Ticket> tickets);
    }

    public void setRegistrationListener(RegistrationListener registrationListener) {
        this.registrationListener = registrationListener;
    }

    public void setAuthorizationListener(AuthorizationListener authorizationListener) {
        this.authorizationListener = authorizationListener;
    }

    public void setDownloadFlightsListener(DownloadFlightsListener downloadFlightsListener) {
        this.downloadFlightsListener = downloadFlightsListener;
    }

    public void setDownloadTicketsListener(DownloadTicketsListener downloadTicketsListener) {
        this.downloadTicketsListener = downloadTicketsListener;
    }

    private ServerApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pure-taiga-64408.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public void uploadNewUserDataToServer(final NewUser user, final RegistrationActivity registrationActivity) {
        Call<ResponseFromServer> call = api.postNewUser(user);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                 //   Toast.makeText(registrationActivity, "Successful", Snackbar.LENGTH_LONG).show();
                    ResponseFromServer info = response.body();
                    registrationListener.onNewUserUploaded(info);
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                Toast.makeText(registrationActivity, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void uploadUserDataToServer(final UserData user, final Authorization authorization) {
        Call<ResponseFromServer> call = api.postUserData(user);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    ResponseFromServer info = response.body();
                  //  Toast.makeText(authorization, info.status, Snackbar.LENGTH_LONG).show();
                    authorizationListener.onAuthenticatedUser(info);
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                authorizationListener.onFailure("Не удалось соединиться с сервером");
                //Toast.makeText(authorization, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void downloadFlights(final MainActivity mainActivity) {

        final Call<List<Flight>> newFlights = api.downloadFlights();
        newFlights.enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                if (response.isSuccessful()) {
                    List<Flight> flights = response.body();
                    downloadFlightsListener.onDownloadedFlights(flights);
                }
            }

            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {
                Toast.makeText(mainActivity, "Не удалось соединиться с сервером", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void downloadTickets(final FlightActivity flightActivity, final int idFlight) {
        final Call<List<Ticket>> newTickets = api.downloadTickets(idFlight);
        newTickets.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    List<Ticket> tickets = response.body();
                    downloadTicketsListener.onDownloadedTickets(tickets);
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(flightActivity, "Не удалось соединиться с сервером", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
