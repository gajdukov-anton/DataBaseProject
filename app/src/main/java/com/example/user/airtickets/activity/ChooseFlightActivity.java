package com.example.user.airtickets.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.FlightAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.Flight;

import java.util.ArrayList;
import java.util.List;

public class ChooseFlightActivity extends AppCompatActivity {

    private List<Flight> flights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_flight);
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
        adapter.setButtonText("СОЗДАТЬ БИЛЕТ");  // TODO: 03.12.2018 вынести в ресурсы
        FlightAdapter.Callback adapterListener = new FlightAdapter.Callback() {
            @Override
            public void onMoreButtonClick(int flightId, int positionInList) {
                startCreateTicketActivity(flightId);
            }
        };

        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private void startCreateTicketActivity(int flightId) {
        Intent intent = new Intent(this, CreateTicketActivity.class);
        intent.putExtra("idFlight", flightId);
        startActivity(intent);
    }

}
