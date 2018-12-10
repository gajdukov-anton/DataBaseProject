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
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.FlightForUpload;
import com.example.user.airtickets.models.ResponseFromServer;

public class CreateFlightActivity extends AppCompatActivity {

    private boolean isButtonPassed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight);
        createBackButton();
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


    public void createNewFlight(View view) {
        if (!isButtonPassed) {
            if (isOnline()) {
                FlightForUpload flight = new FlightForUpload();
        //        postNewFlightToServer(flight);
                flight.setIdAirport(getValueFromEditText(R.id.idAirportEditText));
                flight.setIdPlane(getValueFromEditText(R.id.idPlaneEditText));
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
