package com.example.user.airtickets.activity.admin;

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
import com.example.user.airtickets.models.ResponseFromServer;

import java.util.ArrayList;
import java.util.List;

public class DeleteFlightActivity extends AppCompatActivity {

    private List<Flight> flights = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_flight);
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
        adapter.setButtonText("УДАЛИТЬ РЕЙС");
        FlightAdapter.Callback adapterListener = new FlightAdapter.Callback() {
            @Override
            public void onMoreButtonClick(int flightId, int positionInList) {
                deleteFlight(flightId);
            }
        };

        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private void deleteFlight(int idFlight) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DeleteFlightListener listener = new ServerApi.DeleteFlightListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                Toast.makeText(DeleteFlightActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                createRecyclerViewWithFlights();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(DeleteFlightActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
        serverApi.setDeleteFlightListener(listener);
        serverApi.deleteFlight(idFlight);
    }
}
