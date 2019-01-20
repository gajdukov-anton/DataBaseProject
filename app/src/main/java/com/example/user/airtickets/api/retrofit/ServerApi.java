package com.example.user.airtickets.api.retrofit;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.airtickets.activity.user.AllOrdersActivity;
import com.example.user.airtickets.activity.user.Authorization;
import com.example.user.airtickets.activity.user.ProfileActivity;
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
    private PostConfirmOrderToServerListener postConfirmOrderToServerListener;
    private PostRejectOrderToServerListener postRejectOrderToServerListener;
    private CheckStatusTicketListener checkStatusTicketListener;
    private GetAllClassesListener getAllClassesListener;
    private GetAllAirportsListener getAllAirportsListener;
    private GetAllCompaniesListener getAllCompaniesListener;
    private GetAllPlanesListener getAllPlanesListener;
    private DeleteFlightListener deleteFlightListener;
    private DeleteTicketListener deleteTicketListener;
    private GetAllTicketListener getAllTicketListener;
    private AddAirportListener addAirportListener;
    private AddClassListener addClassListener;
    private AddCompanyListener addCompanyListener;
    private AddPlaneListener addPlaneListener;
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

        void onFailure(String message);
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

        void onFailure(String message);
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

    public interface PostConfirmOrderToServerListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface PostRejectOrderToServerListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface CheckStatusTicketListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface GetAllClassesListener {
        void onSuccessful(List<Class> classes);

        void onFailure(String message);
    }

    public interface GetAllPlanesListener {
        void onSuccessful(List<Plane> planes);

        void onFailure(String message);
    }

    public interface GetAllCompaniesListener {
        void onSuccessful(List<Company> companies);

        void onFailure(String message);
    }

    public interface GetAllAirportsListener {
        void onSuccessful(List<Airport> airports);

        void onFailure(String message);
    }

    public interface DeleteFlightListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface DeleteTicketListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface GetAllTicketListener {
        void onSuccessful(List<Ticket> tickets);

        void onFailure(String message);
    }

    public interface AddClassListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface AddPlaneListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface AddAirportListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public interface AddCompanyListener {
        void onSuccessful(ResponseFromServer responseFromServer);

        void onFailure(String message);
    }

    public void setAddAirportListener(AddAirportListener addAirportListener) {
        this.addAirportListener = addAirportListener;
    }

    public void setAddClassListener(AddClassListener addClassListener) {
        this.addClassListener = addClassListener;
    }

    public void setAddCompanyListener(AddCompanyListener addCompanyListener) {
        this.addCompanyListener = addCompanyListener;
    }

    public void setAddPlaneListener(AddPlaneListener addPlaneListener) {
        this.addPlaneListener = addPlaneListener;
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

    public void setPostConfirmOrderToServerListener(PostConfirmOrderToServerListener postConfirmOrderToServerListener) {
        this.postConfirmOrderToServerListener = postConfirmOrderToServerListener;
    }

    public void setDownLoadFlightsByLocationListener(DownLoadFlightsByLocationListener downLoadFlightsByLocation) {
        this.downLoadFlightsByLocationListener = downLoadFlightsByLocation;
    }

    public void setPostRejectOrderToServerListener(PostRejectOrderToServerListener postRejectOrderToServerListener) {
        this.postRejectOrderToServerListener = postRejectOrderToServerListener;
    }

    public void setCheckStatusTicketListener(CheckStatusTicketListener checkStatusTicketListener) {
        this.checkStatusTicketListener = checkStatusTicketListener;
    }

    public void setGetAllClassesListener(GetAllClassesListener getAllClassesListener) {
        this.getAllClassesListener = getAllClassesListener;
    }

    public void setGetAllAirportsListener(GetAllAirportsListener getAllAirportsListener) {
        this.getAllAirportsListener = getAllAirportsListener;
    }

    public void setGetAllCompaniesListener(GetAllCompaniesListener getAllCompaniesListener) {
        this.getAllCompaniesListener = getAllCompaniesListener;
    }

    public void setGetAllPlanesListener(GetAllPlanesListener getAllPlanesListener) {
        this.getAllPlanesListener = getAllPlanesListener;
    }

    public void setDeleteFlightListener(DeleteFlightListener deleteFlightListener) {
        this.deleteFlightListener = deleteFlightListener;
    }

    public void setDeleteTicketListener(DeleteTicketListener deleteTicketListener) {
        this.deleteTicketListener = deleteTicketListener;
    }

    public void setGetAllTicketListener(GetAllTicketListener getAllTicketListener) {
        this.getAllTicketListener = getAllTicketListener;
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
                    authorizationListener.onAuthenticatedUser(info);
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                authorizationListener.onFailure("Не удалось соединиться с сервером");
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

    public void downloadTickets(final AppCompatActivity flightActivity, final int idFlight) {
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
                downloadTicketsListener.onFailure(t.getMessage());
                //Toast.makeText(flightActivity, "Билеты отсутствуют", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void downloadUser(final ProfileActivity profileActivity, final UserData userData) {
        Call<User> userCall = api.downloadUser(userData.login, userData.password);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
                editUserListener.onFailure("Не удалось соединиться с сервером");
            }
        });
    }

    public void postBookingToServer(final Booking booking) {
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
                postBookingListener.onFailure(t.getMessage());
                //Toast.makeText(bookingActivity, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public void downloadOrdersFromServer(final AllOrdersActivity allOrdersActivity, final UserData userData) {
        Call<List<Order>> call = api.downloadOrders(userData.login, userData.password);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    downloadOrdersListener.onDownloadedOrders(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                downloadOrdersListener.onFailure(t.getMessage());
                //Toast.makeText(allOrdersActivity, "Не удалось соединиться с сервером", Snackbar.LENGTH_LONG).show();
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

    public void postConfirmOrderToServer(final int idBooking) {
        Call<ResponseFromServer> call = api.postConFirmOrderToServer(String.valueOf(idBooking));
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    postConfirmOrderToServerListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                postConfirmOrderToServerListener.onFailure(t.getMessage());
            }
        });
    }

    public void postRejectOrderToServer(final int idBooking, String login, String password) {
        Call<ResponseFromServer> call = api.postRejectOrderToServer(String.valueOf(idBooking), login, password);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    postRejectOrderToServerListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                postRejectOrderToServerListener.onFailure(t.getMessage());
            }
        });
    }


    public void checkStatusTicket(final int idTicket) {
        Call<ResponseFromServer> call = api.checkStatusTicket(String.valueOf(idTicket));
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    checkStatusTicketListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                checkStatusTicketListener.onFailure(t.getMessage());
            }
        });
    }

    public void getAllClasses() {
        Call<List<Class>> call = api.getAllClassesFromServer();
        call.enqueue(new Callback<List<Class>>() {
            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                if (response.isSuccessful()) {
                    getAllClassesListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {
                getAllClassesListener.onFailure(t.getMessage());
            }
        });
    }

    public void getAllPlanes() {
        Call<List<Plane>> call = api.getAllPlanesFromServer();
        call.enqueue(new Callback<List<Plane>>() {
            @Override
            public void onResponse(Call<List<Plane>> call, Response<List<Plane>> response) {
                if (response.isSuccessful()) {
                    getAllPlanesListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Plane>> call, Throwable t) {
                getAllPlanesListener.onFailure(t.getMessage());
            }
        });
    }

    public void getAllCompanies() {
        Call<List<Company>> call = api.getAllCompaniesFromServer();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    getAllCompaniesListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                getAllCompaniesListener.onFailure(t.getMessage());
            }
        });
    }

    public void getAllAirports() {
        Call<List<Airport>> call = api.getAllAirportsFromServer();
        call.enqueue(new Callback<List<Airport>>() {
            @Override
            public void onResponse(Call<List<Airport>> call, Response<List<Airport>> response) {
                if (response.isSuccessful()) {
                    getAllAirportsListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Airport>> call, Throwable t) {
                getAllAirportsListener.onFailure(t.getMessage());
            }
        });
    }

    public void deleteFlight(int idFlight) {
        Call<ResponseFromServer> call = api.deleteFlight(String.valueOf(idFlight));
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    deleteFlightListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                deleteFlightListener.onFailure(t.getMessage());
            }
        });
    }

    public void deleteTicket(int idTicket) {
        Call<ResponseFromServer> call = api.deleteTicket(String.valueOf(idTicket));
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    deleteTicketListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                deleteTicketListener.onFailure(t.getMessage());
            }
        });
    }

    public void getAllTicket(String login, String password, int id) {
        Call<List<Ticket>> call = api.getAllTicketForFlight(login, password, String.valueOf(id));
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    getAllTicketListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                getAllTicketListener.onFailure(t.getMessage());
            }
        });
    }

    public void addClass(Class newClass) {
        Call<ResponseFromServer> call = api.addClass(newClass);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    addClassListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                addClassListener.onFailure(t.getMessage());
            }
        });
    }

    public void addPlane(Plane plane) {
        Call<ResponseFromServer> call = api.addPlane(plane);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    addPlaneListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                addPlaneListener.onFailure(t.getMessage());
            }
        });
    }

    public  void addAirport(Airport airport) {
        Call<ResponseFromServer> call = api.addAirport(airport);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    addAirportListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                addAirportListener.onFailure(t.getMessage());
            }
        });
    }

    public void addCompany(Company company) {
        Call<ResponseFromServer> call = api.addCompany(company);
        call.enqueue(new Callback<ResponseFromServer>() {
            @Override
            public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                if (response.isSuccessful()) {
                    addCompanyListener.onSuccessful(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                addCompanyListener.onFailure(t.getMessage());
            }
        });
    }
}
