package com.example.user.airtickets.activity.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.FlightAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.Flight;

import java.util.List;

public class AllFlightsActivity extends AppCompatActivity {

    private List<Flight> flightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_flights);
        createRecyclerViewWithFlights();
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
        adapter.setButtonText("ПОДРОБНЕЕ");
        FlightAdapter.Callback adapterListener = new FlightAdapter.Callback() {
            @Override
            public void onMoreButtonClick(int flightId, int positionInList) {
                loadAllTicketActivity(flightId);
            }
        };

        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private void loadAllTicketActivity(int flightId) {
        Intent intent = new Intent(this, AllTicketActivity.class);
        intent.putExtra("idFlight", flightId);
        startActivity(intent);
;
    }
}
