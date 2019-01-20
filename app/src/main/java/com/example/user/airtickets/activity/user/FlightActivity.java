package com.example.user.airtickets.activity.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.airtickets.R;
import com.example.user.airtickets.adapter.TicketForBookingAdapter;
import com.example.user.airtickets.api.retrofit.ServerApi;
import com.example.user.airtickets.fragment.BookingDialogFragment;
import com.example.user.airtickets.models.Flight;
import com.example.user.airtickets.models.ResponseFromServer;
import com.example.user.airtickets.models.Ticket;
import com.example.user.airtickets.models.UserData;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FlightActivity extends AppCompatActivity implements BookingDialogFragment.BookingDialogListener {

    private List<Ticket> tickets = new ArrayList<>();
    private ArrayList<Ticket> bookedTickets = new ArrayList<>();
    private int choosenTicket;
    private Flight flight;
    private int idFlight;
    private boolean isAvailable;
    private Calendar calendar = Calendar.getInstance();
    private Date dateOfBirth;
    private DatePickerDialog.OnDateSetListener dateListener;
    private BookingDialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        getDataFromIntent();
        displayFlightInformation();
        createRecyclerViewWithTickets(idFlight);
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
        setStringToTextView(R.id.pointOfDepartureFlightInformation, "Место отправления: " + flight.getPointOfDeparture());
        setStringToTextView(R.id.pointOfDestinationFlightInformation, "Место прибытия: " + flight.getPointOfDestination());
        setStringToTextView(R.id.airportFlightInformation, "Данные о компании: Компания");
        setStringToTextView(R.id.timeOfDepartureFlightInformation,  "Время отправления: " + flight.getFormattedTimeOfDeparture());
        setStringToTextView(R.id.timeOfDestinationFlightInformation,  "Время прибытия: " + flight.getFormattedTimeOfDestination());
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
            @Override
            public void onFailure(String message) {
                Toast.makeText(FlightActivity.this, "Билеты отсутсвуют", Toast.LENGTH_SHORT).show();
                createRecyclerView(new ArrayList<Ticket>());
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

        TicketForBookingAdapter adapter = new TicketForBookingAdapter(this, this.tickets, flight);
        TicketForBookingAdapter.Callback adapterListener = new TicketForBookingAdapter.Callback() {
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
        dialog = new BookingDialogFragment();
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


    public void setDateOfBirth(View v) {
        dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateOfBirth = calendar.getTime();
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
                setTextOnDialogTextView(R.id.setDataOfBirthTextView, "Дата рождения: " + df.format(calendar.getTime()));
            }

        };
        new DatePickerDialog(FlightActivity.this, dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    private void setTextOnDialogTextView(int id, String value) {
        TextView textView = (TextView) dialog.getDialog().findViewById(id);
        textView.setText(value);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText dialogName = dialog.getDialog().findViewById(R.id.dialog_name);
        EditText dialogSecondName = dialog.getDialog().findViewById(R.id.dialog_second_name);
        String name = dialogName.getText().toString();
        String secondName = dialogSecondName.getText().toString();
        String sex = getSexFromRadioButton(dialog.getDialog().findViewById(R.id.male), dialog.getDialog().findViewById(R.id.female));
        if (isReadyToBook(dialog)) {
            bookedTickets.add(tickets.get(choosenTicket));
            bookedTickets.get(bookedTickets.size() - 1).setFirstName(name);
            bookedTickets.get(bookedTickets.size() - 1).setLastName(secondName);
            bookedTickets.get(bookedTickets.size() - 1).setSex(sex);
            bookedTickets.get(bookedTickets.size() - 1).setDateOfBirth(dateOfBirth.toString());
            UserData.bookedTickets.add(tickets.get(choosenTicket));
            createRecyclerView(tickets);
        } else {
         //   createDialogFragment(choosenTicket);
         //   setPreviousDataOnDialogFragment(this.dialog, name, secondName, sex, dateOfBirth);
        }
    }

    private boolean isReadyToBook(DialogFragment dialogFragment) {
        EditText dialogName = dialogFragment.getDialog().findViewById(R.id.dialog_name);
        EditText dialogSecondName = dialogFragment.getDialog().findViewById(R.id.dialog_second_name);
        if (dialogName.getText().toString().equals("")) {
            Toast.makeText(this, "Укажите имя", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogSecondName.getText().toString().equals("")) {
            Toast.makeText(this, "Укажите фамилию", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dateOfBirth == null) {
            Toast.makeText(this, "Укажите дату рождения", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setPreviousDataOnDialogFragment(DialogFragment dialogFragment, String name, String secondName, String sex, Date dateOfBirth) {
        EditText dialogName = dialogFragment.getDialog().findViewById(R.id.dialog_name);
        EditText dialogSecondName = dialogFragment.getDialog().findViewById(R.id.dialog_second_name);
        TextView dateOfBirthView = dialogFragment.getDialog().findViewById(R.id.setDataOfBirthTextView);
        dialogName.setText(name);
        dialogSecondName.setText(secondName);
        setValueToSexRadioButton(dialogFragment, sex);
        if (dateOfBirth != null) {
            Locale local = new Locale("ru","RU");
            DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
            dateOfBirthView.setText("Дата рождения: " + df.format(dateOfBirth));
        }
    }

    private void checkStatusTicket(int id, final int position) {
        ServerApi serverApi = ServerApi.getInstance();
        ServerApi.CheckStatusTicketListener listener = new ServerApi.CheckStatusTicketListener() {
            @Override
            public void onSuccessful(ResponseFromServer responseFromServer) {
                if (responseFromServer.status.equals("not_booked")) {
                    createDialogFragment(position);
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

    private void setValueToSexRadioButton(DialogFragment dialogFragment, String value) {
        if (value.equals("male")) {
            RadioButton button = (RadioButton) dialogFragment.getDialog().findViewById(R.id.male);
            button.setChecked(true);
        } else if (value.equals("female")) {
            RadioButton button = (RadioButton) dialogFragment.getDialog().findViewById(R.id.female);
            button.setChecked(true);
        }
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
