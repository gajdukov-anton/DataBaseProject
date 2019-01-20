package com.example.user.airtickets.activity.admin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.AdminData;
import com.example.user.airtickets.models.Airport;
import com.example.user.airtickets.models.ResponseFromServer;

public class AddAirportActivity extends AppCompatActivity {

    private boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airport);
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


    public void createAirport(View view) {
        Airport airport = new Airport();
        EditText name = (EditText) findViewById(R.id.nameAirportEditText);
        EditText location = (EditText) findViewById(R.id.locationAirportEditText);
        airport.setName(name.getText().toString());
        airport.setLocation(location.getText().toString());
        if (airport.isReadyToUpload() && !isPressed) {
            if (!AdminData.getInstance().isAirportExist(airport)) {
                isPressed = true;
                postNewAirportToServer(airport);
            } else {
                Toast.makeText(this, "Такой аэропорт уже есть", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }
    }

    private void postNewAirportToServer(final Airport airport) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.AddAirportListener listener = new ServerApi.AddAirportListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                Toast.makeText(AddAirportActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                if (responseFromServer.status.equals("added")) {
                    AdminData.getInstance().getAirportList().add(airport);
                }
                isPressed = false;
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AddAirportActivity.this, message, Toast.LENGTH_SHORT).show();
                isPressed = false;
            }
        };
        serverApi.setAddAirportListener(listener);
        serverApi.addAirport(airport);
    }
}
