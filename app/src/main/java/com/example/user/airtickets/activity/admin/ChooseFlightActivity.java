package com.example.user.airtickets.activity.admin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.FlightAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.Flight;

import java.util.ArrayList;
import java.util.List;

public class ChooseFlightActivity extends AppCompatActivity {

    private List<Flight> flights = new ArrayList<>();
    private static final String CREATE_ACTION = "CREATE_TICKET";
    private static final String DELETE_ACTION = "DELETE_TICKET";
    private static final String CREATE_TICKET = "СОЗДАТЬ БИЛЕТ";
    private static final String DELETE_TICKET = "УДАЛИТЬ БИЛЕТ";
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_flight);
        Bundle arguments = getIntent().getExtras();
        action = arguments.getString("Action");
        createBackButton();
        createRecyclerViewWithFlights();
    }

    private void createBackButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void createRecyclerViewWithFlights() {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownloadFlightsListener listener = new ServerApi.DownloadFlightsListener() {
            @Override
            public void onDownloadedFlights(List<Flight> flights) {
                createRecyclerView(flights);
            }
        };
        serverApi.setDownloadFlightsListener(listener);
        serverApi.downloadFlights(this);
    }

    private void createRecyclerView(final List<Flight> flights) {
        this.flights = flights;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        FlightAdapter adapter = new FlightAdapter(this, flights, null);
        adapter.setButtonText(getActionNameForButton());
        FlightAdapter.Callback adapterListener = createCallBack();
        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private String getActionNameForButton() {
        if (action.equals(CREATE_ACTION)) {
            return CREATE_TICKET;
        } else {
            return DELETE_TICKET;
        }
    }

    private FlightAdapter.Callback createCallBack() {
        if (action.equals(CREATE_ACTION)) {
            return new FlightAdapter.Callback() {
                @Override
                public void onMoreButtonClick(int flightId, int positionInList) {
                    startCreateTicketActivity(flightId);
                }
            };
        } else {
            return new FlightAdapter.Callback() {
                @Override
                public void onMoreButtonClick(int flightId, int positionInList) {
                    startDeleteTicketActivity(flightId);
                }
            };
        }
    }

    private void startCreateTicketActivity(int flightId) {
        Intent intent = new Intent(this, CreateTicketActivity.class);
        intent.putExtra("idFlight", flightId);
        startActivity(intent);
    }

    private void startDeleteTicketActivity(int flightId) {
        Intent intent = new Intent(this, DeleteTicketActivity.class);
        intent.putExtra("idFlight", flightId);
        startActivity(intent);
    }

}
