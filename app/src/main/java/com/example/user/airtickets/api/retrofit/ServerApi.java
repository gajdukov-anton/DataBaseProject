package com.example.user.airtickets.api.retrofit;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.airtickets.activity.AllOrdersActivity;
import com.example.user.airtickets.activity.Authorization;
import com.example.user.airtickets.activity.BookingActivity;
import com.example.user.airtickets.activity.FlightActivity;
import com.example.user.airtickets.activity.ProfileActivity;
import com.example.user.airtickets.models.Booking;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.FlightForUpload;
import com.example.user.airtickets.models.Order;
import com.example.user.airtickets.models.TicketForUpload;
import com.example.user.airtickets.models.User;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.Ticket;
import com.example.user.airtickets.models.UserData;

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
    private DownloadUserListener downloadUserListener;
    private EditUserListener editUserListener;
    private PostBookingListener postBookingListener;
    private DownloadOrdersListener downloadOrdersListener;
    private UploadNewFlightListener uploadNewFlightListener;
    private UploadNewTicketListener uploadNewTicketListener;
    private DownLoadFlightsByLocationListener downLoadFlightsByLocationListener;
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

    public interface DownloadUserListener {
        void onDownloadUser(User user);
    }

    public interface EditUserListener {
        void onUploadEditUser(ResponseFromServer responseFromServer);

        void onFailure(String require);
    }

    public interface PostBookingListener {
        void onUploadBooking(ResponseFromServer responseFromServer);

        void onFailure(String request);
    }

    public interface DownloadOrdersListener {
        void onDownloadedOrders(List<Order> orders);
    }

    public interface UploadNewFlightListener {
        void onUploadNewFlight(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface UploadNewTicketListener {
        void onUploadNewTicketListener(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface DownLoadFlightsByLocationListener {
        void onDownloadFlights(List<Flight> flights);

        void onFailure(String message);
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

    public void setDownloadUserListener(DownloadUserListener downloadUserListener) {
        this.downloadUserListener = downloadUserListener;
    }

    public void setEditUserListener(EditUserListener editUserListener) {
        this.editUserListener = editUserListener;
    }

    public void setPostBookingListener(PostBookingListener postBookingListener) {
        this.postBookingListener = postBookingListener;
    }

    public void setDownloadOrdersListener(DownloadOrdersListener downloadOrdersListener) {
        this.downloadOrdersListener = downloadOrdersListener;
    }

    public void setUploadNewFlightListener(UploadNewFlightListener uploadNewFlightListener) {
        this.uploadNewFlightListener = uploadNewFlightListener;
    }

    public void setUploadNewTicketListener(UploadNewTicketListener uploadNewTicketListener) {
        this.uploadNewTicketListener = uploadNewTicketListener;
    }

    public void setDownLoadFlightsByLocationListener(DownLoadFlightsByLocationListener downLoadFlightsByLocation) {
        this.downLoadFlightsByLocationListener = downLoadFlightsByLocation;
    }

    private ServerApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pure-taiga-64408.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public void uploadNewUserToServer(final User user, final AppCompatActivity appCompatActivity) {
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
                Toast.makeText(appCompatActivity, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
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

    public void downloadFlights(final AppCompatActivity appCompatActivity) {

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
                Toast.makeText(appCompatActivity, "Не удалось соединиться с сервером", Toast.LENGTH_SHORT).show();
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

    public void downloadUser(final ProfileActivity profileActivity, final UserData userData) {
        Call<User> userCall = api.downloadUser(userData.login, userData.password);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Toast.makeText(profileActivity, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    User user = response.body();

                    downloadUserListener.onDownloadUser(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(profileActivity, "Не удалось соединиться с сервером", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadEditUserToServer(final User user, final AppCompatActivity appCompatActivity) {
        Call<ResponseFromServer> call = api.postEditUser(user);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(appCompatActivity, response.body().status, Snackbar.LENGTH_LONG).show();
                    ResponseFromServer info = response.body();
                    editUserListener.onUploadEditUser(info);
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                //Toast.makeText(appCompatActivity, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
                editUserListener.onFailure("Не удалось соединиться с сервером");
            }
        });
    }

    public void postBookingToServer(final BookingActivity bookingActivity, final Booking booking) {
        Call<ResponseFromServer> call = api.postBooking(booking);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    //ResponseFromServer
                    postBookingListener.onUploadBooking(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                postBookingListener.onFailure("Не удалось соединиться с сервером");
                //Toast.makeText(bookingActivity, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public void downloadOrdersFromServer(final AllOrdersActivity allOrdersActivity, final UserData userData) {
        Call<List<Order>> call = api.downloadOrders(userData);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    downloadOrdersListener.onDownloadedOrders(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(allOrdersActivity, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void uploadNewFlightToServer(final FlightForUpload flight) {
        Call<ResponseFromServer> call = api.postNewFlight(flight);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    uploadNewFlightListener.onUploadNewFlight(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                uploadNewFlightListener.onFailure(t.getMessage());
            }
        });
    }

    public void uploadNewTicketToServer(final TicketForUpload ticket) {
        Call<ResponseFromServer> call = api.postNewTicket(ticket);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    uploadNewTicketListener.onUploadNewTicketListener(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                uploadNewTicketListener.onFailure(t.getMessage());
            }
        });
    }

    public void downloadFlightsByLocation(final String pointOfDestination, final String pointOfDeparture) {
        Call<List<Flight>> call = api.downloadFlights(pointOfDestination, pointOfDeparture);
        call.enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                if (response.isSuccessful()) {
                    downLoadFlightsByLocationListener.onDownloadFlights(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {
                downLoadFlightsByLocationListener.onFailure(t.getMessage());
            }
        });
    }


    public void downloadFlightsByLocation(final String city) {
        Call<List<Flight>> call = api.downloadFlights(city);
        call.enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                if (response.isSuccessful()) {
                    downLoadFlightsByLocationListener.onDownloadFlights(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {
                downLoadFlightsByLocationListener.onFailure(t.getMessage());
            }
        });
    }
}
