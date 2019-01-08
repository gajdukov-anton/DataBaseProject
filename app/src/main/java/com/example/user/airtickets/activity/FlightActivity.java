package com.example.user.airtickets.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.TicketAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.fragment.BookingDialogFragment;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.Ticket;
import com.example.user.airtickets.models.UserData;

import java.util.ArrayList;
import java.util.List;

public class FlightActivity extends AppCompatActivity implements BookingDialogFragment.BookingDialogListener {

    private List<Ticket> tickets = new ArrayList<>();
    private ArrayList<Ticket> bookedTickets = new ArrayList<>();
    private int choosenTicket;
    private Flight flight;
    private int idFlight;
    private boolean isAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        getDataFromIntent();
        displayFlightInformation();
        createRecyclerViewWithTickets(idFlight);
        createBackButton();
        Toast.makeText(this, String.valueOf(UserData.bookedTickets.size()), Toast.LENGTH_SHORT).show();
    }

    private void getDataFromIntent() {
        idFlight = getIntent().getIntExtra("idFlight", 0);
        Bundle bundle = getIntent().getExtras();
        try {
            flight = bundle.getParcelable("flight");

        } catch (NullPointerException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void displayFlightInformation() {
        setStringToTextView(R.id.pointOfDepartureFlightInformation, "Место отправления: " + flight.getPointOfDeparture());
        setStringToTextView(R.id.pointOfDestinationFlightInformation, "Место прибытия: " + flight.getPointOfDestination());
        setStringToTextView(R.id.airportFlightInformation, "Данные о компании: Компания");
        setStringToTextView(R.id.timeOfDepartureFlightInformation, "Время отправления: " + flight.getTimeOfDeparture());
        setStringToTextView(R.id.timeOfDestinationFlightInformation, "Время прибытия: " + flight.getTimeOfDestination());
    }

    private void setStringToTextView(int id, String value) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
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

    private void createRecyclerViewWithTickets(int idFlight) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.DownloadTicketsListener listener = new ServerApi.DownloadTicketsListener() {
            @Override
            public void onDownloadedTickets(List<Ticket> tickets) {
                createRecyclerView(tickets);
            }
        };
        serverApi.setDownloadTicketsListener(listener);
        serverApi.downloadTickets(this, idFlight);
    }

    private void createRecyclerView(final List<Ticket> tickets) {
        this.tickets = removeBookedTickets(tickets);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listTicket);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        TicketAdapter adapter = new TicketAdapter(this, this.tickets, flight);
        TicketAdapter.Callback adapterListener = new TicketAdapter.Callback() {
            @Override
            public void onButtonClick(int position) {
               // createDialogFragment(position);
                checkStatusTicket(tickets.get(position).getIdTicket(), position);
            }
        };
        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    public void createDialogFragment(int position) {
        this.choosenTicket = position;
        BookingDialogFragment dialog = new BookingDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }

    private List<Ticket> removeBookedTickets(List<Ticket> tickets) {
        List<Ticket> treatmentTickets = new ArrayList<>();
        if (UserData.bookedTickets.isEmpty()) {
            return tickets;
        }
        for (Ticket ticket : tickets) {
            boolean isContain = false;
            for (Ticket bookedTicket : UserData.bookedTickets) {
                if (ticket.getIdTicket() == bookedTicket.getIdTicket() &&ticket.getName() == ticket.getName()
                        && ticket.getPrice() == bookedTicket.getPrice()) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                treatmentTickets.add(ticket);
            }
        }
        return treatmentTickets;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText dialogName = dialog.getDialog().findViewById(R.id.dialog_name);
        EditText dialogSecondName = dialog.getDialog().findViewById(R.id.dialog_second_name);
        EditText dialogDate = dialog.getDialog().findViewById(R.id.dialog_date_of_birth);
        bookedTickets.add(tickets.get(choosenTicket));
        bookedTickets.get(bookedTickets.size() - 1).setFirstName(dialogName.getText().toString());
        bookedTickets.get(bookedTickets.size() - 1).setLastName(dialogSecondName.getText().toString());
        bookedTickets.get(bookedTickets.size() - 1).setSex(getSexFromRadioButton(dialog.getDialog().findViewById(R.id.male),
                dialog.getDialog().findViewById(R.id.female)));
        bookedTickets.get(bookedTickets.size() - 1).setDateOfBirth(dialogDate.getText().toString());
        UserData.bookedTickets.add(tickets.get(choosenTicket));
        createRecyclerView(tickets);
    }

    private void checkStatusTicket(int id, final int position) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.CheckStatusTicketListener listener = new ServerApi.CheckStatusTicketListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                if (responseFromServer.status.equals("not_booked")) {
                    createDialogFragment(position);
                    Toast.makeText(FlightActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FlightActivity.this, responseFromServer.status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(FlightActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
        serverApi.setCheckStatusTicketListener(listener);
        serverApi.checkStatusTicket(id);
    }

    private String getSexFromRadioButton(View viewMale, View viewFemale) {
        RadioButton maleButton = (RadioButton) viewMale;
        RadioButton femaleButton = (RadioButton) viewFemale;
        if (maleButton.isChecked()) {
            return "male";
        } else if (femaleButton.isChecked()) {
            return "female";
        } else {
            return "";
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra(MainActivity.ACCESS_MESSAGE_FLIGHT_ACTIVITY, bookedTickets);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
