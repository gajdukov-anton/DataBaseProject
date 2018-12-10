package com.example.user.airtickets.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.FlightForUpload;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.Ticket;
import com.example.user.airtickets.models.TicketForUpload;

public class CreateTicketActivity extends AppCompatActivity {

    private boolean isButtonPassed = false;
    private int flightId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        Bundle arguments = getIntent().getExtras();
        flightId = arguments.getInt("idFlight");
        //createBackButton();
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

    public void createTicket(View view) {
        if (!isButtonPassed) {
            if (isOnline()) {
                TicketForUpload ticket = new TicketForUpload();
                ticket.setIdFlight(flightId);
                ticket.setIdClass(getValueFromEditText(R.id.idClassEditText));
                ticket.setPrice(getValueFromEditText(R.id.priceEditText));
                ticket.setDescription(getValueFromEditText(R.id.descriptionEditText));
                ticket.setPlaceNumber(getValueFromEditText(R.id.placeNumberEditText));
                if (ticket.isReadyToUpload()) {
                    postNewTicketToServer(ticket);
                } else {
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    isButtonPassed = false;
                }
            }
            else {
                isButtonPassed = false;
                Toast.makeText(CreateTicketActivity.this, getResources().getString(R.string.online_error), Snackbar.LENGTH_LONG).show();
            }
        }

    }

    private void postNewTicketToServer(TicketForUpload ticket) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.UploadNewTicketListener listener = new ServerApi.UploadNewTicketListener() {
            @Override
            public void onUploadNewTicketListener(ResponseFromServer responseFromServer) {
                Toast.makeText(CreateTicketActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                isButtonPassed = false;
                finish();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(CreateTicketActivity.this, message, Toast.LENGTH_SHORT).show();
                isButtonPassed = false;
            }
        };
        serverApi.setUploadNewTicketListener(listener);
        serverApi.uploadNewTicketToServer(ticket);
    }

    private String getValueFromEditText(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
