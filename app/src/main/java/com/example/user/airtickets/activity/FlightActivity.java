package com.example.user.airtickets.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.TicketAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.fragment.BookingDialogFragment;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class FlightActivity extends AppCompatActivity implements BookingDialogFragment.BookingDialogListener {

    private List<Ticket> tickets = new ArrayList<>();
    private ArrayList<Ticket> bookedTickets = new ArrayList<>();
    private int choosenTicket;
    private Flight flight;
    private int idFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        getDataFromIntent();
        initData();
        displayFlightInformation();
        createRecyclerView();
        //createRecyclerViewWithTickets(idFlight);
        createBackButton();
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
        setStringToTextView(R.id.pointOfDepartureFlightInformation, "Место отправления: " + flight.getPoint_of_departure());
        setStringToTextView(R.id.pointOfDestinationFlightInformation, "Место прибытия: " + flight.getPoint_of_destination());
        setStringToTextView(R.id.airportFlightInformation, "Данные о компании: Компания");
        setStringToTextView(R.id.timeOfDepartureFlightInformation, "Время отправления: " + flight.getTime_of_departure());
        setStringToTextView(R.id.timeOfDestinationFlightInformation, "Время прибытия: " + flight.getTime_of_destination());
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

    private void createRecyclerView() { //не забыть удалить
        this.tickets = tickets;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listTicket);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        TicketAdapter adapter = new TicketAdapter(this, tickets, flight);
        TicketAdapter.Callback adapterListener = new TicketAdapter.Callback() {
            @Override
            public void onButtonClick(int position) {
                createDialogFragment(position);
            }


        };
        adapter.setCallback(adapterListener);
        recyclerView.setAdapter(adapter);
    }

    private void createRecyclerView(List<Ticket> tickets) {
        this.tickets = tickets;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listTicket);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        TicketAdapter adapter = new TicketAdapter(this, tickets, flight);
        TicketAdapter.Callback adapterListener = new TicketAdapter.Callback() {
            @Override
            public void onButtonClick(int position) {
                createDialogFragment(position);
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText dialogName = dialog.getDialog().findViewById(R.id.dialog_name);
        EditText dialogSecondName = dialog.getDialog().findViewById(R.id.dialog_second_name);
        bookedTickets.add(tickets.get(choosenTicket));
        bookedTickets.get(bookedTickets.size() - 1).setName(dialogName.getText().toString());
        bookedTickets.get(bookedTickets.size() - 1).setName(dialogSecondName.getText().toString());
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    private void initData() {
        tickets.add(new Ticket(1, 1200, "econom"));
        tickets.add(new Ticket(12, 1900, "econom"));
        tickets.add(new Ticket(15, 2500, "econom"));
        tickets.add(new Ticket(14, 3000, "econom"));
        tickets.add(new Ticket(30, 7500, "medium"));
        tickets.add(new Ticket(32, 8500, "medium"));
        tickets.add(new Ticket(36, 8000, "medium"));
        tickets.add(new Ticket(31, 7500, "medium"));
        tickets.add(new Ticket(39, 12000, "medium"));
        tickets.add(new Ticket(50, 20000, "business"));
        tickets.add(new Ticket(53, 23000, "business"));
        tickets.add(new Ticket(55, 27000, "business"));
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra(MainActivity.ACCESS_MESSAGE_FLIGHT_ACTIVITY, bookedTickets);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
