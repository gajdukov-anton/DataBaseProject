package com.example.user.airtickets.activity.user;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.fragment.BookingDialogFragment;
import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.FlightAdapter;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BookingDialogFragment.BookingDialogListener, SearchView.OnQueryTextListener {

    private List<Flight> flightList = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private TextView amountTicketInBooking;
    static final String ACCESS_MESSAGE_FLIGHT_ACTIVITY = "ACCESS_MESSAGE_FLIGHT_ACTIVITY";
    static final String ACCESS_MESSAGE_BOOKING_ACTIVITY = "ACCESS_MESSAGE_BOOKING_ACTIVITY";
    private static final int REQUEST_ACCESS_TO_FLIGHT_ACTIVITY = 1;
    private static final int REQUEST_ACCESS_TO_BOOKING_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        createRecyclerViewWithFlights();
        amountTicketInBooking = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_camera));
        initializeAmountTicketInBooking();
    }

    private void createRecyclerViewWithFlights(String pointOfDestination, String pointOfDeparture) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownLoadFlightsByLocationListener listener = new ServerApi.DownLoadFlightsByLocationListener() {
            @Override
            public void onDownloadFlights(List<Flight> flights) {
                createRecyclerView(flights);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, "Не удалось: " + message, Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setDownLoadFlightsByLocationListener(listener);
        serverApi.downloadFlightsByLocation(pointOfDestination, pointOfDeparture);
    }

    private void createRecyclerViewWithFlights(String city) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownLoadFlightsByLocationListener listener = new ServerApi.DownLoadFlightsByLocationListener() {
            @Override
            public void onDownloadFlights(List<Flight> flights) {
                createRecyclerView(flights);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, "Не удалось найти рейс", Toast.LENGTH_LONG).show();
            }
        };
        serverApi.setDownLoadFlightsByLocationListener(listener);
        serverApi.downloadFlightsByLocation(city);
    }

    private void createRecyclerViewWithFlights() {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownloadFlightsListener listener = new ServerApi.DownloadFlightsListener() {
            @Override
            public void onDownloadedFlights(List<Flight> flights) {
                createRecyclerView(flights);
                flightList = flights;
            }
        };
        serverApi.setDownloadFlightsListener(listener);
        serverApi.downloadFlights(this);
    }

    private void createRecyclerView(final List<Flight> flights) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        FlightAdapter adapter = new FlightAdapter(this, flights, null);
        FlightAdapter.Callback adapterListener = new FlightAdapter.Callback() {
            @Override
            public void onMoreButtonClick(int flightId, int positionInList) {
                loadFlightActivity(flightId, flights.get(positionInList));
            }
        };

        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private void initializeAmountTicketInBooking() {
        amountTicketInBooking.setGravity(Gravity.CENTER_VERTICAL);
        amountTicketInBooking.setTypeface(null, Typeface.BOLD);
        amountTicketInBooking.setTextColor(getResources().getColor(R.color.colorAccent));
        amountTicketInBooking.setText("0");
    }

    private void updateAmountTicketInBooking(int amount) {
        amountTicketInBooking.setText(String.valueOf(amount));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ACCESS_TO_FLIGHT_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                ArrayList<Ticket> tempArray = data.getParcelableArrayListExtra(ACCESS_MESSAGE_FLIGHT_ACTIVITY);
                tickets.addAll(tempArray);
                updateAmountTicketInBooking(tickets.size());
            }
        }
        if (requestCode == REQUEST_ACCESS_TO_BOOKING_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                tickets = data.getParcelableArrayListExtra(ACCESS_MESSAGE_BOOKING_ACTIVITY);
                updateAmountTicketInBooking(tickets.size());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        String locations[] = query.split(" ");
//        if (locations.length == 1) {
//            createRecyclerViewWithFlights(locations[0]);
//        } else if (locations.length == 2) {
//            createRecyclerViewWithFlights(locations[0], locations[1]);
//        }
        createRecyclerViewWithFlights(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            //createRecyclerViewWithFlights();
            createRecyclerView(flightList);
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            exitToAuthorization();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBookingActivity() {
        if (tickets.size() != 0) {
            Intent intent = new Intent(this, BookingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("tickets", tickets);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_ACCESS_TO_BOOKING_ACTIVITY);
        } else {
            Toast.makeText(this, "Корзина пуста", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFlightActivity(int flightId, Flight flight) {
        Intent intent = new Intent(this, FlightActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("flight", flight);
        intent.putExtras(bundle);
        intent.putExtra("idFlight", flightId);
        startActivityForResult(intent, REQUEST_ACCESS_TO_FLIGHT_ACTIVITY);
    }

    private void exitToAuthorization() {
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
        this.finish();
    }

    private void loadProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void loadAllOrdersActivity() {
        Intent intent = new Intent(this, AllOrdersActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            loadBookingActivity();
        } else if (id == R.id.nav_profile) {
            loadProfileActivity();
        } else if (id == R.id.nav_slideshow) {
            loadAllOrdersActivity();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_exit_to_authorization) {
            exitToAuthorization();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
