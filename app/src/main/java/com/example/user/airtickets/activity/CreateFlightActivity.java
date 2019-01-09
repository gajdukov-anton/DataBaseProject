package com.example.user.airtickets.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.AdminData;
import com.example.user.airtickets.models.Airport;
import com.example.user.airtickets.models.Class;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.FlightForUpload;
import com.example.user.airtickets.models.Plane;
import com.example.user.airtickets.models.ResponseFromServer;

import java.util.ArrayList;
import java.util.List;

public class CreateFlightActivity extends AppCompatActivity {

    private boolean isButtonPassed = false;
    private List<String> idPlanesList = getIdPlanesList(AdminData.getInstance().getPlaneList());
    private List<String> idAirportsList = getIdAirportsList(AdminData.getInstance().getAirportList());
    private int currentIdPlane = Integer.valueOf(idPlanesList.get(0));
    private int currentIdAirport = Integer.valueOf(idAirportsList.get(0));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight);
        createBackButton();
        createSpinner(R.id.idAirportSpinner, idAirportsList, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentIdAirport = Integer.valueOf(idAirportsList.get(position));
                Toast.makeText(CreateFlightActivity.this, String.valueOf(currentIdAirport), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        createSpinner(R.id.idPlaneSpinner, idPlanesList, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentIdPlane = Integer.valueOf(idPlanesList.get(position));
                Toast.makeText(CreateFlightActivity.this, String.valueOf(currentIdPlane), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
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

    private List<String> getIdPlanesList(List<Plane> planes) {
        List<String> idPlanes = new ArrayList<>();
        for (Plane plane : planes) {
            idPlanes.add(String.valueOf(plane.getIdPlane()));
        }
        return idPlanes;
    }

    private List<String> getIdAirportsList(List<Airport> airports) {
        List<String> idAirports = new ArrayList<>();
        for (Airport airport : airports) {
            idAirports.add(String.valueOf(airport.getIdAirport()));
        }
        return idAirports;
    }

    private void createSpinner(int layoutId, final List<String> dataList, AdapterView.OnItemSelectedListener listener) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(layoutId);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(listener);
    }

    public void createNewFlight(View view) {
        if (!isButtonPassed) {
            if (isOnline()) {
                FlightForUpload flight = new FlightForUpload();
                //        postNewFlightToServer(flight);
                flight.setIdAirport(currentIdAirport);
                flight.setIdPlane(currentIdPlane);
                flight.setDescription(getValueFromEditText(R.id.descriptionEditText));
                flight.setPointOfDeparture(getValueFromEditText(R.id.pointOfDepEditText));
                flight.setPointOfDestination(getValueFromEditText(R.id.pointOfDestEditText));
                flight.setTimeOfDeparture(getValueFromEditText(R.id.timeOfDepEditText));
                flight.setTimeOfDestination(getValueFromEditText(R.id.timeOfDestEditText));
                if (flight.isReadyToUpload()) {
                    postNewFlightToServer(flight);
                } else {
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    isButtonPassed = false;
                }
            } else {
                isButtonPassed = false;
                Toast.makeText(CreateFlightActivity.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }
        }

    }

    private String getValueFromEditText(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    private void postNewFlightToServer(FlightForUpload flight) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.UploadNewFlightListener listener = new ServerApi.UploadNewFlightListener() {
            @Override
            public void onUploadNewFlight(ResponseFromServer responseFromServer) {
                Toast.makeText(CreateFlightActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                finish();
                isButtonPassed = false;
            }

            @Override
            public void onFailure(String message) {
                isButtonPassed = false;
                Toast.makeText(CreateFlightActivity.this, "Не удалось: " + message, Toast.LENGTH_SHORT).show();
            }
        };
        serverApi.setUploadNewFlightListener(listener);
        serverApi.uploadNewFlightToServer(flight);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
