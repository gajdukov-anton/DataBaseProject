package com.example.user.airtickets.activity.admin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.models.AdminData;
import com.example.user.airtickets.models.Class;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.TicketForUpload;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketActivity extends AppCompatActivity {

    private boolean isButtonPassed = false;
    private int flightId;
    private List<String> idClassesList = getIdClassesList(AdminData.getInstance().getClassList());
    private int currentIdClass = Integer.valueOf(idClassesList.get(0));;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        Bundle arguments = getIntent().getExtras();
        flightId = arguments.getInt("idFlight");
        createSpinner();
        updateTextView(R.id.idClassesTextView, "Класс билета: " + AdminData.getInstance().getClassList().get(0).getName());
    }

    private List<String> getIdClassesList(List<Class> classes) {
        List<String> idClasses = new ArrayList<>();
        for (Class classTicket : classes) {
            idClasses.add(String.valueOf(classTicket.getIdClass()));
        }
        return idClasses;
    }

    private void createSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                getIdClassesList(AdminData.getInstance().getClassList()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.idClassesSpinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               currentIdClass = Integer.valueOf(idClassesList.get(position));
               updateTextView(R.id.idClassesTextView, "Класс билета: " + AdminData.getInstance().getClassList().get(position).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        currentIdClass = Integer.valueOf(idClassesList.get(0));
    }

    private void updateTextView(int id, String text) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(text);
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
                ticket.setIdClass(currentIdClass);
                ticket.setPrice(getValueFromEditText(R.id.priceEditText));
                ticket.setDescription(getValueFromEditText(R.id.descriptionEditText));
                ticket.setPlaceNumber(getValueFromEditText(R.id.placeNumberEditText));
                if (ticket.isReadyToUpload()) {
                    postNewTicketToServer(ticket);
                } else {
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    isButtonPassed = false;
                }
            } else {
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
                //finish();
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
